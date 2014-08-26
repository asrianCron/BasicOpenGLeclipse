package com.model;

public class Quad extends Polygon {
	
	public Quad(){
		vertices = new Vertex[6];
		colors = new VColor[6];
		for(int i=0;i<6;i++){
			vertices[i] = new Vertex();
			colors[i] = new VColor();
		}
	}
	public Quad(Vertex A, Vertex B, Vertex C, Vertex D){
		this();
		vertices[0] = A;
		vertices[1] = B;
		vertices[2] = C;
		vertices[3] = C;
		vertices[4] = D;
		vertices[5] = A;
	}
    public Quad(float AX, float AY, float BX, float BY, float CX, float CY, float DX, float DY) {
    	this();
        vertices[0] = new Vertex(AX, AY);
        vertices[1] = new Vertex(BX, BY);
        vertices[2] = new Vertex(CX, CY);
        vertices[3] = new Vertex(CX, CY);
        vertices[4] = new Vertex(DX, DY);
        vertices[5] = new Vertex(AX, AY);
    }
	
    @Override
    public String toString(){
    	return "Quad: {\n" + getVertex(0).toString() + "\n" + getVertex(1).toString() + "\n" + getVertex(2).toString() + "\n" + getVertex(3).toString() + "\n}"; 
    }
    
//	public Quad(){
//		vertices = new Vertex[4];
//		colors = new VColor[4];
//		for(int i=0;i<4;i++){
//			vertices[i] = new Vertex();
//			colors[i] = new VColor();
//		}
//	}
//	public Quad(Vertex A, Vertex B, Vertex C, Vertex D){
//		this();
//		vertices[0] = A;
//		vertices[1] = B;
//		vertices[2] = C;
//		vertices[3] = D;	
//	}
//    public Quad(float AX, float AY, float BX, float BY, float CX, float CY, float DX, float DY) {
//    	this();
//        vertices[0] = new Vertex(AX, AY);
//        vertices[1] = new Vertex(BX, BY);
//        vertices[2] = new Vertex(CX, CY);
//        vertices[3] = new Vertex(DX, DY);
//    }
//	
//    @Override
//    public String toString(){
//    	return "Quad: {\n" + getVertex(0).toString() + "\n" + getVertex(1).toString() + "\n" + getVertex(2).toString() + "\n" + getVertex(3).toString() + "\n}"; 
//    }
    
}
