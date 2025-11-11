# Matrices in Linear Algebra : Objects that operate on Vectors

## 1) Matrices, vectors, and solving simultaneous equation problems
- A **vector** can describe movement in space or features of an object (+data view).
- Operations:
  - Vector add => componentwise
  - Scalar multiply => scale length
- Basis vectors (i, j, k) express coordinates.
- A **matrix** transforms vectors.
- Columns of a matrix = image of basis vectors.
- Linear transformation preserves:
  - Straight lines
  - Parallelism
 
## 2) How matrices transform space
- Identity -> do nothing
- Scaling -> stretch/compress axes
- Reflection -> flip across line/axis
- Shear -> slide one axis direction
- Rotation -> rotate by angle θ  
  (2D) =  [  cosθ   -sinθ ]
          [  sinθ    cosθ ]
- All maintain linearity (lines <-> lines)

## 3) Types of matrix transformation
- Doing transform A then B -> composite transform
- Composite = **matrix multiplication**
- Order matters -> **not commutative**  
  (AB ≠ BA)
- But associative -> (A·B)·C = A·(B·C)
- Reason: transforms applied in sequence

## 4) Composition or combination of matrix transformations
- Example : Multiple trips (simultaneous linear eqautions)
- Can express as matrix x vector = output
- Solving systems / Modeling data / Fitting models are useful
- connection in ML : find best parameters, linear models, optimization

## 5) Solving the apples and bananas problem: Gaussian elimination
- Goal: solve **A r = s**
- inverse : If A⁻¹ exists -> **r = A⁻¹ s** (But computing inverse directly is unnecessary)
- Use **Elimination (Gaussian elimination)**:
  1. Make zeros below diagonal (echelon form)
  2. Back-substitution to solve variables
- Equivalent to rewriting system into simpler form
- More efficient than computing A⁻¹ first

## 6) Going from Gaussian elimination to finding the inverse matrix
- To compute A⁻¹:
  - Solve A x = e₁ , A x = e₂ , ... for each basis vector e
- Practical way:
  - Make augmented matrix: [ A | I ]
  - Apply elimination
  - Result: [ I | A⁻¹ ]
- Same elimination procedure as solving A r = s
- Works for general (square, invertible) A

## 7) Determinants and inverses
- Determinant = area/volume scaling factor => 2×2: det = ad − bc
- det ≠ 0
  - A invertible
  - Columns (basis vectors) linearly independent
  - Unique solution exists
- det = 0
  - A not invertible
  - Columns dependent -> space collapses (e.g., plane -> line)
  - No unique solution (none or infinite)
- Inverse of 2×2 uses det in denominator
