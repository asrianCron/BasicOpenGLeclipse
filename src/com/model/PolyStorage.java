package com.model;

public class PolyStorage {

	private Polygon[] polyStorage;
	private VColor[] colorStorage;
	private float[] polyStorageArray;
	private float[] colorStorageArray;
	private int nVertices;
	private int nShapes;
	
	public PolyStorage(){
		polyStorage = new Polygon[0];
		colorStorage = new VColor[0];
		polyStorageArray = new float[0];
		colorStorageArray = new float[0];
		nVertices = 0;
		nShapes = 0;
	}
	
	public void addPolygon(Polygon poly) {
		nShapes++;
		Polygon[] polyTempStorage = new Polygon[polyStorage.length + 1];
		VColor[] colorTempStorage = new VColor[colorStorage.length
				+ poly.nVertices];

		nVertices += poly.getNVertices();
		System.arraycopy(polyStorage, 0, polyTempStorage, 0, polyStorage.length);
		polyTempStorage[polyTempStorage.length - 1] = poly;
		polyStorage = polyTempStorage;

		System.arraycopy(colorStorage, 0, colorTempStorage, 0,
				colorStorage.length);

		int index = 0;

		for (int i = colorTempStorage.length - poly.nVertices; i < colorTempStorage.length; i++, index++) {
			colorTempStorage[i] = poly.getColor(index);
		}
		colorStorage = colorTempStorage;
//		updateStorage();
	}

	public void updateStorage(){
		calcPolyStorageArray();
		calcVColorStorageArray();
	}
	
	public float[] getStoredPolyArray() {
		return polyStorageArray;
	}

	public float[] getStoredVColorArray() {
		return colorStorageArray;
	}

	private void calcVColorStorageArray() {
		float[] output = new float[4 * colorStorage.length];
		for (int i = 0; i < colorStorage.length; i++) {
			System.arraycopy(colorStorage[i].getColor(), 0, output, i * 4, 4);
		}
		colorStorageArray = output;
	}

	private void calcPolyStorageArray() {

		float[] output = new float[2 * nVertices];

		for (int i = 0; i < polyStorage.length; i++) {
			System.arraycopy(polyStorage[i].getVerticesArray(), 0, output, i
					* 2 * polyStorage[i].getNVertices(),
					2 * polyStorage[i].getNVertices());
		}
		polyStorageArray = output;
	}

	public VColor[] getColors() {
		return colorStorage;
	}

	public Polygon[] getPolyStorage() {
		return polyStorage;
	}
	
	public int getPolyCount(){
		return nVertices / 3;
	}
	
	public int getShapeCount(){
		return nShapes;
	}
	
	public Polygon getPolygon(int index){
		if(index >= polyStorage.length){
			throw new IndexOutOfBoundsException();
		}		
		return polyStorage[index];
	}
	

}
