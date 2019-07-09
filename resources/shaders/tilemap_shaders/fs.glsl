#version 330 core

in vec3 final_color;
out vec4 out_Color;
void main(void){
    out_Color = vec4(final_color, 1.0);
}