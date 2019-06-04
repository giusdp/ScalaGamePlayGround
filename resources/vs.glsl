#version 420 core
layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tex;

out vec2 tex_coords;


void main(void){
    gl_Position = vec4(pos, 1.0);
    tex_coords = tex;
}

