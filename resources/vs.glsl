#version 420 core
layout (location = 0) in vec3 pos;

out vec3 color;

void main(void){
    gl_Position = vec4(pos, 1.0);
    color = vec3(pos.x + 0.5, 1.0, pos.y+0.5);
}

