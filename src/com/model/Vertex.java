
package com.model;

/**
 *
 * @author asrianCron
 */
public class Vertex {

	private float[] data;

	public Vertex() {
		data = new float[2];
	}

	public Vertex(float X, float Y) {
		this();
		data[0] = X;
		data[1] = Y;
	}

	public Vertex(Vertex vert) {
		this.data = vert.getVertex();
	}

	public float getX() {
		return data[0];
	}

	public float getY() {
		return data[1];
	}

	public void setX(float value) {
		data[0] = value;
	}

	public void setY(float value) {
		data[1] = value;
	}

	public float[] getVertex() {
		return data;
	}

	public void setVertex(float[] data) {
		if (data.length == this.data.length) {
			this.data = data;
		}
	}

	public void setVertex(Vertex vert) {
		data = vert.getVertex();
	}

	@Override
	public String toString() {
		String x,y;
		
		if(data[0]>=0.0f){
			x = "+" + data[0];
		} else{
			x = data[0] + "";
		}
		if(data[1]>=0.0f){
			y = "+" + data[1];
		} else{
			y = data[1] + "";
		}
		
		return "Vertex[" + "X=" + x + ", Y=" + y + ']';
	}

}
