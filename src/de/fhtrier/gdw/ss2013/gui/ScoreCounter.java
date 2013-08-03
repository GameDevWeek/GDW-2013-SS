package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ScoreCounter extends MechanicalCounter {
    public int desiredValue;
    public float speed;
    
    public ScoreCounter(Image digits, Vector2f position, int value, int numberOfDigits, float speed)
    {
        super(digits, position, value, numberOfDigits);
        this.speed = speed;
    }
    
    public void setDesiredValue(int desiredValue)
    {
        this.desiredValue = desiredValue;
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
        super.update(container, game, delta);
        
        if(desiredValue > this.getValue())
        {
            this.up(speed);
        }
        
    }
}