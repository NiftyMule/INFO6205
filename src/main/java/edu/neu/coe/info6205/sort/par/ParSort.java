package edu.neu.coe.info6205.sort.par;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 */
public class ParSort {

    public static int cutoff = 2500;
    public static int depthLimit = 4;

    public static void sort(int[] array, int from, int to, ForkJoinPool pool, int depth) {
        if (to - from < cutoff || depth >= depthLimit) Arrays.sort(array, from, to);
        else {
            CompletableFuture<int[]> parSort1 =
                    parSort(array, from, from + (to - from) / 2, pool, depth + 1);
            CompletableFuture<int[]> parSort2 =
                    parSort(array, from + (to - from) / 2, to, pool, depth + 1);
            CompletableFuture<int[]> parSort = parSort1.thenCombine(parSort2, (xs1, xs2) -> {
                int[] result = new int[xs1.length + xs2.length];
                int i = 0;
                int j = 0;
                for (int k = 0; k < result.length; k++) {
                    if (i >= xs1.length) {
                        result[k] = xs2[j++];
                    } else if (j >= xs2.length) {
                        result[k] = xs1[i++];
                    } else if (xs2[j] < xs1[i]) {
                        result[k] = xs2[j++];
                    } else {
                        result[k] = xs1[i++];
                    }
                }
                return result;
            });

            parSort.whenComplete((result, throwable) -> System.arraycopy(result, 0, array, from, result.length));
            parSort.join();
        }
    }

    private static CompletableFuture<int[]> parSort(int[] array, int from, int to, ForkJoinPool pool, int depth) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int[] result = new int[to - from];
                    System.arraycopy(array, from, result, 0, result.length);
                    sort(result, 0, to - from, pool, depth);
                    return result;
                }
        , pool);
    }
}