#version 330 core

in vec4 color;
out vec4 outputColor;

void main()
{
    outputColor = color;

    //color = vec4(1.0, 1.0, 1.0, 0.0); // declaring 4D vector to hold R G B A
    // assigned as output, output from the fragment shader
        // color = white;
}
