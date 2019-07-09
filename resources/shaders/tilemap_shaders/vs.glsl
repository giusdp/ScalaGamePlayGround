#version 330 core
layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tex;

out vec3 color;

void main(void){
    gl_Position = vec4(pos, 1.0);
    color = vec3(pos.x + 0.2, pos.y + 0.3, pos.z+0.5);
}

