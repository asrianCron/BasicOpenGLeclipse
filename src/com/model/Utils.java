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
    
    public static float[] getMultipleVectors3(Vector3[] vects) {
        float[] output = new float[6 * vects.length];
//        
//        int index = 0;
//        for (int i = 0; i < vects.length; i++) {
//            float[] temp = vects[i].getArray();
//            for (int j = 0; j < temp.length; j++) {
//                output[index] = temp[j];
//                index++;
//            }
//        }
        
    	
    	for(int i=0;i<vects.length;i++){
    		System.arraycopy(vects[i].getArray(), 0, output, i * 6, 6);
    	}
        
        return output;



    }

    public static float[] getMultipleColors(V3Color[] colors) {
        float[] output = new float[4 * 3 * colors.length];
        
//        int index = 0;
//        for (int i = 0; i < colors.length; i++) {
//            float[] temp = colors[i].getArray();
//            for (int j = 0; j < temp.length; j++) {
//                output[index] = temp[j];
//                index++;
//            }
//        }
        
    	for(int i=0;i<colors.length;i++){
    		System.arraycopy(colors[i].getArray(), 0, output, i * 4 * 3, 4 * 3);
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
