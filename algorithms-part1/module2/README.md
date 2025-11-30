# module 2

## Quick-Find

- Uses array `id[]`: same component → same id
- `find` is fast
- `union` is slow → must scan entire array → O(N)

---

## Quick-Union

- Uses `id[]` as parent pointers → forest (trees)
- Root represents component
- `union` connects roots → fast
- Bad case: trees can become tall → `find` becomes O(N)

---

## Weighted Quick-Union

- Always attach smaller tree under larger tree
- Keeps trees balanced → height ≤ log N
- `find` / `union`: O(log N)

---

## Path Compression

- During `find`, make nodes point closer to root
- Makes tree almost flat
- Very fast in practice → ~ O(1)

---

| Method                           | find      | union     | worst-case time |
| -------------------------------- | --------- | --------- | --------------- |
| Quick-Find                       | fast      | slow      | M N             |
| Quick-Union                      | slow      | fast      | M N             |
| Weighted Q.U.                    | fast      | fast      | N + M Log N     |
| Weighted Q.U. + Path Compression | very fast | very fast | N + M Lg N    |

> Weighted Quick-Union + Path Compression → **best practical performance**


## Assignment

### Percolation problem
Estimate the value of percolation threshold using Manto Carlo simulation.

#### Percolation
Percolation is a simple way to describe how something moves through a material full of tiny openings. The same idea can be applied to many systems that rely on connectivity. If enough parts of the system are linked, a continuous path can form from one side to the other.

#### Grid Representation
To model this idea, we use an n-by-n grid.

Each site can be:
- open, meaning it can be part of a path, or
- blocked, meaning nothing can pass through.

A site becomes full if it is open and connected to an open site in the top row through neighboring open sites (up, down, left, or right).
The system is said to percolate once at least one full site appears in the bottom row.
In other words, fluid would be able to travel from the top of the grid to the bottom through open spaces.

#### Problem
- With p = 0, every site is blocked → no percolation.
- With p = 1, the whole grid is open → guaranteed percolation.
When the grid is large, there exists a special value p* where behavior changes suddenly.
Below this value, percolation almost never happens.
Above it, percolation almost always occurs.
This threshold cannot be calculated exactly, so we estimate it numerically.

#### Monte Carlo Method
To approximate the threshold:
1. Start with all sites blocked.
2. Repeatedly pick a blocked site at random and open it.
3. Stop when the system percolates.

The proportion of open sites at that moment is one estimate of the percolation threshold.
Running this experiment many times allows us to compute averages, variation, and confidence intervals.

#### Project Description
This project implements the percolation simulation described above.
It was an assignment given by Princeton University’s online algorithms course.

Percolation.java — models the grid and checks for connectivity
PercolationStats.java — runs repeated simulations and summarizes the results

Two instances of WeightedQuickUnionUF are used to avoid the known backwash issue when determining which sites are full.