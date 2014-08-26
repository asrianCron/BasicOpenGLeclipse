/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asrianCron
 */
public class Utils {

    public static String loadFile(String path) {

        Path filePath = Paths.get(path);

        String temp;
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader;
        if (Files.exists(filePath)) {
            try {
                reader = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath)));
                while ((temp = reader.readLine()) != null) {
                    buffer.append(temp).append("\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return buffer.toString();
        } else {
            System.out.println("FILE NOT FOUND");
            return null;
        }
    }

    public static float convertNeutre(int pos, int maxCoord){
    	float halfCoord = maxCoord/2;
    	return (pos - halfCoord)/halfCoord;
    }
    
    public static int convertNonNeutre(float pos, int maxCoord){
    	float halfCoord = maxCoord/2;
    	return (int) (pos * halfCoord + halfCoord);
    }
    
    public static float[] getMultipleVectors3(Polygon[] polys) {
    	if(polys != null){
            float[] output = new float[6 * polys.length];     
        	
        	for(int i=0;i<polys.length;i++){
        		System.arraycopy(polys[i].getVerticesArray(), 0, output, i * 6, 6);
        	}
        	return output;
    	}
        return null;
    }

    public static float[] getMultipleColors(VColor[] colors) {
        float[] output = new float[4 * colors.length];        
    	for(int i=0;i<colors.length;i++){
    		System.arraycopy(colors[i].getColor(), 0, output, i * 4, 4);
    	}
        return output;
    }
    
    
//    public static List<String> loadFile(String path) {
//        Path filePath = Paths.get(path);
//        List<String> fileData = null;
//        if (Files.exists(filePath)) {
//            try {
//                fileData = Files.readAllLines(filePath);
//            } catch (IOException ex) {
//                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return fileData;
//        } else {
//            System.out.println("FILE NOT FOUND");
//            return null;
//        }
//    }

}
