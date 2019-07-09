#version 330
layout (points) in;
layout (triangle_strip, max_vertices = 4) out;

in vec3 color[];
out vec3 final_color;

uniform mat4 mvp;
const float size = 0.1;

void createVertex(vec3 offset) {
    vec4 actual_offset = vec4(offset * size, 0.0);
    vec4 world_position = gl_in[0].gl_Position + actual_offset;
    gl_Position = mvp * world_position;
    final_color = color[0];
    EmitVertex();
}

void main() {
    createVertex(vec3(-1.0, 1.0, 1.0));
    createVertex(vec3(-1.0, -1.0, 1.0));
    createVertex(vec3(1.0, 1.0, 1.0));
    createVertex(vec3(1.0, -1.0, 1.0));
    EndPrimitive();
}