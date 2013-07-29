/**
 * 
 * OxygenFlower class
 * @author Justin, Sandra
 * 
 * Blume erzeugt Blasen und Blasenvorrat verringert sich
 */
package de.fhtrier.gdw.ss2013.game.entities;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;

public class OxygenFlower extends Entity {

    private int maxBubble;

    private ArrayList<OxygenBubble> bubbles = new ArrayList<OxygenBubble>();

    public OxygenFlower(Vector2f position) {
        super(position);
        
        //Default
        maxBubble = 0; 
    }

    public OxygenFlower(Vector2f position, int maxBubble) {

        super(position);
        this.maxBubble = maxBubble;
    }

    public void shootBubbles() {

        maxBubble--;
    }
    
    public void setMaxBubble(int maxBubble){
        this.maxBubble = maxBubble;
    }

    public ArrayList<OxygenBubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(ArrayList<OxygenBubble> bubbles) {
        this.bubbles = bubbles;
    }

    public int getMaxBubble() {
        return maxBubble;
    }
    
    
}
