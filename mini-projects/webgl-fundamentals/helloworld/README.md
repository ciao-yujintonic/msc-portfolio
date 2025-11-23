# WebGL Hello World

https://webglfundamentals.org/webgl/lessons/webgl-fundamentals.html

WebGL's core job is simply to draw points, lines, and triangles.

WebGL programs run on the GPU, and therefore require two small GPU programs called shaders

- Vertex Shader : computes the final position of each vertex.
- Fragment Shader : computes the color of each pixel of the rendered primitive.

These two shaders, written in GLSL (a C-like language), together form a WebGL program.
Almost all WebGL API calls exist to configure data and state for these shaders before executing them with `gl.drawArrays` or `gl.drawElements`.

Shaders can receive data in 4 ways,
1. Attributes & Buffers

Buffers are binary arrays stored on the GPU. They commonly contain vertex positions, normals, colors, or texture coordinates.
Attributes describe how to read data from a buffer when the vertex shader runs.
A vertex shader executes once per vertex and automatically reads the next chunk of data from the bound buffers.

2. Uniforms

Uniforms are global variables supplied by the CPU for each draw call.
They are constant during a single draw and are often used for transformation matrices, material properties, or lighting information.

3. Textures

Textures are randomly accessible arrays on the GPU.
While most often used for image data, they can also store any numerical information.

4. Varyings

Varyings allow vertex shaders to pass data to fragment shaders.
Their values are automatically interpolated across the surface of each point, line, or triangle during rasterization.

## Initialization
To compile shaders to put them on the GPU, so we need to get them into strings.
These GLSL strings can be created as strings by Javascript, concatenating, usign AJAX to download them, by using template strings.

#### 1. Create strings
```javascript
<script id="vertex-shader-2d" type="notjs">
 
  // an attribute will receive data from a buffer
  attribute vec4 a_position;
 
  // all shaders have a main function
  void main() {
 
    // gl_Position is a special variable a vertex shader is responsible for setting
    gl_Position = a_position;
  }
 
</script>
 
<script id="fragment-shader-2d" type="notjs">
 
  // fragment shaders don't have a default precision so we need to pick one. mediump is a good default
  precision mediump float;
 
  void main() {
    // gl_FragColor is a special variable a fragment shader is responsible for setting
    gl_FragColor = vec4(1, 0, 0.5, 1); // return reddish-purple
    // Colors in WebGL go from 0 to 1.
  }
 
</script>
```
This sample is not complex enough to need to generate GLSL at runtime.

#### 2. Compile the shader
After creating a shader, uploading the GLSL source, and we need to compile the shader.

```javascript
function createShader(gl, type, source) {
  var shader = gl.createShader(type);
  gl.shaderSource(shader, source);
  gl.compileShader(shader);
  var success = gl.getShaderParameter(shader, gl.COMPILE_STATUS);
  if (success) {
    return shader;
  }
 
  console.log(gl.getShaderInfoLog(shader));
  gl.deleteShader(shader);
}
```

#### 3. Create the shaders
And create the two shaders by calling the function

```javascript
var vertexShaderSource = document.querySelector("#vertex-shader-2d").text;
var fragmentShaderSource = document.querySelector("#fragment-shader-2d").text;
 
var vertexShader = createShader(gl, gl.VERTEX_SHADER, vertexShaderSource);
var fragmentShader = createShader(gl, gl.FRAGMENT_SHADER, fragmentShaderSource);
```

#### 4. Link shaders into a program
```javascript
// function to link shaders into a program
function createProgram(gl, vertexShader, fragmentShader) {
  var program = gl.createProgram();
  gl.attachShader(program, vertexShader);
  gl.attachShader(program, fragmentShader);
  gl.linkProgram(program);
 
  var success = gl.getProgramParameter(program, gl.LINK_STATUS);
  if (success) {
    return program;
  }
 
  console.log(gl.getProgramInfoLog(program));
  gl.deleteProgram(program);
}



// calling part
var program = createProgram(gl, vertexShader, fragmentShader);
```

#### 5. Supply data
We've created a GLSL progrma on the GPU then we need to supply data to it. The majority of the WebGL API is about setting up state to supply data to our GLSL programs.

1. Find a location of the attribute of the created program.
```javascript
var positionAttributeLocation = gl.getAttribLocation(program, "a_position");
```
Looking up attribute locations (and uniform locations) should be done during initialization, not in the render loop.

2. create buffers
we need to create a buffer because attributes get their data from buffers
```javascript
var positionBuffer = gl.createBuffer();
```

3. refer to the resource thorugh the bind point.
```javascript
gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
```
We can manipulate many WebGL resource on global bind points. Bind points are considered as internal global variables.
All other functions refer to the resource through the bind point.

4. put data in buffer
We can put data in the buffer by referencing it through the bind point.
```javascript
// three 2d points
var positions = [
  0, 0,
  0, 0.5,
  0.7, 0,
];
// gl.bufferData copies data to positionBuffer on the GPU
gl.bufferData(gl.ARRAY_BUFFER, 
// WebGL needs strongly typed data, new 32bit floating poitn numbers is created
new Float32Array(positions), 
// This is a hint to WebGL about how we'll use the data. gl.STATIC_DRAW for static data.
gl.STATIC_DRAW);
```

## Rendering
You should always set the size you want a canvas to be with CSS.

#### 1. Convert clip space values into pixels
To do this, we call `gl.viewport` and pass it the current size of the canvas.
```javascript
gl.viewport(0, 0, gl.canvas.width, gl.canvas.height);
```
This maps the -1 +1 clip space
for x, 0 <-> `gl.canvas.width`
for y, 0 <-> `gl.canvas.height`

#### 2. Clear canvas
```javascript
// Clear the canvas
gl.clearColor(0, 0, 0, 0);
gl.clear(gl.COLOR_BUFFER_BIT);
```
`0,0,0,0` are rgba, so it's transparent.

#### 3. Execute shader program
```javascript
gl.useProgram(program);
```
#### 4. Supply buffered data to attributes of shaders
1. enable attribute
```javascript
gl.enableVertexAttribArray(positionAttributeLocation);
```
enable attribute which we looked up by `gl.getAttribLocation`

2. Set how to take out the data
```javascript
// Bind the position buffer.
gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
 
// Tell the attribute how to get data out of positionBuffer (ARRAY_BUFFER)
var size = 2;          // 2 components per iteration
var type = gl.FLOAT;   // the data is 32bit floats
var normalize = false; // don't normalize the data
var stride = 0;        // 0 = move forward size * sizeof(type) each iteration to get the next position
var offset = 0;        // start at the beginning of the buffer
gl.vertexAttribPointer(
    positionAttributeLocation, size, type, normalize, stride, offset)
```
The hidden part of `gl.vertexAttribPointer` is that WebGL assignes currently binding ARRAY_BUFFER to attribute.
It means that attributes are binded to `poisitionBuffer`

3. Execute GLSL program
```javascript
var primitiveType = gl.TRIANGLES;
var offset = 0;
var count = 3; //3 times
gl.drawArrays(primitiveType, offset, count);
```
Since `primitiveType` is `gl.TRIANGLES`, WebGL generates a triangle based on 3 values of `gl_Position` when vertex shader is executed 3 times.

#### 5. Render a triangle
Every pixel calls the fragment shader for the color.