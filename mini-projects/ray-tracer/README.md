## Concepts
### What is Ray Equation?
> P(t) = O + t * D

This is not invented by computer graphics, it is actually a classic parametric equation of a line in gemometry.

#### Point : A ray is a line
A ray is a line that starts at one point and extends infintely in one direction.
Mathematically, we need a way to represent "every point along a line in a given direction".

The most natural and widely used representation is the parametric form of line
> P(t) = O + t * D

- O : the origin (starting point of the ray) e.g. camera
- D : the direction vector
- t : a real number (distance along the ray)

=> Changing `t` moves the point along the ray.

### Then why this form?

#### 1. `t` = distance
It means (1) small `t` points close to the camera, and (2) large `t` points fartehr away.
So when a ray intersects multiple objects, the object with the smallest positive `t` is the one that appears in the final image.

#### 2. intersection math is simple
Ray tracing is about a repetitive question to check ```if this ray hit the object? then at what t?```

By subsituting the parametric ray equation into an object's equation for example,
- Ray-sphere intersection becomes solving a simple quadratic equation
- Ray-plane intersection becoems solving a linear equation

> This image helped me understand how the image is created https://www.scratchapixel.com/index.html
> <img width="741" height="443" alt="image" src="https://github.com/user-attachments/assets/c94dbfe0-1dd7-43a9-b399-6bdb56400bc0" />



> ### 1. Sphere equation
> A sphere is defined as `All points that are at a fixed distance R from the center C.`
>
> In math: ```|P - C| = R```
>
> Squaring both sides: ```|P -C|^2 = R^2``` to simplify √(Px - Cx)^2 + (Py - Cy)^2 + (Pz - Cz)^2 without the square root
>
> - P : any point on the sphere
> - C : sphere center
> - R : radius
> - | | : vector length (norm)
>
> This is the standard sphere equation.
>
> #### Example
> Subsitue P(t) = O + tD: ```| (O + tD) - C |^2 = R^2```
> - No solution : ray misses the sphere
> - One solution : ray touches the sphere
> - Two solutions : ray enters and exits the sphere (taking the smaller positive `t`)
> ** if t < 0, since ray points forward, negative `t` means it's not visible. So negative `t` is ignored.
>
> From ```P(t_intersection) = O + t_intersection * D```,
>
> => `t_intesction` means how far the camera(or eye) goes to touch the surface of sphere.
> => ```P(t_intersection)``` is the point where ray meets the surface of sphere.

> ### 2. Plane equation
> A plane is defined as `Any point P on the plane must form a vector (P - P0) that is orthogonal to the plane's Nnormal N.`
>
> In math : ```N · (P - P0) = 0```
> - N : normal vector (decides the direction fo the plane)
> - P0 : a known point on the plane
> - P : a point being tested
> - · : dot product
>
> #### Example
> Subsitute P(t) = ```N · ((O + tD) - P0) = 0```
> - Solve for `t` : the intersection distance

## (Added) Lambert diffuse shading
Lambert diffuse shading models how light interacts with a rough, matte surface.
It assumes that the surface reflects incoming light equally in all direction, regardless of the viewer's position.
Because the reflection is perfely uniform, the only factor that determines brightness is **the angle** between the light direction and the surface normal.

This idea is a fundamental physical rule which is very obvious in the real world.
> A surface appears brightest when it faces the light directly, and darker as it tilts away from the light.

Geometrically, this behavior is captured by the cosine of the angle between the surface normal N and the light direction L
```cosθ = N ⋅ L```
- θ = 0deg : surface faces the light -> cosθ = 1 => brightest
- θ = 90deg : light grazes the surface -> cosθ = 0 => no brightness
- θ > 90deg : light hits from behind -> negative => consider as 0

This creates the core Lambert shading equation
> Diffuse Brightness = max(0, N ⋅ L)

The final surface color combines the material's albedo (its inherent color) with this brightness factor
> Color = Albedo * max(0, N ⋅ L)

```
** Albedo
In Lambert diffuse shading, albedo represents the maerial's intrinsic color 
how much of the incoming light is reflected at each RGB channel.
```


---


## Project Structure
- vec3.py : vector calculation, geometry calculation based
- ray.py : ray origin, direction
- sphere.py : ray-sphre hit test
- camera.py : position of camera, viewport, ray generation logic
- main.py : all rendering loops, background and hit color

