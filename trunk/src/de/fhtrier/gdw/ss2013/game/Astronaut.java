package de.fhtrier.gdw.ss2013.game;

public class Astronaut extends Player{

    private float oxygen;
    private float maxOxygen;
    
    public Astronaut(float x, float y, float maxOxygen) {
        super(x, y);
        this.maxOxygen =  maxOxygen;
        setOxygen(maxOxygen);

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


    
    

 
}
