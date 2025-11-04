# Linear Algebra — Why We Need It

Linear algebra provides a systematic way to model and solve problems involving multiple variables and linear relationships. It is built on two core objects below.

## 1) Vectors
- Represent quantities with multiple components (e.g., prices of items, data features)
- Used to express input/output values in many systems

## 2) Matrices
- Represent linear relationships between vectors
- Example: multiple shopping trips → equations → matrix form  

Linear algebra helps us solve these systems efficiently, especially when the number of variables becomes large.

---

## Use in Machine Learning
- Data is stored as vectors and matrices (rows = samples / columns = features)
- Models apply linear transformations to these vectors
- Training involves adjusting parameters to best fit data, often through linear algebra + optimization
- Example: fitting a line/distribution to data → find parameters that minimize error

Thus, linear algebra is fundamental for:
- expressing data
- defining model operations
- efficiently computing solutions

---

# Vectors

A vector can be viewed in two ways:
1. A geometric object that indicates movement in space.
2. A list of attributes describing an object (e.g. house features).

We generalize vectors in data science to represent multiple features of an object.

## Basic Vector Operations
- **Addition:** Place one vector after another, component-wise addition.
- **Scalar multiplication:** Stretch or shrink a vector by a number.
  - Includes negative scaling (음수 -> 방향 반대).

## Coordinate System
- Defined by basis vectors (e.g., i, j in 2D).
- Any vector can be represented as a linear combination(선형 결합) of basis vectors.
  - Example: r = 3i + 2j → 좌표 (3, 2)

## Subtraction
- Defined as adding the negative: r − s = r + (−s)

## Example
A house can be represented as a vector of attributes,  
e.g., (120 m², 2 beds, 1 bath, 150k€).  
Vector addition and scaling apply similarly:  
2 × house → (240, 4, 2, 300)

## Key Idea
Vectors are defined by:
- addition
- scaling (multiplying by a scalar)

These operations allow vectors to describe both geometric directions and abstract data features.
