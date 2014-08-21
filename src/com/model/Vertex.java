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
public class Vertex {

    float X, Y;

    public Vertex(float X, float Y) {
        this.X = X;
        this.Y = Y;
    }

    public Vertex(Vertex vert) {
        this.X = vert.X;
        this.Y = vert.Y;
    }

    @Override
    public String toString() {
        return "Vertex[" + "X=" + X + ", Y=" + Y + ']';
    }

}
