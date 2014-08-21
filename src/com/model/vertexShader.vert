#version 330 core // using GLSL version 330 in core profile

// layout : from where the information comes from
// location : the adress of the location to read the data from

layout(location = 0) in vec2 vertex;  // read a 2d vector from location 0
layout(location = 1) in vec4 col;

out vec4 color;

void main()
{
    color = col; // sending the info passed from col to color; in -> out
    gl_Position = vec4(vertex, 0.0, 1.0); // converting the vertex(vec2) to a 4D vector : 0.0 for z component; 1.0 for w component
    // result is assigned to gl_Position to use it in later stages of the rendering pipeline
}