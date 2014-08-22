/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.model;
///**
// *
// * @author asrianCron
// */
//public class Vertex {
//
//    float X, Y;
//
//    public Vertex(float X, float Y) {
//        this.X = X;
//        this.Y = Y;
//    }
//
//    public Vertex(Vertex vert) {
//        this.X = vert.X;
//        this.Y = vert.Y;
//    }
//
//    @Override
//    public String toString() {
//        return "Vertex[" + "X=" + X + ", Y=" + Y + ']';
//    }
//
//}

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
		return "Vertex[" + "X=" + data[0] + ", Y=" + data[1] + ']';
	}

}
