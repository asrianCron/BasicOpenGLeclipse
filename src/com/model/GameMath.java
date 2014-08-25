package com.model;

public class GameMath {

	public static float getSlope(Vertex x1, Vertex x2) {
		if (x1.getX() - x2.getX() == 0) {
			return 0;
		}
		return (x1.getY() - x2.getY()) / (x1.getX() - x2.getX());

	}

	public static float getSlope(float x1, float y1, float x2, float y2) {
		if (x1 - x2 == 0) {
			return 0;
		}
		return (y1 - y2) / (x1 - x2);

	}

	public static float[] calculateOffset(float x1, float y1, float x2,
			float y2, float divideValue) {

		float[] result = new float[2];

		result[0] = (x2 - x1) / divideValue;
		result[1] = (y2 - y1) / divideValue;
		return result;
	}

}
