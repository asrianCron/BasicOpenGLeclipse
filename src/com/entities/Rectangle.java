package com.entities;

import com.model.Polygon;
import com.model.Vertex;

public class Rectangle implements Shape{

	protected Polygon[] polygons;
	
	public Rectangle(){
		polygons = new Polygon[0];
	}
	
	public Rectangle(Vertex A, Vertex B, Vertex C, Vertex D){
		polygons = new Polygon[2];
		polygons[0] = new Polygon(A, B, C);
		polygons[1] = new Polygon(C, D, A);
	}
	
	public Rectangle(float width, float height){
		polygons = new Polygon[2];
		polygons[0] = new Polygon(new Vertex(-width/2.0f,height/2.0f), new Vertex(width/2.0f,height/2.0f), new Vertex(width/2.0f, -height/2.0f));
		polygons[1] = new Polygon(new Vertex(width/2.0f, -height/2.0f), new Vertex(-width/2.0f,-height/2.0f), new Vertex(-width/2.0f, height/2.0f));
	}
	
	@Override
	public Polygon[] getPolys() {
		// TODO Auto-generated method stub
		return polygons;
	}

	@Override
	public void addXOffset(float value) {
		for(int i=0;i<polygons.length;i++){
			polygons[i].addXOffset(value);
		}
	}

	@Override
	public void addYOffset(float value) {
		for(int i=0;i<polygons.length;i++){
			polygons[i].addYOffset(value);
		}
	}

	@Override
	public Vertex getCentroid() {
		return new Vertex((polygons[0].getVertex(0).getX() + polygons[0].getVertex(1).getX())/2,(polygons[0].getVertex(1).getY() + polygons[0].getVertex(2).getY())/2);
	}
	
	@Override
	public String toString(){
		return "Rectangle{\n" + polygons[0].getVertex(0) + "\n" + polygons[0].getVertex(1) + "\n" + polygons[0].getVertex(2) + "\n" + polygons[1].getVertex(1) + "\n" + "}";
	}
		
}
