#version 310 es

in highp vec2 tex_coords;
out highp vec4 fragment_color;

uniform sampler2D sampler;

void main(void){
    fragment_color = texture(sampler,tex_coords);
}
