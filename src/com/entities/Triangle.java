package com.entities;

import com.model.Polygon;
import com.model.Vertex;

public class Triangle implements Shape {

	private Polygon[] polygons;

	public Triangle() {
		polygons = new Polygon[1];
		polygons[0] = new Polygon();
	}

	public Triangle(Vertex A, Vertex B, Vertex C) {
		polygons = new Polygon[1];
		polygons[0] = new Polygon(A, B, C);
	}

	@Override
	public Polygon[] getPolys() {
		return polygons;
	}

	@Override
	public void addXOffset(float value) {
		polygons[0].addXOffset(value);
	}

	@Override
	public void addYOffset(float value) {
		polygons[0].addYOffset(value);
	}

	@Override
	public Vertex getCentroid() {
		return polygons[0].getVertex(0);
	}
	
	@Override
	public String toString(){
		return "Triangle{" + "\n" + polygons[0].getVertex(0) + "\n" + polygons[0].getVertex(1) + "\n" + polygons[0].getVertex(2) + "\n}";
	}

}
