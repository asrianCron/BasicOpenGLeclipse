package com.model;

public class Polygon {
	
	protected Vertex[] vertices;
	protected VColor[] colors;
	
	public Polygon(){
		vertices = new Vertex[3];
		colors = new VColor[3];
		for(int i=0;i<3;i++){
			vertices[i] = new Vertex();
			colors[i] = new VColor();
		}
	}
	
    public Polygon(Vertex A, Vertex B, Vertex C) {
		this();
    	vertices[0] =  A;
    	vertices[1] =  B;
    	vertices[2] =  C;
    }

    public Polygon(float AX, float AY, float BX, float BY, float CX, float CY) {
    	this();
        vertices[0] = new Vertex(AX, AY);
        vertices[1] = new Vertex(BX, BY);
        vertices[2] = new Vertex(CX, CY);
    }
	
    public void addXOffset(float offset) {
    	for(int i=0;i<vertices.length;i++){
    		getVertex(i).setX(getVertex(i).getX() + offset);
    	}
    }

    public void addYOffset(float offset) {
    	for(int i=0;i<vertices.length;i++){
    		getVertex(i).setY(getVertex(i).getY() + offset);
    	}
    }
    
	public Vertex getVertex(int index){
		if(index > vertices.length){
			throw new IndexOutOfBoundsException();
		}
		return vertices[index];
	}
	public Vertex[] getVertices(){
		return vertices;
	}
	public float[] getVerticesArray(){
		float[] result = new float[2 * vertices.length];
		for(int i=0;i<vertices.length;i++){
			System.arraycopy(vertices[i].getVertex(), 0, result, i*2, 2);
		}
		return result;		
	}
	public VColor getColor(int index){
		if(index > colors.length){
			throw new IndexOutOfBoundsException();
		}
		return colors[index];
	}
	public VColor[] getColors(){
		return colors;
	}
	public float[] getColorsArray(){
		float[] result = new float[4 * colors.length];
		for(int i=0;i<colors.length;i++){
			System.arraycopy(colors[i].getColor(), 0, result, i*4, 4);
		}
		return result;	
	}
	
	public void setVertice(int index, Vertex vert){
		if (index > vertices.length){
			throw new IndexOutOfBoundsException();
		}
		vertices[index] = vert;
	}
	public void setVertice(int index, float[] vert){
		if (index > vertices.length){
			throw new IndexOutOfBoundsException();
		}
		if(vert.length != 2){
			throw new IllegalArgumentException();
		}
		vertices[index].setVertex(vert);
	}
	public void setVertices(Vertex[] vertices){
		this.vertices = vertices;
	}
	public void setColor(int index, VColor color){
		if (index > colors.length){
			throw new IndexOutOfBoundsException();
		}
		colors[index] = color;
	}
	public void setColor(int index, float[] color){
		if (index > color.length){
			throw new IndexOutOfBoundsException();
		}
		if(color.length != 4){
			throw new IllegalArgumentException();
		}
		colors[index].setColor(color);
	}
	@Override
	public String toString(){
		return "Polygon: {\n" + getVertex(0).toString() + "\n" + getVertex(1).toString() + "\n" + getVertex(2).toString() + "\n}"; 
	}
	
}
