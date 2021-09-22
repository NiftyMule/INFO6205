package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.util.Benchmark;
import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class InsertionSortBenchmark {

    /**
     * Generate random array with given size
     * @param size - array size
     * @return generated random array
     */
    private static ArrayList<Integer> getRandomArray(int size)
    {
        ArrayList<Integer> result = new ArrayList<>();
        // set element's max possible value to be 2*size
        int bound = size * 2;
        Random rd = new Random();
        for (int i = 0; i < size; ++i)
        {
            result.add(rd.nextInt(bound));
        }
        return result;
    }

    /**
     * Generate ordered array (elements are from 1 to size)
     * @param size - array size
     * @return generated ordered array
     */
    private static ArrayList<Integer> getOrderedArray(int size)
    {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) result.add(i);
        return result;
    }

    /**
     * Generate partially ordered array
     * the first half of the array will be in order, and the second half will be random values
     * @param size - array size
     * @return generated partially ordered array
     */
    private static ArrayList<Integer> getPartiallyOrderedArray(int size)
    {
        ArrayList<Integer> result = getOrderedArray((size + 1) / 2);
        result.addAll(getRandomArray(size / 2));
        return result;
    }

    /**
     * Generate reverse-ordered array
     * @param size - array size
     * @return generated reverse-ordered array
     */
    private static ArrayList<Integer> getReverseOrderedArray(int size)
    {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) result.add(size - i);
        return result;
    }

    public static void main(String[] args)
    {
        int numOfRuns = 50;
        // N values
        int[] ns = {1000, 2000, 4000, 8000, 16000, 32000};
        String[] arrayType = {"random", "ordered", "partially ordered", "reverse ordered"};

        // test random array sorting time
        for (int n : ns)
        {
            System.out.println("========================= " + n + " =========================");
            GenericSort<Integer> sorter = new InsertionSort<>();
            Benchmark<Integer[]> bm = new Benchmark_Timer<>("Test insertion sort", sorter::sort);

            for (int i = 0; i < arrayType.length; i++)
            {
                // generate array
                ArrayList<Integer> arrList = new ArrayList<>();;
                switch (i) {
                    case 0: arrList = getRandomArray(n);
                            break;
                    case 1: arrList = getOrderedArray(n);
                            break;
                    case 2: arrList = getPartiallyOrderedArray(n);
                            break;
                    case 3: arrList = getReverseOrderedArray(n);
                            break;
                }
                Integer[] arr = arrList.toArray(new Integer[0]);

                // time the sorting function
                double time = bm.run(arr, numOfRuns);
                System.out.printf("%-40s: %.3f%n", arrayType[i] + " array sorting time", time);
            }
        }
    }
}
