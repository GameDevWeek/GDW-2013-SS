package de.fhtrier.gdw.ss2013.gui.utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class CenteredText {

    public static Font font;
    public static Graphics g;
    
    public static void init(Font font)
    {
        CenteredText.font=font;
        
    }
    
    public static void draw(float x, float y,String string, Graphics g)
    {
        g.setColor(Color.black);
        //g.scale(2, 2);
        g.drawString(string,x-font.getWidth(string)/2,y-font.getHeight(string)/2);
        //g.scale(0.5f, 0.5f);
        //font.drawString(x-font.getWidth(string)/2,y-font.getHeight(string)/2,string);
    }
    
}
