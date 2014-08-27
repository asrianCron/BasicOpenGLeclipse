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

import com.entities.Rectangle;
import com.entities.Shape;
import com.entities.Triangle;


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
	
//	static PolyStorage storage = new PolyStorage();
	static ShapeStorage storage = new ShapeStorage();
	
	static void addTriangle(float minSize, float maxSize) {

		float size = randFloat(minSize, maxSize);
		Shape input = new Triangle(new Vertex(0.0f, size), new Vertex(size, -size),new Vertex( -size, -size));

		input.addXOffset(randFloat(-1f, 1f));
		input.addYOffset(randFloat(-1f, 1f));
		
//		storage.addPolygon(input);
		storage.addShape(input);
		
		sendPolygons(storage.getStorage().getStoredPolyArray());
		sendColors(storage.getStorage().getStoredVColorArray());
	}

//	static void addQuad(float minSize, float maxSize) {
//
//		float size = randFloat(minSize, maxSize);
//		Polygon input = new Quad(-size/2,size/2,size/2,size/2,size/2,-size/2,-size/2,-size/2);
//
////		input.addXOffset(randFloat(-1f, 1f));
////		input.addYOffset(randFloat(-1f, 1f));
//		
//		storage.addPolygon(input);
//
//		sendPolygons(storage.getStoredPolyArray());
//		sendColors(storage.getStoredVColorArray());
//	}	
	
	static void addQuad(float minSize, float maxSize) {

		float size = randFloat(minSize, maxSize);
		Shape input = new Rectangle(new Vertex(-size/2,size/2),new Vertex(size/2,size/2),new Vertex(size/2,-size/2),new Vertex(-size/2,-size/2));

//		input.addXOffset(randFloat(-1f, 1f));
//		input.addYOffset(randFloat(-1f, 1f));
		
//		storage.addPolygon(input);
		
		storage.addShape(input);
		
		sendPolygons(storage.getStorage().getStoredPolyArray());
		sendColors(storage.getStorage().getStoredVColorArray());
	}	
	
	static Object[] getClickedShape(float[] mousePos, float range){
		// to check if a point is inside the triangle, we need to calculate the click's barycentric coordinates
		//alpha, beta, gamma
		
		float alpha; // how close mousePos is to poly.A
		float beta;  // how close mousePos is to poly.B
		float gamma; // how close mousePos is to poly.C
		Shape poly;
		for(int i=0;i<storage.size();i++){ // problem here
			poly = storage.getShape(i);
			for(int j=0;j<poly.getPolys().length;j++){
				alpha = ((poly.getPolys()[j].getVertex(1).getY() - poly.getPolys()[j].getVertex(2).getY()) * (mousePos[0] - poly.getPolys()[j].getVertex(2).getX()) + (poly.getPolys()[j].getVertex(2).getX() - poly.getPolys()[j].getVertex(1).getX()) * (mousePos[1] - poly.getPolys()[j].getVertex(2).getY())) 
						/ ((poly.getPolys()[j].getVertex(1).getY() - poly.getPolys()[j].getVertex(2).getY()) * (poly.getPolys()[j].getVertex(0).getX() - poly.getPolys()[j].getVertex(2).getX()) + (poly.getPolys()[j].getVertex(2).getX() - poly.getPolys()[j].getVertex(1).getX()) * (poly.getPolys()[j].getVertex(0).getY() - poly.getPolys()[j].getVertex(2).getY()));
				
				beta = ((poly.getPolys()[j].getVertex(2).getY() - poly.getPolys()[j].getVertex(0).getY()) * (mousePos[0] - poly.getPolys()[j].getVertex(2).getX()) + (poly.getPolys()[j].getVertex(0).getX() - poly.getPolys()[j].getVertex(2).getX()) * (mousePos[1] - poly.getPolys()[j].getVertex(2).getY())) 
						/ ((poly.getPolys()[j].getVertex(1).getY() - poly.getPolys()[j].getVertex(2).getY()) * (poly.getPolys()[j].getVertex(0).getX() - poly.getPolys()[j].getVertex(2).getX()) + (poly.getPolys()[j].getVertex(2).getX() - poly.getPolys()[j].getVertex(1).getX()) * (poly.getPolys()[j].getVertex(0).getY() - poly.getPolys()[j].getVertex(2).getY()));
				gamma = 1.0f - alpha - beta;
				
				if(alpha > 0-range && beta > 0-range && gamma > 0-range && alpha < 1+range && beta < 1+range && gamma < 1+range){
//					System.out.println(alpha + " " + beta + " " + gamma);
					Object[] result = new Object[4];
					result[0] = poly;
					result[1] = alpha;
					result[2] = gamma;
					return result;
				}
				
			}

//			System.out.println(alpha + " " + beta + " " + gamma);
		}
		return null;
	}
		
	
	static void moveShapesTo(float[] destination) {


		for(int i=0; i < storage.size();i++){
			for(int j=0;j<storage.getShape(i).getPolys().length;j++){
				storage.getShape(i).getPolys()[j].addXOffset(-GameMath.calculateOffset(destination[0], destination[1], storage.getShape(i).getCentroid().getX(), storage.getShape(i).getCentroid().getX(),50f)[0]);
				storage.getShape(i).getPolys()[j].addYOffset(-GameMath.calculateOffset(destination[0], destination[1], storage.getShape(i).getCentroid().getY(), storage.getShape(i).getCentroid().getY(),50f)[1]);
			}
		}
		storage.updateStorage();
		updatePolygons(storage.getStorage().getStoredPolyArray());
		updateColors(storage.getStorage().getStoredVColorArray());
		
	}

	static void createRandomlyPlacedTriangles(int numbers, float minSize, float maxSize) {
		float size;
		for (int i = 0; i < numbers; i++) {
			size = ObjectTools.randFloat(minSize, maxSize);
			storage.addShape(new Triangle(new Vertex(0.0f, size), new Vertex(size, -size),new Vertex( -size, -size)));
			storage.getShape(i).addXOffset(ObjectTools.randFloat(-1f, 1f));
			storage.getShape(i).addYOffset(ObjectTools.randFloat(-1f, 1f));
		}
		ObjectTools.storage.updateStorage();
		Game.numbers = numbers;
		sendPolygons(storage.getStorage().getStoredPolyArray());
		sendColors(storage.getStorage().getStoredVColorArray());
	}

	static void createTriangles(int numbers, float minSize, float maxSize) {

		float size;
		for (int i = 0; i < numbers; i++) {
			size = ObjectTools.randFloat(minSize, maxSize);
			storage.addShape(new Triangle(new Vertex(0.0f, size), new Vertex(size, -size),new Vertex( -size, -size)));
		}
		
		Game.numbers = numbers;
		sendPolygons(storage.getStorage().getStoredPolyArray());
		sendColors(storage.getStorage().getStoredVColorArray());
	}
	
	public static void randomlyMoveAllShapes(float bot, float top) {

		for (int i = 0; i < storage.size(); i++) {
			for(int j=0;j<storage.getShape(i).getPolys().length;j++){
				
				storage.getShape(i).getPolys()[j].addXOffset(ObjectTools.randFloat(bot, top));
				storage.getShape(i).getPolys()[j].addYOffset(ObjectTools.randFloat(bot, top));

			}
		}
		storage.updateStorage();
		updatePolygons(storage.getStorage().getStoredPolyArray());
		updateColors(storage.getStorage().getStoredVColorArray());
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
		colorBufferObjId = glGenBuffers(); // creating a VBO (BUFFER)

		System.out.println("VBOID:" + colorBufferObjId);

		glBindVertexArray(vertexArrayObjId); // binding the vao; we can't send
												// data otherwise

		// WHERE, WHAT
		glBindBuffer(GL_ARRAY_BUFFER, colorBufferObjId); // binds buffer object

		// WHERE, WHAT, WHAT WAY
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0); // setting pointers in the VAO
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
