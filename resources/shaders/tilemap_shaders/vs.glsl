#version 330 core
layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tex;
layout (location = 2) in vec3 offset;

uniform mat4 mvp;

out vec3 final_color;
//out vec2 tex_coords;

void main(void){
    gl_Position = mvp * vec4(pos + offset, 1.0);
    final_color = vec3(pos.x + offset.x, pos.y + offset.y, 0.5);
//    tex_coords = tex;
}