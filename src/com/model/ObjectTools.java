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
	static FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(0);
	static FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(0);

//	static Polygon[] polyStorage = new Polygon[0];
//	static VColor[] colStorage = new VColor[0];
	
	static PolyStorage storage = new PolyStorage();
	
	
	static void addNewVect3(float minSize, float maxSize) {

		float size = randFloat(minSize, maxSize);
		Polygon input = new Polygon(0.0f, size, size, -size, -size, -size);

		input.addXOffset(randFloat(-1f, 1f));
		input.addYOffset(randFloat(-1f, 1f));
		
		storage.addPolygon(input);

		sendPolygons(storage.getStoredPolyArray());
		sendColors(storage.getStoredVColorArray());
	}

	static void addQuad(float minSize, float maxSize) {

		float size = randFloat(minSize, maxSize);
		Polygon input = new Quad(-size/2,size/2,size/2,size/2,size/2,-size/2,-size/2,-size/2);

//		input.addXOffset(randFloat(-1f, 1f));
//		input.addYOffset(randFloat(-1f, 1f));
		
		storage.addPolygon(input);

		sendPolygons(storage.getStoredPolyArray());
		sendColors(storage.getStoredVColorArray());
	}	
	
	static Object[] getClickedPoly(float[] mousePos, float range){
		// to check if a point is inside the triangle, we need to calculate the click's barycentric coordinates
		//alpha, beta, gamma
		
		float alpha; // how close mousePos is to poly.A
		float beta;  // how close mousePos is to poly.B
		float gamma; // how close mousePos is to poly.C
		Polygon poly;
		for(int i=0;i<storage.getPolyCount();i++){ // problem here
			poly = storage.getPolygon(i);
			alpha = ((poly.getVertex(1).getY() - poly.getVertex(2).getY()) * (mousePos[0] - poly.getVertex(2).getX()) + (poly.getVertex(2).getX() - poly.getVertex(1).getX()) * (mousePos[1] - poly.getVertex(2).getY())) 
					/ ((poly.getVertex(1).getY() - poly.getVertex(2).getY()) * (poly.getVertex(0).getX() - poly.getVertex(2).getX()) + (poly.getVertex(2).getX() - poly.getVertex(1).getX()) * (poly.getVertex(0).getY() - poly.getVertex(2).getY()));
			
			beta = ((poly.getVertex(2).getY() - poly.getVertex(0).getY()) * (mousePos[0] - poly.getVertex(2).getX()) + (poly.getVertex(0).getX() - poly.getVertex(2).getX()) * (mousePos[1] - poly.getVertex(2).getY())) 
					/ ((poly.getVertex(1).getY() - poly.getVertex(2).getY()) * (poly.getVertex(0).getX() - poly.getVertex(2).getX()) + (poly.getVertex(2).getX() - poly.getVertex(1).getX()) * (poly.getVertex(0).getY() - poly.getVertex(2).getY()));
			gamma = 1.0f - alpha - beta;
			if(alpha > 0-range && beta > 0-range && gamma > 0-range && alpha < 1+range && beta < 1+range && gamma < 1+range){
				System.out.println(alpha + " " + beta + " " + gamma);
				Object[] result = new Object[4];
				result[0] = poly;
				result[1] = alpha;
				result[2] = gamma;
				return result;
			}
//			System.out.println(alpha + " " + beta + " " + gamma);
			poly = null;
		}
		return null;
	}
		
	
	static void moveTo(Polygon vert, float[] destination) {

		vert.addXOffset(-GameMath.calculateOffset(destination[0],
				destination[1], vert.getVertex(0).getX(), vert.getVertex(0).getY(), 100)[0]);
		vert.addYOffset(-GameMath.calculateOffset(destination[0],
				destination[1], vert.getVertex(0).getX(), vert.getVertex(0).getY(), 100)[1]);
		storage.updateStorage();
		updatePolygons(storage.getStoredPolyArray());
		updateColors(storage.getStoredVColorArray());
	}

	static void moveTo(float[] destination) {

		for (int i = 0; i < storage.getShapeCount(); i++) {
			storage.getPolygon(i).addXOffset(-GameMath
					.calculateOffset(destination[0], destination[1],
							storage.getPolygon(i).getVertex(0).getX(), storage.getPolygon(i).getVertex(0).getY(), 50f)[0]);
			storage.getPolygon(i).addYOffset(-GameMath
					.calculateOffset(destination[0], destination[1],
							storage.getPolygon(i).getVertex(0).getX(), storage.getPolygon(i).getVertex(0).getY(), 50f)[1]);
		}
		storage.updateStorage();
		updatePolygons(storage.getStoredPolyArray());
		updateColors(storage.getStoredPolyArray());
	}

	static void createRandomPolygon3(int numbers, float minSize, float maxSize) {

		float size;
		for (int i = 0; i < numbers; i++) {
			size = ObjectTools.randFloat(minSize, maxSize);
			storage.addPolygon(new Polygon(0.0f, size, size, -size, -size, -size));
			storage.getPolygon(i).addXOffset(ObjectTools.randFloat(-1f, 1f));
			storage.getPolygon(i).addYOffset(ObjectTools.randFloat(-1f, 1f));
		}
		
		int polyIndex = 0;
		Game.numbers = numbers;
		sendPolygons(storage.getStoredPolyArray());
		sendColors(storage.getStoredVColorArray());
	}

	static void createPolygon3(int numbers, float minSize, float maxSize) {

		float size;
		for (int i = 0; i < numbers; i++) {
			size = ObjectTools.randFloat(minSize, maxSize);
			storage.addPolygon(new Polygon(0.0f, size, size, -size, -size, -size));
		}
		
		Game.numbers = numbers;
		sendPolygons(storage.getStoredPolyArray());
		sendColors(storage.getStoredVColorArray());
	}
	
	public static void updateRandomPolygon3(float bot, float top) {

		for (int i = 0; i < storage.getShapeCount(); i++) {
			storage.getPolygon(i).addXOffset(ObjectTools.randFloat(bot, top));
			storage.getPolygon(i).addYOffset(ObjectTools.randFloat(bot, top));
		}
		storage.updateStorage();
		updatePolygons(storage.getStoredPolyArray());
		updateColors(storage.getStoredVColorArray());
	}

	public static void sendPolygons(float[] array) { // location 0
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

	public static void updatePolygons(float[] array) {

		vertexBuffer.clear();
		vertexBuffer.put(array);
		vertexBuffer.rewind();

		// glBindVertexArray(vertexArrayObjId);
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObjId);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuffer);
		// glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

		// glBindVertexArray(0);
	}

	public static void updateColors(float[] array) {

		colorBuffer.clear();
		colorBuffer.put(array);
		colorBuffer.rewind();
		glBindBuffer(GL_ARRAY_BUFFER, colorBufferObjId);
		glBufferSubData(GL_ARRAY_BUFFER, 0, colorBuffer);
	}

	public static void sendColors(float[] array) { // location 1

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
