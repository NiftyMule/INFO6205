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

        while (uf.components() != 1) {
            Random random = new Random();
            int x;
            int y;

            // generate random pairs of integer
            do {
                x = random.nextInt(n);
                y = random.nextInt(n);
            } while (uf.isConnected(x, y));

            // connect these two unconnected sites
            uf.union(x, y);
            operations++;
        }

        return operations;
    }

    public static void main(String[] args)
    {
        int[] Ns = {50, 100, 200, 300, 500};
        for (int N : Ns)
        {
            System.out.printf("*************** N=%d ***************\n", N);
            for (int i = 0; i < 5; ++i)
            {
                System.out.printf("Run %d : %d\n", i, count(N));
            }
        }
    }
}
