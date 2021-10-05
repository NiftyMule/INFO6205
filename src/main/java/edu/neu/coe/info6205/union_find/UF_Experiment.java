package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UF_Experiment {

    /**
     * This method will generate a union-find data structure with size n,
     * and will randomly connect two un-connected sites
     * It will return how many union operations are required to connect all sites
     * @param n - number of sites
     * @return - number of operations required to connect all sites
     */
    public static int count(int n)
    {
        // to count the number of union() operation
        int operations = 0;

        // initialize UF data structure
        UF uf = new UF_HWQUPC(n, true);
        Random random = new Random();

        while (uf.components() > 1) {
            // generate random pairs of integer
            int x = random.nextInt(n);
            int y = random.nextInt(n);
            // connect these two unconnected sites
            uf.union(x, y);
            operations++;
        }

        return operations;
    }

    public static void main(String[] args)
    {
        int[] Ns = {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 256000, 512000, 1024000};
        for (int N : Ns)
        {
            System.out.printf("*************** N=%d ***************\n", N);
            long totalOps = 0;
            for (int i = 0; i < 1000; ++i)
            {
                totalOps += count(N);
            }
            System.out.println("Mean number of operations: " + totalOps / 1000.0);
        }
    }
}
