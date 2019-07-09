#version 330
layout (points) in;
layout (triangle_strip, max_vertices = 4) out;

uniform mat4 mvp;
uniform float tile_size;

void createVertex(vec3 offset) {

}

void main() {
    gl_Position = gl_in[0].gl_Position;
    EmitVertex();
    EndPrimitive();
}