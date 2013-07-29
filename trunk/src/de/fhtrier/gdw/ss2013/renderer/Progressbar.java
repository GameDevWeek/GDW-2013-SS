package de.fhtrier.gdw.ss2013.renderer;

import org.newdawn.slick.Image;

import de.fhtrier.gdw.ss2013.game.Entity;

public class Progressbar {
    
    private float xpos = 10;
    private float ypos = 10;
   
    private float maxValue; //gibt den Maximalwert an
    private float currentValue; //gibt den aktuellen Wert an 
    private float currentPercentValue; //currentValue in Prozent in Bezug auf maxValue
    
    private Image progress;
    
    public void update()
    {
        //currentValue auslesen.
        currentPercentValue = (currentValue / maxValue) * 100;
    
    }
    
    public void render()
    {
       // progress.draw(xpos,ypos); ???
    }    

}
