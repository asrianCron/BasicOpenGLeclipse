package com.model;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.PixelFormat;

public class Game {

	static int width = 800;
	static int height = 600;
	static float mouseX, mouseY;
	static boolean running = false;
	static PixelFormat pFormat;
	static ContextAttribs cAttrib;
	static ShaderProgram shProg;
	static int numbers = 0;
	static long lastFrame;
	static long currentFrame;

	private static float[] mousePos;

	static float[] congregationPoint = new float[] { 0f, 0f };
	static float[] randomArea = new float[] { 0.009f, -0.009f };
	static long interval = 10l;

	public static void start() {
		Game.init();
		int numbers = 1000000;
		float minSize = 0.02f;
		float maxSize = 0.06f;
		ObjectTools.createRandomVector3(numbers, minSize, maxSize);
//		ObjectTools.createVector3(1, 0.2f, 0.2f);
		lastFrame = System.currentTimeMillis();
		gameLoop();
	}

	private static void gameLoop() {

		while (running) {
			mousePos = getMousePos();
			Input.update();
			currentFrame = System.currentTimeMillis();
			if (checkFrame(interval)) {
				lastFrame = System.currentTimeMillis();
//				ObjectTools.moveTo(congregationPoint);
//				ObjectTools.updateRandomVector3(randomArea[0], randomArea[1]);
			}

			checkInput();

//			System.out.println("@OBJECTS:" + ObjectTools.vec3Storage.length);
			// System.out.println(mousePos[0] + " " + mousePos[1]);
			render();

			if (Display.wasResized()) {
				resize();
			}
			Display.update();
			Display.sync(60);
			if (!running || Display.isCloseRequested()
					|| Input.isKeyPressed(Input.KEY_ESCAPE)) {
				Game.exit();
			}
		}
	}

	private static void checkInput() {
		if (Input.isKeyDown(Input.KEY_W)) {
			congregationPoint[1] += 0.03f;
		}
		if (Input.isKeyDown(Input.KEY_S)) {
			congregationPoint[1] -= 0.03f;
		}
		if (Input.isKeyDown(Input.KEY_A)) {
			congregationPoint[0] -= 0.03f;
		}
		if (Input.isKeyDown(Input.KEY_D)) {
			congregationPoint[0] += 0.03f;
		}
		if (Input.isMouseDown(0)) {
			randomArea[0] = -0.09f;
			randomArea[1] = 0.09f;
		} else {
			randomArea[0] = -0.009f;
			randomArea[1] = 0.009f;
		}
		if (Input.isMouseDown(1)) {
			congregationPoint[0] = getMousePos()[0];
			congregationPoint[1] = getMousePos()[1];
		}
	}

	private static boolean checkFrame(long interval) {
		return currentFrame > (lastFrame + interval);
	}

	private static float[] getMousePos() { // [0] = mouse.GetX(), [1] =
											// mouse.GetY()
		float[] result = new float[2];
		result[0] = Utils.convertNeutre(Mouse.getX(), width);
		result[1] = Utils.convertNeutre(Mouse.getY(), height);
		return result;
	}

	public static void snapShot(ByteBuffer buff) {
		glReadBuffer(GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		int bytesPerPixel = 4; // R G B A
		buff = BufferUtils.createByteBuffer(width * height * bytesPerPixel);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buff);
	}

	public static void createDisplay() {
		pFormat = new PixelFormat();
		cAttrib = new ContextAttribs(3, 2).withForwardCompatible(false)
				.withProfileCore(true);
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(pFormat, cAttrib);
			Display.setResizable(true);
		} catch (LWJGLException ex) {
			ex.printStackTrace();
		}
	}

	public static void resize() {
		width = Display.getWidth();
		height = Display.getHeight();
		glViewport(0, 0, width, height);
	}

	public static void init() {
		running = true;
		Game.createDisplay();
		shProg = new ShaderProgram();
		shProg.init();
		shProg.attachVertexShader(ObjectTools.vertexShaderPath);
		shProg.attachFragmentShader(ObjectTools.fragmentShaderPath);
		shProg.link();
		shProg.initUniform();
		try {
			Keyboard.create();
			Keyboard.enableRepeatEvents(true);
		} catch (LWJGLException ex) {
			ex.printStackTrace();
		}
//		glEnable(GL_POINT_SPRITE);
//		glEnable(GL_BLEND);

	}

	public static void render() {
		initRender();
		
		
//		glUniform2f(shProg.uniformOffsetLocation, -GameMath.calculateOffset(congregationPoint[0], congregationPoint[1], ObjectTools.vec3Storage[0].A.X, ObjectTools.vec3Storage[0].A.Y, 1f)[0], -GameMath.calculateOffset(congregationPoint[0], congregationPoint[1], ObjectTools.vec3Storage[0].A.X, ObjectTools.vec3Storage[0].A.Y, 1f)[1]);
		
		// glDrawArrays(int mode, int first, int count)
		glDrawArrays(GL_TRIANGLES, 0, ObjectTools.vec3Storage.length * 3); // drawing
		// vertex x
		// 3
//		glDrawArrays(GL_POINTS, 0, ObjectTools.vec3Storage.length * 3); // drawing
																		// point
		
		cleanupRender();
	}
	
	public static void initRender(){
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // screen clear color
		glClear(GL_COLOR_BUFFER_BIT); // clears the screen

		shProg.bind();
		glBindVertexArray(ObjectTools.vertexArrayObjId);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

	}
	
	public static void cleanupRender(){
		glDisableVertexAttribArray(0); // disabling location 0
		glDisableVertexAttribArray(1); // disabling location 1
		glBindVertexArray(0); // unbinding vao
		shProg.unBind();
	}
	
	public static long getTime() {
		return System.currentTimeMillis();
	}

	public static void dispose() {
		shProg.dispose();
		glBindVertexArray(0);
		glDeleteVertexArrays(ObjectTools.vertexArrayObjId);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(ObjectTools.vertexBufferObjId);
		glDeleteBuffers(ObjectTools.colorBufferObjId);
	}

	public static void exit() {
		running = false;
		Game.dispose();
		Display.destroy();
	}

}
