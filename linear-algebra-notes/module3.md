# Matrices in Linear Algebra : Objects that operate on Vectors

## 1) Vectors & Matrices Basics
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
 
## 2) Types of Transformations
- Identity -> do nothing
- Scaling -> stretch/compress axes
- Reflection -> flip across line/axis
- Shear -> slide one axis direction
- Rotation -> rotate by angle θ  
  (2D) =  [  cosθ   -sinθ ]
          [  sinθ    cosθ ]
- All maintain linearity (lines <-> lines)

## 3) Composition & Matrix Multiplication
- Doing transform A then B -> composite transform
- Composite = **matrix multiplication**
- Order matters -> **not commutative**  
  (AB ≠ BA)
- But associative -> (A·B)·C = A·(B·C)
- Reason: transforms applied in sequence

## 4) Linear Algebra Motication (Applications)
- Example : Multiple trips (simultaneous linear eqautions)
- Can express as matrix x vector = output
- Solving systems / Modeling data / Fitting models are useful
- connection in ML : find best parameters, linear models, optimization

