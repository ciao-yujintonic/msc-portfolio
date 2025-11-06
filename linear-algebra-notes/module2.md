# Vectors are objects that move around apce

> ### Learning Objectives
> - Calculate basic operations (dot product, modulus, negation) on vectors
> - Calculate a change of basis
> - Recall linear independence
> - Identify a linearly independent basis and relate this to the dimensionality of the space

## Opersions on vectors
1) Vector LEngth (Modulus)
A vector's length, also called its modulus, is: |r| = √a² + b²
If r=(a,b) then by the Pythagorean theorem, its modulus (size) is √a² + b²
> modulus = length = magnitude



2) Dot Product and Cosine
- the magnitudes (modulus) of two vectors
- the angle beween them
> r·s =|r||s|cosθ
=> The dot product depends on how aligned two vectors are.



- If θ = 0d (same direction):
cosθ = 1 => r·s =|r||s|  -> dot product is positive & maximal
- If θ = 90d => r·s = 0  -> vectors are perpendicular
- If θ = 180d (opposite)
cosθ = -1 => r·s = -|r||s|  -> dot product is negative



3) Projection
- |s|cosθ represents the projection (shadow) of vector s onto r.
- If s is perpendicular to r (θ =90d) then cosθ = 0 -> projection = 0 (no shadow)

- Salar Projection: How much of s lies along direction r => r·s/|r|
- Vector Proejction: Projection including direction of r => (r·s/|r|^2) * r

---

## Changing Basis
- A vector **exists independently** of the coordinate system;  
  coordinates only describe it relative to chosen **basis vectors**.

- Basis vectors (e.g., `e1`, `e2`) define the coordinate system.
- A vector can be written as a linear combination of basis vectors.
- Changing the basis changes the **coordinates**, not the vector itself.

- ## Changing Basis (Orthogonal Case)
If the new basis vectors (`b1`, `b2`) are **orthogonal (90° apart)**:
- We can use **dot product / projection** to find new coordinates.

### Scalar coefficient along `b1`
r·b1/|b1|^2

### Scalar coefficient along `b2`
r·b2/|b2|^2

### Vector projection
r·bi/|bi|^2 * bi

- Adding these projection vectors reconstructs `r` in the new basis.

---

### Orthogonality Check
b1·b2 = 0 => orthogonal

- If basis vectors are **not orthogonal**,  
  we must use **matrices** to transform coordinates.

---


## Vector Operations Assignment
![IMG_0222](https://github.com/user-attachments/assets/9b9782be-4700-4678-973c-ef00438e56df)
![IMG_0223](https://github.com/user-attachments/assets/397e6525-ce2e-47b3-832c-6cf9a00e2d19)
