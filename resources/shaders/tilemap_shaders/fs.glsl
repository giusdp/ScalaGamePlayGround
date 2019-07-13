#version 420 core

//in vec3 final_color;
in vec2 tex_coords;
out vec4 fragment_color;

uniform sampler2D sampler;

void main(void){
//    fragment_color = vec4(final_color, 1.0);
    fragment_color = texture(sampler,tex_coords);

}


//uniform sampler2DArray tex;
//
//flat in uint fragLayer;
//in vec2 fragTexCoord;
//
//out vec4 colour;
//
//void main()
//{
//    colour = texture( tex, vec3( fragTexCoord, fragLayer ) );
//}