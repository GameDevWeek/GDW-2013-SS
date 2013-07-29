package de.fhtrier.gdw.ss2013.game;

public class Alien extends Player{

    private float mana;
    private float maxMana;
    
    public Alien(float x, float y, float maxMana) {
        super(x, y);
        this.maxMana= maxMana;
        setMana(maxMana);
        // TODO Auto-generated constructor stub
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



    
    
}
