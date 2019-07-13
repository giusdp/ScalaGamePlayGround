#version 420 core
layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texs;
layout (location = 2) in vec3 pos_offset;
layout (location = 3) in vec2 tex_offset;

uniform mat4 mvp;
uniform float tile_width;
uniform float tile_height;

out vec2 tex_coords;

void main(void){
    gl_Position = mvp * vec4(pos + pos_offset, 1.0);
    if (texs.x == 0 && texs.y == 0){ tex_coords = tex_offset; }
    else if (texs.x == 0 && texs.y == 1){ tex_coords = vec2(tex_offset.x, tex_offset.y+tile_height); }
    else if (texs.x == 1 && texs.y == 1){ tex_coords = vec2(tex_offset.x+tile_width, tex_offset.y+tile_height);}
    else { tex_coords = vec2(tex_offset.x+tile_width, tex_offset.y); }
}