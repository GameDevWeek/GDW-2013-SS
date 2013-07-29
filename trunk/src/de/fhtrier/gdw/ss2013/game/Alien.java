package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.geom.Vector2f;

public class Alien extends Player{

    private float mana;
    private float maxMana;
    
    public Alien(Vector2f position) {
        super(position);
        
        //Default 
        maxMana = 0.0f;
        mana = maxMana;
        
    }

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public float getMaxMana() {
        return maxMana;
    }

    public void setMaxMana (float maxMana)
    {
        this.maxMana = maxMana;
    }


    
    
}
