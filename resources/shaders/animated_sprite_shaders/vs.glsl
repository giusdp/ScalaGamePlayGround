#version 330 core
layout (location = 0) in vec3 pos;
layout (location = 3) in vec2 tex_offset;

uniform mat4 mvp;
uniform float sprite_width;
uniform float sprite_height;

out vec2 tex_coords;

void main(void){
    gl_Position = mvp * vec4(pos, 1.0);
    if (gl_VertexID == 0){ tex_coords = tex_offset; }
    else if (gl_VertexID == 1){ tex_coords = vec2(tex_offset.x, tex_offset.y + sprite_height); }
    else if (gl_VertexID == 2){ tex_coords = vec2(tex_offset.x + sprite_width, tex_offset.y + sprite_height);}
    else { tex_coords = vec2(tex_offset.x + sprite_width, tex_offset.y); }
}