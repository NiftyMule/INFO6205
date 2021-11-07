package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 */
public class Main {

    public static void main(String[] args) {
        testDepth();
        testCutoff();
    }

    /**
     * Test the parallel sorting performance with different recursion depth
     */
    public static void testDepth()
    {
        // Determine best thread pool
        ForkJoinPool myPool = new ForkJoinPool(512);
        Map<Integer, List<Long>> result = new HashMap<>();
        int size = 1000000;
        for (int i = 0; i < 8; i++)
        {
            int[] arr = new int[size];
            result.put(size, new ArrayList<>());

            for (int j = 0; j < 9; j++) {
                // construct thread pool
                ParSort.depthLimit = j;

                long avgTime = TimeUnit.NANOSECONDS.toMillis(evaluateSortTime(arr, myPool, 10));

                result.get(size).add(avgTime);
                System.out.printf("Array size: %d, recursion depth: %d, elapsed time: %d ms \n",
                        size, ParSort.depthLimit, avgTime);
            }
            size *= 2;
        }

        writeToFile(result, "./src/depth.csv", null);
    }

    /**
     * Determine the best cutoff value
     */
    public static void testCutoff()
    {
        ForkJoinPool myPool = new ForkJoinPool(512);

        // Different array size
        Map<Integer, List<Long>> result = new HashMap<>();

        // Avoid cutoff as we are comparing performance between system sort and parallel sort
        ParSort.cutoff = 1;

        // array size loop
        for (int i = 0; i < 20; ++i)
        {
            int n = 200 * (i + 1);
            int[] array = new int[n];
            List<Long> timeList = new ArrayList<>();

            ParSort.depthLimit = 1;
            long avgParSortTime = TimeUnit.NANOSECONDS.toMicros(evaluateSortTime(array, myPool, 2000));
            System.out.printf("Array size: %d, average ParSort time: %d μs\n", n, avgParSortTime);
            timeList.add(avgParSortTime);

            ParSort.depthLimit = 0;
            long avgSysSortTime = TimeUnit.NANOSECONDS.toMicros(evaluateSortTime(array, myPool, 2000));
            timeList.add(avgSysSortTime);
            System.out.printf("Array size: %d, average SysSort time: %d μs\n", n, avgSysSortTime);

            long diff = avgParSortTime - avgSysSortTime;
            System.out.printf("ParSort time - SysSort time: %d\n", diff);
            timeList.add(diff);

            result.put(n, timeList);
        }

        writeToFile(result, "./src/cutoff.csv", null);
    }

    /**
     * Write experiment results to file
     * @param filepath - path to file
     * @param header - column headers
     */
    public static void writeToFile(Map<Integer, List<Long>> result, String filepath, String header)
    {
        try {
            FileOutputStream fis = new FileOutputStream(filepath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fis));

            if (header != null)
            {
                bw.write(header);
                bw.newLine();
            }

            for (int arrSize : result.keySet()) {
                StringBuilder content = new StringBuilder(Integer.toString(arrSize));
                for (long elapsed : result.get(arrSize)) {
                    content.append(",").append(elapsed);
                }
                bw.write(content.toString());
                bw.newLine();
            }

            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fill the array with random numbers
     * @param arr - array to be filled
     * @param seed - random seed
     */
    public static void fillArrayRandom(int[] arr, int seed)
    {
        // to ensure array with same size will have same contents
        Random random = new Random(seed);
        for (int i = 0; i < arr.length; ++i)
        {
            arr[i] = random.nextInt(50000000);
        }
    }

    /**
     * run parallel sort `t` times with given thread pool
     * @param array - random number container
     * @param pool - thread pool
     * @param t - the number of experiments to be run
     * @return - average elapsed time among `t` runs (in ms)
     */
    public static long evaluateSortTime(int[] array, ForkJoinPool pool, int t)
    {
        long totalTime = 0;

        for (int i = 0; i < t; i++)
        {
            fillArrayRandom(array, array.length + t);
            Instant startTime = Instant.now();
            ParSort.sort(array, 0, array.length, pool, 0);
            Instant endTime = Instant.now();
            totalTime += Duration.between(startTime, endTime).toNanos();
        }
        return totalTime / t;
    }
}
