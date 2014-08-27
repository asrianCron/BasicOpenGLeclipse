package com.main;

import com.entities.Rectangle;
import com.entities.Shape;
import com.model.Game;
//observation : using self-made storage, without List, performance in loading shapes is higher
public class Main {

	public static void main(String[] args) {
		 Game.start();
//		 java.awt.Polygon
		
		 Shape rect = new Rectangle(2f,2f);
		 for(int i=0;i<rect.getPolys().length;i++){
			 System.out.println(rect.getPolys()[i]);
		 }
		 
		 rect.addXOffset(1f);
		 
		 for(int i=0;i<rect.getPolys().length;i++){
			 System.out.println(rect.getPolys()[i]);
		 }

	}

}
