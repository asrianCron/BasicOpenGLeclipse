package com.model;

public class VColor {

	private float[] color;

	public VColor() {
		color = new float[4];
		for (int i = 0; i < 4; i++) {
			color[i] = 1.0f;
		}
	}

	public VColor(float R, float G, float B, float A) {
		this();
		if (checkInput(R)) {
			color[0] = R;
		}
		if (checkInput(G)) {
			color[1] = G;
		}
		if (checkInput(B)) {
			color[2] = B;
		}
		if (checkInput(A)) {
			color[3] = A;
		}
	}

	public float[] getColor() {
		return color;
	}
	
	public void setColor(float[] color){
		this.color = color;
	}

	private boolean checkInput(float value) {
		if (value < 0 || value > 1) {
			// throw new IllegalArgumentException();
			return false;
		}
		return true;
	}

}
