#version 330 core

in vec2 texCoord;

out vec4 color;

uniform sampler2D textureSampler;
uniform bool debug;
void main()
{
    if (debug)
    {
        color = vec4(1.0, 0.0, 0.0, 1.0);
    }
    else
    {
        color = texture(textureSampler, texCoord);
    }
}