package com.model;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;


public class ObjectTools {
//	static String vertexShaderPath = "src/com/model/vertexShader.vert";
	static String vertexShaderPath = "src/com/model/offsetVertexShader.vert";
	static String fragmentShaderPath = "src/com/model/fragmentShader.frag";
	static int vertexArrayObjId;
	static int vertexBufferObjId;
	static int colorBufferObjId;
	static FloatBuffer vertexBuffer, colorBuffer;

	static Polygon3[] poly3Storage;
	static VColor[] colStorage;
	
	static void addVect3(float minSize, float maxSize) {
		float size = randFloat(minSize, maxSize);
		Polygon3 input = new Polygon3(0.0f, size, size, -size, -size, -size);

		input.addXOffset(randFloat(-1f, 1f));
		input.addYOffset(randFloat(-1f, 1f));

		Polygon3[] poly3TempStorage = new Polygon3[poly3Storage.length + 1];
		VColor[] colorTempStorage = new VColor[colStorage.length + 3];

		System.arraycopy(poly3Storage, 0, poly3TempStorage, 0,
				poly3Storage.length);
		poly3TempStorage[poly3TempStorage.length - 1] = input;
		poly3Storage = poly3TempStorage;

		System.arraycopy(colStorage, 0, colorTempStorage, 0,
				colStorage.length);
		colorTempStorage[colorTempStorage.length - 3] = input.getColor(0);
		colorTempStorage[colorTempStorage.length - 2] = input.getColor(1);
		colorTempStorage[colorTempStorage.length - 1] = input.getColor(2);
		colStorage = colorTempStorage;

		vertexBuffer = BufferUtils.createFloatBuffer(poly3Storage.length * 6);
		colorBuffer = BufferUtils.createFloatBuffer(colStorage.length * 4);
		vertexBuffer.put(Utils.getMultipleVectors3(poly3Storage)); 
		vertexBuffer.rewind(); // rewinded the buffer for reading; position = 0

		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObjId);

		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);

		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0); // setting pointers

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		colorBuffer.put(Utils.getMultipleColors(colStorage));
		colorBuffer.rewind(); // rewinded the buffer for reading; position = 0

		glBindVertexArray(vertexArrayObjId); // binding the vao; we can't send

		glBindBuffer(GL_ARRAY_BUFFER, colorBufferObjId); // binds buffer object

		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);
		glBindVertexArray(0); // unbinding vao
	}
	
	static Polygon3 getClickedPoly(float[] mousePos){
		// to check if a point is inside the triangle, we need to calculate the click's barycentric coordinates
		//alpha, beta, gamma
		
		float alpha, beta, gamma;
		Polygon3 poly;
		for(int i=0;i<poly3Storage.length;i++){
			poly = poly3Storage[i];
			alpha = ((poly.getVertex(1).getY() - poly.getVertex(2).getY()) * (mousePos[0] - poly.getVertex(2).getX()) + (poly.getVertex(2).getX() - poly.getVertex(1).getX()) * (mousePos[1] - poly.getVertex(2).getY())) 
					/ ((poly.getVertex(1).getY() - poly.getVertex(2).getY()) * (poly.getVertex(0).getX() - poly.getVertex(2).getX()) + (poly.getVertex(2).getX() - poly.getVertex(1).getX()) * (poly.getVertex(0).getY() - poly.getVertex(2).getY()));
			
			beta = ((poly.getVertex(2).getY() - poly.getVertex(0).getY()) * (mousePos[0] - poly.getVertex(2).getX()) + (poly.getVertex(0).getX() - poly.getVertex(2).getX()) * (mousePos[1] - poly.getVertex(2).getY())) 
					/ ((poly.getVertex(1).getY() - poly.getVertex(2).getY()) * (poly.getVertex(0).getX() - poly.getVertex(2).getX()) + (poly.getVertex(2).getX() - poly.getVertex(1).getX()) * (poly.getVertex(0).getY() - poly.getVertex(2).getY()));
			gamma = 1.0f - alpha - beta;
			if(alpha > 0 && beta > 0 && gamma > 0 && alpha < 1 && beta < 1 && gamma < 1){
				System.out.println(alpha + " " + beta + " " + gamma);
				return poly;
			}
			poly = null;
		}
		return null;
	}
	
