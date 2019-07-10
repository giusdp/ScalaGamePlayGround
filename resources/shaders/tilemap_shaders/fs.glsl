#version 330 core

in vec3 final_color;
out vec4 fragment_color;

uniform sampler2D sampler;

void main(void){
    fragment_color = vec4(final_color, 1.0);
//    fragment_color = texture(sampler, tex_coords);
}

//in vec2 tex_coords;
//out vec4 out_Color;
//
//uniform sampler2D sampler;
//
//void main(void){
//    out_Color = texture(sampler, tex_coords);
//}