## What I focused on
this project wasn't just about getting an image to render.
I wanted to understand why each formula and step existed, so I spent time breaking down the math and the gemetroy behind the code line by line.

- Ray Equation

At first, the ray equation ```P(t) = O+ tD``` felt like something I should just accept, but it finally clicked when I realized it's simply
"a line starting from the camera and extending in one direction."
Once I internalized this geometric interpretation, the rest of the calculations naturally followed.

- Half-b Discriminant

The original quadratic form uses ```b = 2(oc·D)```, but many rac tracers use the simplified half-b version.
I wondered why until I drived it myself, it removes unnecessary factors of 2 and makes the equation cleaner and faster especially in C++.
Instead of memorizing it, I wanted to understand the optimization.

- Normal Calculation

I questioned why normal vectors for spheres don't call ```.unit()``` and instead use (P-C)/R.
Later, I realized that any point on a phere is exactly R units away from the center, so (P-C) has length R.
Dividing by the radius immediately gives a unit normal without computing a square root. This was the moment where the geometry simplified the math.

### Why Python instead of C++?
Peter Shirley’s “Ray Tracing in One Weekend” is written in C++, but I chose to implement the project in Python.
The goal of this mini-project is to understand the fundamentals of ray tracing including vector math, ray–object intersections, normals, and shading.
and Python allows me to iterate faster without compiler overhead or language boilerplate and I'm more used to it than C++.
Once the concepts are fully internalized, they can be transferred to C++ easily.


### Normal Visualization
Before applying any lighting model, I rendered the scene using normal-based coloring to verify that ray-object intersections and normal calculations were correct.

Each component of the surface normal lies in [-1, 1]. By converting this range into [0,1]: color = 0.5*(N+1) to map into RGB channel.
The sphere appears in a rainbow gradient. This step intentionally ignores the object's material color (Vec3(0.8, 0.3, 0.3))
because the goal is to visually confirm that normals look correct before moving on to actual lighting.

<img width="600" height="336" alt="image" src="https://github.com/user-attachments/assets/405a2ee9-dc0b-4bd3-9acb-b4cb751c96e6" />

## Summary
This project is not only about code implementation, I pursued to understand why the formulas come up one by one.
It's just a small ray tracer, but was a sifnificant practice to develop all the fundamental factors of graphics (vector, intersections, normal, camera, shading) by my own.

## Next steps
- [ ] Implement Lambert diffuse shading  
- [ ] Add shadow rays for occlusion
- [ ] Add reflections (mirror-like surfaces)  
- [ ] Add refraction (glass/dielectric materials)  
- [ ] Introduce anti-aliasing (multiple rays per pixel)  
- [ ] Add a material system (matte, metal, dielectric)  
- [ ] Implement Depth of Field using a thin-lens camera model

### Implement Lambert diffuse shading
After this, the sphere finally represents its own color with the light.
<img width="596" height="338" alt="image" src="https://github.com/user-attachments/assets/d905e9ab-5466-44f4-a744-fc0050b47d6b" />

#### What I Focuse On
- Normalizing the light direction vector

I realized that using an unnormalized light direction distorts shading because the dot product is scaled by the vector’s length. Normalizing the light vector ensures the dot product truly represents the angle between the light and the surface.
<img width="416" height="237" alt="image" src="https://github.com/user-attachments/assets/0ccd4b2b-7af3-4c59-8716-6ab705e74b06" />

- Why the ground became completely dark

Lambert shading clamps negative values to 0, so any surface that faces away from the light is fully dark.
This helped me understand the limitation of direct illumination and why real scenes require indirect light to avoid pure black shadows.
> This is what happens when I set the light vector as Vec3(1,0,0).
> The ground : P = (0, -0.5, -1), C = (0, 100.5, -1) P-C = (0,100,0) => N = (0,1,0)
> N · L = 0*1 + 1*0 + 0*0 = 0 -> cosθ = 0 (black)
<img width="422" height="241" alt="image" src="https://github.com/user-attachments/assets/7957683d-deff-4f02-a5e3-c836b0341892" />

```=> To enhance this limitation of Lmabert diffuse, we need add concepts of indirect light and ambient light```

<br/><br/><br/>


> References <br/>
> https://raytracing.github.io/books/RayTracingInOneWeekend.html <br/>
> https://www.scratchapixel.com/ <br/>
> Questions that I was curious about to ChatGPT 
