# Analysis of Algorithms

## Overview
Analyzing algorithms is essential for understanding how programs scale and for avoiding performance issues. The course material highlights both empirical measurement and mathematical modeling as complementary approaches for evaluating algorithm efficiency. The goal is to develop intuition for how running time and memory usage grow as input sizes increase.

## Analysis of Algorithms Introduction
One way to study an algorithm is to run it on inputs of various sizes and observe the running time. The classic 3-SUM example illustrates this process: measuring execution time for increasing N and plotting the results provides clear evidence that the brute-force approach grows approximately cubically.

Tools like the Stopwatch class allow consistent timing, and log–log plots help identify power-law relationships. Doubling the input size and examining how much the running time increases provides a quick estimate of the algorithm’s growth rate. For 3-SUM, the ratio stabilizes near 8, confirming an ~N³ trend.

## Mathematical Models
Exact instruction counts are often too detailed to be practical, so simplified models are used. A common strategy is to focus on a single basic operation—such as array accesses—and estimate how often it occurs. Lower-order terms are ignored using tilde notation, which keeps only the dominant term when N becomes large.

This approach shows, for example, that the 2-SUM nested loops require on the order of N² array accesses, while the 3-SUM triple loop requires on the order of N³.

## Order-of-Growth Classifications
Algorithms are often classified into broad growth categories such as constant, logarithmic, linear, linearithmic, quadratic, cubic, and exponential. These categories allow comparisons that are independent of specific machines or compilers.

Understanding these classes is crucial because only linear or linearithmic algorithms are practical for extremely large inputs. Quadratic and cubic approaches quickly become infeasible once input sizes reach the tens or hundreds of thousands.

## Theory of Algorithms
Binary search provides a clear example of logarithmic performance. Each iteration halves the search interval, resulting in at most 1 + log₂N comparisons. The lecture notes derive this using a simple recurrence relation and illustrate how careful reasoning leads to tight guarantees.

Sorting enables a more efficient approach to 3-SUM. After sorting the array, each pair (i, j) can be checked by binary searching for the required third value. This reduces the overall complexity to O(N² log N), which represents a major improvement over N³. Empirical results confirm the large performance gap between these two approaches.

Performance can be studied from multiple perspectives,
Best case - the easiest possible input
Worst case - a guarantee for every input
Average case - behavior for typical or random inputs

These perspectives help determine realistic expectations and theoretical limits.

Algorithm theory uses these notations to express asymptotic relationships within constant factors:
Big-Theta for tight bounds
Big-Oh for upper bounds
Big-Omega for lower bounds

Tilde notation (~) is used throughout the course for simpler approximate modeling when full asymptotic classification is unnecessary.

## Memory
Running time is only one dimension of performance. Memory usage also scales with input size and data structure design.
Primitive: 1-8 bytes
1D arrays: 24-byte overhead + element storage
Objects: 16-byte header + fields + padding

A practical example shows that `WeightedQuickUnionUF` uses approximately 8N bytes, demonstrating how memory grows linearly with the number of elements.