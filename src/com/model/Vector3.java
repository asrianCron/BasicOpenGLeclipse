/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;
/**
 *
 * @author asrianCron
 */
public class Vector3 { // vertices should be defined counterClockwise

    Vertex A, B, C;
    Vector3 savedState;
    
    V3Color color;

    public Vector3() {
        A = new Vertex(0, 0);
        B = new Vertex(0, 0);
        C = new Vertex(0, 0);
        color = new V3Color(1f, 1f, 1f, 0f);
    }

    public Vector3(Vector3 trig) {
        this.A = new Vertex(trig.A);
        this.B = new Vertex(trig.B);
        this.C = new Vertex(trig.C);
        color = new V3Color(1f, 1f, 1f, 0f);
    }

    public Vector3(Vertex A, Vertex B, Vertex C) {
        this.A = A;
        this.B = B;
        this.C = C;
        color = new V3Color(1f, 1f, 1f, 0f);
    }

    public Vector3(float AX, float AY, float BX, float BY, float CX, float CY) {
        A = new Vertex(AX, AY);
        B = new Vertex(BX, BY);
        C = new Vertex(CX, CY);
        color = new V3Color(1f, 1f, 1f, 0f);
    }

    public void addXOffset(float offset) {
        A.X += offset;
        B.X += offset;
        C.X += offset;
    }

    public void addYOffset(float offset) {
        A.Y += offset;
        B.Y += offset;
        C.Y += offset;
    }

    public void applyGravity(float base, float gravAcc){
    	if(B.Y <= base || C.Y <= base){
    		B.Y = base;
    		C.Y = base;
    	} else{
        	this.addYOffset(-gravAcc);
    	}
    }

    public void setPos(float X, float Y){
    	
    }
    
    public void save(){
    	this.savedState = new Vector3(this);
    }
    
    public void restore(){
    	this.A = new Vertex(savedState.A);
    	this.B = new Vertex(savedState.B);
    	this.C = new Vertex(savedState.C);
    	this.color = new V3Color(savedState.color);
    	this.savedState = new Vector3(this.savedState);
    }
    
    public float[] getArray() {
        float[] output = new float[6];
        output[0] = A.X;
        output[1] = A.Y;
        output[2] = B.X;
        output[3] = B.Y;
        output[4] = C.X;
        output[5] = C.Y;
        return output;
    }

    public void setVertices(float AX, float AY, float BX, float BY, float CX, float CY) {
        A.X = AX;
        A.Y = AY;
        B.X = BX;
        B.Y = BY;
        C.X = CX;
        C.Y = CY;
    }

    public boolean collisionCheck(float X, float Y, float minusX, float minusY) {
        return A.X < X && A.Y < Y && A.X > minusX && A.Y > minusY;
    }

    @Override
    public String toString() {
        return "Vector3[" + "A.X=" + A.X + " A.Y=" + A.Y + ", B.X=" + B.X + " B.Y=" + B.Y + ", C.X=" + C.X + " C.Y=" + C.Y + ", \n" + color + '}';
    }

}
