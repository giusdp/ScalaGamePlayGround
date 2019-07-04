#version 420 core

in vec2 tex_coords;
out vec4 out_Color;

uniform sampler2D texture_sampler;

void main(void){
    out_Color = texture(texture_sampler, tex_coords);
}