//	static void updateSnow(float downFall, float minSize, float maxSize) {
//
//		float size = ObjectTools.randFloat(minSize, maxSize);
//
//		for (int i = 0; i < poly3Storage.length; i++) {
//			if (poly3Storage[i].B.Y < -1) {
//				poly3Storage[i] = new Polygon3(0.0f, size, size, -size, -size,
//						-size);
//				poly3Storage[i].addXOffset(ObjectTools.randFloat(-1f, 1f));
//				poly3Storage[i].addYOffset(ObjectTools.randFloat(-1f, 1f));
//			} else {
//				poly3Storage[i].addYOffset(downFall);
//			}
//		}
//		updatePolygon3(Utils.getMultipleVectors3(poly3Storage));
//		updateColor(Utils.getMultipleColors(colStorage));
//	}

	static void moveTo(Polygon3 vert, float[] destination) {

		vert.addXOffset(-GameMath.calculateOffset(destination[0],
				destination[1], vert.getVertex(0).getX(), vert.getVertex(0).getY(), 100)[0]);
		vert.addYOffset(-GameMath.calculateOffset(destination[0],
				destination[1], vert.getVertex(0).getX(), vert.getVertex(0).getY(), 100)[1]);
		updatePolygon3(Utils.getMultipleVectors3(poly3Storage));
		updateColor(Utils.getMultipleColors(colStorage));
	}

	static void moveTo(float[] destination) {

		for (int i = 0; i < poly3Storage.length; i++) {
			poly3Storage[i].addXOffset(-GameMath
					.calculateOffset(destination[0], destination[1],
							poly3Storage[i].getVertex(0).getX(), poly3Storage[i].getVertex(0).getY(), 50f)[0]);
			poly3Storage[i].addYOffset(-GameMath
					.calculateOffset(destination[0], destination[1],
							poly3Storage[i].getVertex(0).getX(), poly3Storage[i].getVertex(0).getY(), 50f)[1]);
		}
		updatePolygon3(Utils.getMultipleVectors3(poly3Storage));
		updateColor(Utils.getMultipleColors(colStorage));
	}
	

	static void createRandomPolygon3(int numbers, float minSize, float maxSize) {

		poly3Storage = new Polygon3[numbers];
		colStorage = new VColor[numbers * 3];
		float size;
		for (int i = 0; i < numbers; i++) {
			size = ObjectTools.randFloat(minSize, maxSize);
			poly3Storage[i] = new Polygon3(0.0f, size, size, -size, -size, -size);
			poly3Storage[i].addXOffset(ObjectTools.randFloat(-1f, 1f));
			poly3Storage[i].addYOffset(ObjectTools.randFloat(-1f, 1f));
		}
		
		int polyIndex = 0;
		
		for(int i=0;i < colStorage.length;i+=3){
			colStorage[i] = poly3Storage[polyIndex].getColor(0);
			colStorage[i+1] = poly3Storage[polyIndex].getColor(1);
			colStorage[i+2] = poly3Storage[polyIndex].getColor(2);
			polyIndex++;
		}
		
		Game.numbers = numbers;
		sendPolygon3(Utils.getMultipleVectors3(poly3Storage));
		sendColor(Utils.getMultipleColors(colStorage));
	}

	static void createPolygon3(int numbers, float minSize, float maxSize) {

		poly3Storage = new Polygon3[numbers];
		colStorage = new VColor[numbers * 3];
		float size;
		for (int i = 0; i < numbers; i++) {
			size = ObjectTools.randFloat(minSize, maxSize);
			poly3Storage[i] = new Polygon3(0.0f, size, size, -size, -size, -size);
		}
		
		int polyIndex = 0;
		
		for(int i=0;i < colStorage.length;i+=3){
			colStorage[i] = poly3Storage[polyIndex].getColor(0);
			colStorage[i+1] = poly3Storage[polyIndex].getColor(1);
			colStorage[i+2] = poly3Storage[polyIndex].getColor(2);
			polyIndex++;
		}
		
		Game.numbers = numbers;
		sendPolygon3(Utils.getMultipleVectors3(poly3Storage));
		sendColor(Utils.getMultipleColors(colStorage));
	}
	
	public static void updateRandomPolygon3(float bot, float top) {

		for (int i = 0; i < poly3Storage.length; i++) {
			poly3Storage[i].addXOffset(ObjectTools.randFloat(bot, top));
			poly3Storage[i].addYOffset(ObjectTools.randFloat(bot, top));
		}

		updatePolygon3(Utils.getMultipleVectors3(poly3Storage));
		updateColor(Utils.getMultipleColors(colStorage));
	}

	public static void sendPolygon3(float[] array) { // location 0
		vertexBuffer = BufferUtils.createFloatBuffer(array.length);

		vertexBuffer.put(array); // created a vertex

		vertexBuffer.rewind(); // rewinded the buffer for reading; position = 0

		// VAO = Vertex Array Object (openGL object), a link between shaders and
		// openGL; stores attributes and pointers
		// VBO = Vertex Buffer Objects
		vertexArrayObjId = glGenVertexArrays(); // creating a VAO (ARRAY)
		System.out.println("VAOID:" + vertexArrayObjId);

		glBindVertexArray(vertexArrayObjId);

		vertexBufferObjId = glGenBuffers(); // creating a VBO (BUFFER)
		System.out.println("VBOID:" + vertexBufferObjId);

		// WHERE, WHAT
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObjId); // binds buffer object
															// to specificed
															// target

		// WHERE, WHAT, WHAT WAY
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);

		// glVertexAttribPointer(int index, int size, int type, boolean
		// normalized, int stride, long buffer_buffer_offset)
		// index Specifies the index of the generic vertex attribute to be
		// modified.
		// size Specifies the number of components per generic vertex attribute.
		// Must be 1, 2, 3, 4. Additionally, the symbolic constant GL_BGRA is
		// accepted.
		// type Specifies the data type of each component in the array. The
		// symbolic constants GL_BYTE, GL_UNSIGNED_BYTE, GL_SHORT,
		// GL_UNSIGNED_SHORT, GL_INT, and GL_UNSIGNED_INT, GL_HALF_FLOAT,
		// GL_FLOAT, GL_DOUBLE are accepted. The initial value is GL_FLOAT
		// normalized Specifies whether fixed-point data values should be
		// normalized GL_TRUE or converted directly as fixed-point values
		// GL_FALSE when they are accessed.
		// stride Specifies the byte offset between consecutive generic vertex
		// attributes. If stride is 0, the generic vertex attributes are
		// understood to be tightly packed in the array. The initial value is 0.
		// offset Specifies a offset of the first component of the first generic
		// vertex attribute in the array in the data store of the buffer
		// currently bound to the GL_ARRAY_BUFFER target. The initial value is
		// 0.
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0); // setting pointers
															// in the VAO

		glBindVertexArray(0); // unbinding vao
	}

	public static void updatePolygon3(float[] array) {
		vertexBuffer.clear();
		vertexBuffer.put(array);
		vertexBuffer.rewind();

		// glBindVertexArray(vertexArrayObjId);
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObjId);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuffer);
		// glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

		// glBindVertexArray(0);
	}

	public static void updateColor(float[] array) {
		colorBuffer.clear();
		colorBuffer.put(array);
		colorBuffer.rewind();
		glBindBuffer(GL_ARRAY_BUFFER, colorBufferObjId);
		glBufferSubData(GL_ARRAY_BUFFER, 0, colorBuffer);
	}

	public static void sendColor(float[] array) { // location 1
		colorBuffer = BufferUtils.createFloatBuffer(array.length);
		colorBuffer.put(array); // uploading color x times in the buffer

		colorBuffer.rewind(); // rewinded the buffer for reading; position = 0

		// VAO = Vertex Array Object (openGL object), a link between shaders and
		// openGL; stores attributes and pointers
		// VBO = Vertex Buffer Objects
		colorBufferObjId = glGenBuffers(); // creating a VBO (BUFFER)

		System.out.println("VBOID:" + colorBufferObjId);

		glBindVertexArray(vertexArrayObjId); // binding the vao; we can't send
												// data otherwise

		// WHERE, WHAT
		glBindBuffer(GL_ARRAY_BUFFER, colorBufferObjId); // binds buffer object
															// to specificed
															// target

		// WHERE, WHAT, WHAT WAY
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0); // setting pointers
															// in the VAO
		glBindVertexArray(0); // unbinding vao
	}

	public static float randFloat(float bot, float top) {
		int iBot = (int) (bot * 10000);
		int iTop = (int) (top * 10000);
		int result = (int) (iBot + Math.round((iTop - iBot) * Math.random()));
		return (float) result / 10000;
	}

	public static int randInt(int bot, int top) {
		return (int) (bot + Math.round((top - bot) * Math.random()));
	}

}
