# WebGL Shaders and GLSL

https://webglfundamentals.org/webgl/lessons/webgl-shaders-and-glsl.html

A vertex shader and fragment shader are linked together into a shader program.
A typical WebGL app will have many shader programs.

## Vertex Shader

A Vertex Shader's job is to generate clip space coordinates.

Shader is called once per vertex. Each time it's called, we are required to set the special global variable, `gl_Position` to some clip space coordinates.

#### 3 ways that vertex shader gets data
1. Attributes : data pulled from buffers
2. Uniforms : values taht stay the same for all vertices of a single draw call
3. Textures : data from pixels/texels

### 1. Attributes
The most common way that vertex shader gets data is through buffers and attributes.

#### 1. Create buffers
```javascript
var buf = gl.createBuffer();
```

#### 2. Put data in those buffers
```javascript
gl.bindBuffer(gl.ARRAY_BUFFER, buf);
gl.bufferData(gl.ARRAY_BUFFER, someData, gl.STATIC_DRAW);
```

#### 3. Look up the location of its attributes at initialization time through a shader program
```javascript
var positionLoc = gl.getAttribLocation(someShaderProgram, "a_position");
```

#### 4. Tell WebGL how to pull data out of those buffers and into the attribute at render time
```javascript
// turn on getting data out of a buffer for this attribute
gl.enableVertexAttribArray(positionLoc);
 
var numComponents = 3;  // (x, y, z)
var type = gl.FLOAT;    // 32bit floating point values
var normalize = false;  // leave the values as they are
var offset = 0;         // start at the beginning of the buffer
var stride = 0;         // how many bytes to move to the next vertex
                        // 0 = use the correct stride for type and numComponents
 
gl.vertexAttribPointer(positionLoc, numComponents, type, normalize, stride, offset);
```

#### 5. We can pass the data directly to the right position in vertex shader if we put clip space vertices into our buffers.

### 2. Uniforms
Uniforms are values passed to the shader that stay the same for all vertices in a draw call.

#### 1) define 'uniform' in a vertex shader to add an offset to the vertex shader
```javascript
attribute vec4 a_position;
uniform vec4 u_offset;
 
void main() {
  gl_Position = a_position + u_offset;
}
```

#### 2) Look up the location of the uniform at initialization time
```javascript
var offsetLoc = gl.getUniformLocation(someProgram, "u_offset");
```

#### 3) Set the uniform before drawing
```javascript
gl.uniform4fv(offsetLoc, [1, 0, 0, 0]);  // offset it to the right half the screen
```

Uniforms belong to individual shader programs. If we have multiple shader programs with uniforms of the same name, both uniforms will have their own locations and hold their own values. When calling `gl.uniformXXX`, we're only setting the uniform for the current program. The current program is the last program we passed to `gl.useProgram`.

#### 4) Uniforms can be many types and we have to call `gl.uniformXXX` to set it.
There are also types `bool`, `bvec2`, `bvec3`, and `bvec4`. They use either the `gl.uniformXfX` or `gl.uniformXiX` functions.

#### 5) We can set all the uniforms of the array at once for an array
```javascript
// in shader
uniform vec2 u_someVec2[3];
 
// in JavaScript at init time
var someVec2Loc = gl.getUniformLocation(someProgram, "u_someVec2");
 
// at render time
gl.uniform2fv(someVec2Loc, [1, 2, 3, 4, 5, 6]);  // set the entire array of u_someVec2
```

#### 6) But to set individual elements of the array, look up the location of each element individually
```javascript
// in JavaScript at init time
var someVec2Element0Loc = gl.getUniformLocation(someProgram, "u_someVec2[0]");
var someVec2Element1Loc = gl.getUniformLocation(someProgram, "u_someVec2[1]");
var someVec2Element2Loc = gl.getUniformLocation(someProgram, "u_someVec2[2]");
 
// at render time
gl.uniform2fv(someVec2Element0Loc, [1, 2]);  // set element 0
gl.uniform2fv(someVec2Element1Loc, [3, 4]);  // set element 1
gl.uniform2fv(someVec2Element2Loc, [5, 6]);  // set element 2
```

#### 7) Look up the location of each element individually if create a struct
```javascript
struct SomeStruct {
  bool active;
  vec2 someVec2;
};
uniform SomeStruct u_someThing;
```
Look up each field individually
```javascript
var someThingActiveLoc = gl.getUniformLocation(someProgram, "u_someThing.active");
var someThingSomeVec2Loc = gl.getUniformLocation(someProgram, "u_someThing.someVec2");
```

## Fragment Shader

