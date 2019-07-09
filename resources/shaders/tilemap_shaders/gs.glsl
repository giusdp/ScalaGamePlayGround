#version 330
layout (points) in;
layout (triangle_strip, max_vertices = 4) out;

in vec3 color[];
out vec3 final_color;

uniform mat4 mvp;
const float size = 16;

void createVertex(vec2 offset) {
    vec4 actual_offset = vec4(offset.x * size, offset.y * size, 0.0, 0.0);
    vec4 world_position = vec4(gl_in[0].gl_Position + actual_offset);
    gl_Position = mvp * world_position;
    final_color = color[0];
    EmitVertex();
}

void main() {
    createVertex(vec2(-1.0, 1.0));
    createVertex(vec2(-1.0, -1.0));
    createVertex(vec2(1.0, 1.0));
    createVertex(vec2(1.0, -1.0));
    EndPrimitive();
}