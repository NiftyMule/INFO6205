# Assignment 3 - Program Structures & Algorithms Fall 2021

> Name: Zhilue Wang
>
> NUID: 001522973

## Task

- Implement height-weighted quick union with path compression
- Experiments to find out how many operations required to connect all sites

## Conclusion

$$
Number\ of\ objects\ (n) = Number\ of\ pairs\ (m) + 1
$$

## Evidence

We selected five different `N` values: 50, 100, 200, 300, 500.

And for each `N` value, we called `count()` method five times. From five runs we got the same results. Results as below:

|  N   |  50  | 100  | 200  | 300  | 500  |
| :--: | :--: | :--: | :--: | :--: | :--: |
|  m   |  49  |  99  | 199  | 299  | 499  |

And there is an easy way to explain it:

Initially, we have `n` number of [connected components] as they are all unconnected.

Each time when we connect two unconnected sites, we are connecting two unconnected [connected components]. So the number of [connected components] will decrease by `1`.

Our goal is to let the number of [connected components] to be `1`.

And the total number of union operations required to reduce it from `N` to `1` is `N - 1`.

Thus, `m = n - 1`.

## Code

- `UF_HWQUPC` - implement union-find structure
- `UF_Experiment` - UF client file to run experiments. The main function defines a list of N values to run experiments.

## Unit tests

![image-20210930005619203](Assignment3.assets/image-20210930005619203.png)