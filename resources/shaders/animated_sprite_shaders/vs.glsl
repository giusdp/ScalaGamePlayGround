#version 330 core
layout (location = 0) in vec3 pos;

uniform mat4 mvp;
uniform float sprite_width;
uniform float sprite_height;

uniform vec2 tex_offsets[4];

out vec2 tex_coords;

void main(void){
    gl_Position = mvp * vec4(pos, 1.0);
    tex_coords = tex_offsets[gl_VertexID];
}