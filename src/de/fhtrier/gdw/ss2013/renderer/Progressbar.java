/**
 * Boris, David (UI-Team)
 */

package de.fhtrier.gdw.ss2013.renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.Entity;

public class Progressbar {
    
    //Werte für die Abmessungen der Progessbar
    private float xpos = 10;
    private float ypos = 10;
    private float height = 40;
    private float width = 243;
    private int cornerradius = 5;
    
    //Werte für konkreten Balken   
    private float maxValue = 2000; //gibt den Maximalwert an
    private float currentValue = 1000; //gibt den aktuellen Wert an 
    private float currentPercentValue; //currentValue in Prozent in Bezug auf maxValue
    private float filled; //gibt an wie weit der Balken gefüllt ist
    
    private Image progress;
    
    
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
        //currentValue auslesen.
        currentPercentValue = (currentValue / maxValue) * 100;
        
        filled = width * (currentPercentValue / 100);
    
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        g.setColor(Color.blue);
        g.fillRoundRect(xpos, ypos, filled, height, cornerradius);
        
    }    

}
