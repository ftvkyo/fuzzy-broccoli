#version 330 core
in vec3 vertex;
out vec4 vPos;
out vec4 vColorMain;
out vec4 vColorAlternative;
void main()
{
    gl_Position = vec4(vertex.xyz, 1.0);
    vPos = vec4(vertex.xyz, 1.0);
    vColorMain = vec4((vPos.yzx + 1) / 2, 1.0);
    vColorAlternative = vec4((vPos.xyz + 1) / 2, 1.0);
}
