package edu.neu.coe.info6205.sort;

import edu.neu.coe.info6205.sort.par.ParSort;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * Simple unit test to check if the parallel sort implementation is correct
 */
public class parSortTest {

    Random random = new Random();

    @Test
    public void testParSort()
    {
        int size = 100000;
        for (int i = 0; i < 4; i++)
        {
            int[] parSortArr = new int[size];
            fillArrayRandom(parSortArr);
            int[] normalSortArr = parSortArr.clone();
            ParSort.sort(parSortArr, 0, size, ForkJoinPool.commonPool(), 0);
            Arrays.sort(normalSortArr);
            Assert.assertArrayEquals(parSortArr, normalSortArr);
            size *= 2;
        }
    }

    @Test
    public void testParSortWithOddSize()
    {
        int size = 1000001;

        int[] parSortArr = new int[size];
        fillArrayRandom(parSortArr);
        int[] normalSortArr = parSortArr.clone();

        ParSort.sort(parSortArr, 0, size, ForkJoinPool.commonPool(), 0);
        Arrays.sort(normalSortArr);
        Assert.assertArrayEquals(parSortArr, normalSortArr);
    }

    public void fillArrayRandom(int[] arr)
    {
        for (int i = 0; i < arr.length; ++i)
        {
            arr[i] = random.nextInt(50000000);
        }
    }
}
