# Step 1 - Setting Up a Basic Rendering Pipeline

## 1. WebGLRenderer: Initializing the GPU Rendering Pipeline
- Three.js provides a high-level wrapper over WebGL.
- WebGLRenderer creates the GPU context, compiles shaders, manages buffers, and performs draw calls.

### Concept
#### WebGL(Web Graphics Library)
WebGl is a low-level graphics APi based on OpenGL ES 2.0 that exposes programmable GPU pipelines to the browser.
Every visible pixel must be explicitly produced through shaders and draw calls.

#### Three.js
Three.js is a high-level Javascript 3D library built on top of WebGL.
WebGL is too low-level, so it's not easy to use such as for buffer, state machine, attribute loading.
So Three.js provides abstractions over WebGL, enabling developers to work with 3D scenes, materials, lights, and camera without manually managing low-level GPU buffers or shader compilation.

#### Shader
A shader is a GPU program written in GLSL. It defines how to draw in each step of graphics pipelines.
There are two types of shader in WebGL/Three.js;
The vertex shader transforms vertex positions, while the fragment shader computes the final color of each pixel.

- Vertex Shader<br/>
calculates the position of each vertex in GPU
processes transformation of animation(wave, ripple) 

- Fragment Shader<br/>
calculates color, light, reflection of each pixel(fragment) in the screen.
implements effects like water, fire, noise, PBR.



## 2. Scene: The Root of the Scene Graph
- All objects, lights, and cameras must be attached.
- The scene graph allows hierarchical transformations.

## 3. PerspectiveCamera: Projecting 3D to 2D
- Defines how the world is viewed.
- Perspective projection simulates realistic depth perception.

## 4. Render Loop: Core of Real-Time Graphics
- requestAnimationFrame ensures ~60 FPS rendering.
- All animation and shader uniform updates happen here.
