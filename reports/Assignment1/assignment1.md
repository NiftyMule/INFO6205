# Assignment 1 - Program Structures & Algorithms Fall 2021

> Name: Zhilue Wang
>
> NUID: 001522973

## Task

- Implement the `RandomWalk` class
- Run experiments to get the relation between `d` and `N`

## Conclusion

$$
d = 0.8876\sqrt N - 0.005
$$

## Evidence

For each `N`, 10,000,000 experiments were performed to get more stable mean value of `d`. 

Below are the test results:

| **N** | **d** | sqrt(N) |
| :---: | :---: | :-----: |
|  10   | 2.800 |  3.162  |
|  20   | 3.966 |  4.472  |
|  30   | 4.857 |  5.477  |
|  40   | 5.609 |  6.325  |
|  50   | 6.270 |  7.071  |

Tried plotting the data point with `N` as X-axis and `d` as Y-axis, we got the graph below:

<img src="C:\Users\Harry\Downloads\save (2).png" alt="save (2)" style="zoom:50%;" />

It looks like there is a relation but not a perfect linear relation. As the distance formula used square root operation, we can also try using `sqrt(N)` as X-axis and `d` as Y-axis:

<img src="C:\Users\Harry\Downloads\save (3).png" alt="save (3)" style="zoom:50%;" />

This seems like a perfect linear relation, we can use [online linear regression tool](https://www.statskingdom.com/linear-regression-calculator.html) to get the formula. Enter the data points, and we got:

![image-20210917031956553](C:\Users\Harry\AppData\Roaming\Typora\typora-user-images\image-20210917031956553.png)

## Code

Only `RandomWalk.java` is modified.

## Unit tests

![image-20210917034621006](C:\Users\Harry\AppData\Roaming\Typora\typora-user-images\image-20210917034621006.png)