/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;

/**
 *
 * @author asrianCron
 */
public class ShaderProgram {

	int programId;
	int vertexShaderId;
	int fragmentShaderId;
	int uniformOffsetLocation;
	String shaderSource;

	public ShaderProgram() {

	}

	// Vertex shader
	// Fragment shader
	// Geometry shader
	public boolean init() {
		programId = glCreateProgram();
		return programId != 0;
	}

	public void attachVertexShader(String path) {

		shaderSource = Utils.loadFile(path); // file = single string
		vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderId, shaderSource);
		glCompileShader(vertexShaderId);

		if (glGetShaderi(vertexShaderId, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println("FAILED TO LOAD VERTEX SHADER");
			glGetShaderInfoLog(vertexShaderId,
					glGetShaderi(vertexShaderId, GL_INFO_LOG_LENGTH));
			dispose();
			Game.exit();
		}
		glAttachShader(programId, vertexShaderId);
	}

	public void attachFragmentShader(String path) {

		shaderSource = Utils.loadFile(path); // file = single string
		fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderId, shaderSource);
		glCompileShader(fragmentShaderId);

		if (glGetShaderi(fragmentShaderId, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println("FAILED TO LOAD FRAGMENT SHADER");
			glGetShaderInfoLog(fragmentShaderId,
					glGetShaderi(fragmentShaderId, GL_INFO_LOG_LENGTH));
			dispose();
			Game.exit();
		}
		glAttachShader(programId, fragmentShaderId);
	}

	public void link() {
		glLinkProgram(programId); // linking the program
		if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
			System.out.println("FAILED TO LINK SHADER");
			dispose();
			Game.exit();
		}
	}

	public void bind() {
		glUseProgram(programId); // binding the program to openGl
	}

	public void unBind() {
		glUseProgram(0); // 0 unbinds current shader
	}

	public void dispose() {
		unBind();
		glDetachShader(programId, vertexShaderId);
		glDetachShader(programId, fragmentShaderId);
		glDeleteShader(vertexShaderId);
		glDeleteShader(fragmentShaderId);
		glDeleteProgram(programId);
	}

	public void initUniform() {
		uniformOffsetLocation = glGetUniformLocation(programId, "offset");
		if (uniformOffsetLocation == -1) {
			System.err.println("ERROR RETRIEVING OFFSET UNIFORM LOCATION");
		} else {
			System.out.println("@UNIFORM LOCATION: " + uniformOffsetLocation);
		}
	}

}
