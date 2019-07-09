#version 330 core

in vec2 tex_coords;
out vec4 out_Color;

uniform sampler2D sampler;

void main(void){
    out_Color = texture(sampler, tex_coords);
}