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

=> This is how real light works that the surface your eye sees first is the one closest along that direction.

#### 2. intersection math is simple
Ray tracing is about a repetitive question to check ```if this ray hit the object? then at what t?```

By subsituting the parametric ray equation into an object's equation for example,
- Ray-sphere intersection becomes solving a simple quadratic equation
- Ray-plane intersection becoems solving a linear equation

> This image helped me understand how the image is created
<img width="741" height="443" alt="image" src="https://github.com/user-attachments/assets/c94dbfe0-1dd7-43a9-b399-6bdb56400bc0" />

> ### 1. Sphere equation
> A sphere is defined as `All points that are at a fixed distance R from the center C.`
>
> In math: ```|P - C| = R```
>
> Squaring both sides: ```|P -C|^2 = R^2```
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

### Normal Visualization
Before applying any lighting model, I rendered the scene using normal-based coloring to verify that ray-object intersections and normal calculations were correct.

Each component of the surface normal lies in [-1, 1]. By converting this range into [0,1]: color = 0.5*(N+1)
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


> References <br/>
> https://www.scratchapixel.com/ <br/>
> https://raytracing.github.io/books/RayTracingInOneWeekend.html <br/>
> Questions that I was curious about to ChatGPT 
