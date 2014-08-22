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
        A.X += offset;
//        this.getVertex(0).setVertex(this.getVertex(0).getVertex()[0] + offset);
        B.X += offset;
        C.X += offset;
    }

    public void addYOffset(float offset) {
        A.Y += offset;
        B.Y += offset;
        C.Y += offset;
    }
}
