package de.fhtrier.gdw.ss2013.gui.utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class CenteredText {
   
    public static void draw(float x, float y,String string, Font font)
    {
        
        
        font.drawString(x-font.getWidth(string)/2,y-font.getHeight(string)/2,string);
    }
    
}
