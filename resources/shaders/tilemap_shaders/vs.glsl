#version 420 core
layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texs;
layout (location = 2) in vec3 pos_offset;
layout (location = 3) in vec2 tex_offset;

uniform mat4 mvp;

out vec2 tex_coords;

void main(void){
    int corner = gl_VertexID % 4;
    gl_Position = mvp * vec4(pos + pos_offset, 1.0);
    if (texs.x == 0 && texs.y == 0){
        tex_coords = tex_offset;
    }
    else if (texs.x == 0 && texs.y == 1){
        tex_coords = vec2(tex_offset.x, tex_offset.y*2);
    }
    else if (texs.x == 1 && texs.y == 1){
        tex_coords = vec2(0.15f, 0.041666668f*2);
        //        tex_coords = vec2(texs.x + (0.15 - 1), texs.y + (0.083333336 - 1));
    } else {
        tex_coords = vec2(0.15f, 0.041666668f);
    }
}
    //    if (corner == 0) {
    //        tex_coords = vec2(uv.x + 0.1, uv.y + (0.083333336 - 1));
    //    }
    //    else if (corner == 1) {
    //        tex_coords = vec2(uv.x + 0.1 , uv.y + 0.041666668);
    //    }else if (corner == 2) {
    //        tex_coords = vec2(uv.x + (0.15 - 1), uv.y + 0.041666668);
    //    }else if (corner == 3) {
    //        tex_coords = vec2(uv.x + (0.15 - 1), uv.y + (0.083333336 - 1));
    //    }

