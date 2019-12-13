#version 310 es
layout (location = 0) in highp vec3 pos;

uniform mediump mat4 mvp;

uniform highp vec2 tex_offsets[4];

out vec2 tex_coords;

void main(void){
    gl_Position = mvp * vec4(pos, 1.0);
    tex_coords = tex_offsets[gl_VertexID];
}