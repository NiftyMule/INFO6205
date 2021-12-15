package edu.neu.coe.info6205;

import java.util.ArrayList;
import java.util.List;

public class Pell {
    List<Long> pellNums;

    public Pell() {
        pellNums = new ArrayList<>();
        pellNums.add(0L);
        pellNums.add(1L);
    }

    public long get(int n) {
        if (n < 0) throw new UnsupportedOperationException("Pell.get is not supported for negative n");
        if (n >= pellNums.size())
        {
            int i = pellNums.size();
            while (i <= n)
            {
                pellNums.add(2 * pellNums.get(i - 1) + pellNums.get(i - 2));
                i++;
            }
        }
        return this.pellNums.get(n);
    }
}
