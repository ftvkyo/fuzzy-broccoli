#version 330 core
in vec4 vPos;
in vec4 vColorMain;
in vec4 vColorAlternative;
out vec4 FragColor;
void main()
{
    if (length(vPos.xyz) > 0.2) {
        FragColor = vColorMain;
    } else {
        FragColor = vColorAlternative;
    }
}
