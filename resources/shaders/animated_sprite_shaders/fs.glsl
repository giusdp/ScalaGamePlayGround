#version 330 core

in vec2 tex_coords;
out vec4 fragment_color;

uniform sampler2D sampler;

void main(void){
    fragment_color = texture(sampler,tex_coords);
}
