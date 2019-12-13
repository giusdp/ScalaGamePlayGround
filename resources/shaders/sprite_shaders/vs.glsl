#version 310 es
layout (location = 0) in highp vec3 pos;
layout (location = 1) in highp vec2 tex;

out vec2 tex_coords;

uniform mat4 mvp;

void main(void){
    gl_Position = mvp * vec4(pos, 1.0);
    tex_coords = tex;
}

