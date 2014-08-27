package com.model;

import java.util.ArrayList;
import java.util.List;

import com.entities.Shape;

public class ShapeStorage {

	private List<Shape> shapes;
	private PolyStorage storage;

	public ShapeStorage() {
		shapes = new ArrayList<>();
		storage = new PolyStorage();
	}

	public void addShape(Shape shape) {
		shapes.add(shape);
		for (int i = 0; i < shape.getPolys().length; i++) {
			storage.addPolygon(shape.getPolys()[i]);
		}
		updateStorage();
	}
	
	public void updateStorage(){
		storage.updateStorage();
	}
	
	public Shape getShape(int index){
		return shapes.get(index);
	}
	public void setShape(int index, Shape element){
		shapes.set(index, element);
	}
	
	public int size() {
		return shapes.size();
	}

	public PolyStorage getStorage() {
		return storage;
	}
}
