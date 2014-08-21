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
public class V3Color {

    float[][] colors;

    public V3Color() {
        colors = new float[3][4];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new float[4];
        }
    }

    public V3Color(V3Color col) {
        for (int i = 0; i < col.colors.length; i++) {
            for (int j = 0; j < col.colors[i].length; j++) {
                this.colors[i][j] = col.colors[i][j];
            }
        }
    }

    public V3Color(float R, float G, float B, float A) {
        colors = new float[3][4];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new float[4];
            colors[i][0] = R;
            colors[i][1] = G;
            colors[i][2] = B;
            colors[i][3] = A;
        }
    }

    public void setColor(float R, float G, float B, float A) {
        colors = new float[3][4];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new float[4];
            colors[i][0] = R;
            colors[i][1] = G;
            colors[i][2] = B;
            colors[i][3] = A;
        }
    }

    public float[] getArray() {
        float[] output = new float[3 * 4];
        int index = 0;
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                output[index] = colors[i][j];
                index++;
            }
        }
        return output;
    }


    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        strb.append("V3Color: \n");
        for (int i = 0; i < this.colors.length; i++) {
            for (int j = 0; j < this.colors[i].length; j++) {
                strb.append(this.colors[i][j]).append(" ");
            }
            strb.append("\n");
        }
        return strb.toString();
    }

}
