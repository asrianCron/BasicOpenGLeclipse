#version 330 core

layout(location = 0) in vec2 vertex;
layout(location = 1) in vec4 col;

uniform vec2 offset;

out vec4 color;

void main(){
	
	vec4 totalOffset = vec4(offset.x, offset.y, 0.0, 0.0); // vec4() = constructor
	vec4 convertedVertex = vec4(vertex, 0.0, 1.0);
	gl_Position = convertedVertex + totalOffset;
		
	color = col;
}