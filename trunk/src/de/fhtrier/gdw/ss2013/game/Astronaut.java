package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.geom.Vector2f;

public class Astronaut extends Player{

    private float oxygen;
    private float maxOxygen;
    
    public Astronaut(Vector2f position) {
        super(position);
        
        //Default
        maxOxygen = 0.0f;
        oxygen = maxOxygen;

    }

    public float getOxygen() {
        return oxygen;
    }

    public void setOxygen(float oxygen) {
        this.oxygen = oxygen;
    }

    public float getMaxOxygen() {
        return maxOxygen;
    }
    
    public void setMaxOxygen(float maxOxygen)
    {
        this.maxOxygen = maxOxygen;
    }


    
    

 
}
