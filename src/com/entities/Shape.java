package com.entities;

import com.model.Polygon;
import com.model.Vertex;

public interface Shape {
	
	public Polygon[] getPolys();
	public void addXOffset(float value);
	public void addYOffset(float value);
	public Vertex getCentroid();
}
