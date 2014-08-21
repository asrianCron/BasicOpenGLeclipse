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

//b 126 cod

public class ObjectTools {

	static int vertexArrayObjId;
	static int vertexBufferObjId;
	static int colorBufferObjId;
	static List<Vector3> vect3List = new ArrayList<>();
	static List<V3Color> vect3ColorList = new ArrayList<>();
	static FloatBuffer vertexBuffer, colorBuffer;

	static Vector3[] vec3Storage;
	static V3Color[] col3Storage;

	static void addVect3(float minSize, float maxSize) {
		float size = randFloat(minSize, maxSize);
		Vector3 input = new Vector3(0.0f, size, size, -size, -size, -size);

		input.addXOffset(randFloat(-1f, 1f));
		input.addYOffset(randFloat(-1f, 1f));
		input.save();

		Vector3[] vect3TempStorage = new Vector3[vec3Storage.length + 1];
		V3Color[] color3TempStorage = new V3Color[col3Storage.length + 1];

		System.arraycopy(vec3Storage, 0, vect3TempStorage, 0,
				vec3Storage.length);
		vect3TempStorage[vect3TempStorage.length - 1] = input;
		vec3Storage = vect3TempStorage;

		System.arraycopy(col3Storage, 0, color3TempStorage, 0,
				col3Storage.length);
		color3TempStorage[color3TempStorage.length - 1] = input.color;
		col3Storage = color3TempStorage;

		vertexBuffer = BufferUtils.createFloatBuffer(vec3Storage.length * 6);
		colorBuffer = BufferUtils.createFloatBuffer(col3Storage.length * 4 * 3);
		vertexBuffer.put(Utils.getMultipleVectors3(vec3Storage)); 
		vertexBuffer.rewind(); // rewinded the buffer for reading; position = 0

		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObjId);

		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);

		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0); // setting pointers

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		colorBuffer.put(Utils.getMultipleColors(col3Storage));
		colorBuffer.rewind(); // rewinded the buffer for reading; position = 0

		glBindVertexArray(vertexArrayObjId); // binding the vao; we can't send

		glBindBuffer(GL_ARRAY_BUFFER, colorBufferObjId); // binds buffer object

		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);
		glBindVertexArray(0); // unbinding vao
	}

	static void updateSnow(float downFall, float minSize, float maxSize) {

		float size = ObjectTools.randFloat(minSize, maxSize);

		for (int i = 0; i < vec3Storage.length; i++) {
			if (vec3Storage[i].B.Y < -1) {
				vec3Storage[i] = new Vector3(0.0f, size, size, -size, -size,
						-size);
				vec3Storage[i].addXOffset(ObjectTools.randFloat(-1f, 1f));
				vec3Storage[i].addYOffset(ObjectTools.randFloat(-1f, 1f));
			} else {
				vec3Storage[i].addYOffset(downFall);
			}
		}
		updateVector3(Utils.getMultipleVectors3(vec3Storage));
		updateColor(Utils.getMultipleColors(col3Storage));
	}

	static void moveTo(Vector3 vert, float[] destination) {

		// vert.addXOffset((float) (GameMath.getSlope(vert.A.X, vert.A.Y,
		// destination[0], destination[1])) / 100);
		// vert.addYOffset((float) (GameMath.getSlope(vert.A.X, vert.A.Y,
		// destination[0], destination[1])) / 100);

		vert.addXOffset(-GameMath.calculateOffset(destination[0],
				destination[1], vert.A.X, vert.A.Y, 100)[0]);
		vert.addYOffset(-GameMath.calculateOffset(destination[0],
				destination[1], vert.A.X, vert.A.Y, 100)[1]);
		updateVector3(Utils.getMultipleVectors3(vec3Storage));
		updateColor(Utils.getMultipleColors(col3Storage));
	}

	static void moveTo(float[] destination) {

		// vert.addXOffset((float) (GameMath.getSlope(vert.A.X, vert.A.Y,
		// destination[0], destination[1])) / 100);
		// vert.addYOffset((float) (GameMath.getSlope(vert.A.X, vert.A.Y,
		// destination[0], destination[1])) / 100);

		for (int i = 0; i < vec3Storage.length; i++) {
			vec3Storage[i].addXOffset(-GameMath
					.calculateOffset(destination[0], destination[1],
							vec3Storage[i].A.X, vec3Storage[i].A.Y, 50f)[0]);
			vec3Storage[i].addYOffset(-GameMath
					.calculateOffset(destination[0], destination[1],
							vec3Storage[i].A.X, vec3Storage[i].A.Y, 50f)[1]);
		}
		updateVector3(Utils.getMultipleVectors3(vec3Storage));
		updateColor(Utils.getMultipleColors(col3Storage));
	}

	static void createRandomVector3(int numbers, float minSize, float maxSize) {

		vec3Storage = new Vector3[numbers];
		col3Storage = new V3Color[numbers];
		float size;
		for (int i = 0; i < numbers; i++) {
			size = ObjectTools.randFloat(minSize, maxSize);
			vec3Storage[i] = new Vector3(0.0f, size, size, -size, -size, -size);
			col3Storage[i] = vec3Storage[i].color;
			vec3Storage[i].addXOffset(ObjectTools.randFloat(-1f, 1f));
			vec3Storage[i].addYOffset(ObjectTools.randFloat(-1f, 1f));
			vec3Storage[i].save();
		}
		Game.numbers = numbers;
		sendVector3(Utils.getMultipleVectors3(vec3Storage));
		sendColor(Utils.getMultipleColors(col3Storage));
	}

	public static void updateRandomVector3(float bot, float top) {

		for (int i = 0; i < vec3Storage.length; i++) {
			vec3Storage[i].addXOffset(ObjectTools.randFloat(bot, top));
			vec3Storage[i].addYOffset(ObjectTools.randFloat(bot, top));
		}

		updateVector3(Utils.getMultipleVectors3(vec3Storage));
		updateColor(Utils.getMultipleColors(col3Storage));
	}

	public static void sendVector3(float[] array) { // location 0
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

	public static void updateVector3(float[] array) {
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
