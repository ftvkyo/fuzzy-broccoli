#version 330 core
layout (location = 0) in vec3 vertex;
layout (location = 1) in vec2 texture;
out vec2 texCoord;
void main()
{
    gl_Position = vec4(vertex.xyz, 1.0);
    texCoord = texture;
}
