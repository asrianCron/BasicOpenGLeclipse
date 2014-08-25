package com.model;

public class Polygon3 extends Polygon{
	
	public Polygon3(){
		super();
	}
	
    public Polygon3(Vertex A, Vertex B, Vertex C) {
    	super(A,B,C);
    }

    public Polygon3(float AX, float AY, float BX, float BY, float CX, float CY) {
        super(AX, AY, BX, BY, CX, CY);
    }
    
    public void addXOffset(float offset) {

        getVertex(0).setX(getVertex(0).getX() + offset);
        getVertex(1).setX(getVertex(1).getX() + offset);
        getVertex(2).setX(getVertex(2).getX() + offset);
        
    }

    public void addYOffset(float offset) {
        getVertex(0).setY(getVertex(0).getY() + offset);
        getVertex(1).setY(getVertex(1).getY() + offset);
        getVertex(2).setY(getVertex(2).getY() + offset);
    }
}
