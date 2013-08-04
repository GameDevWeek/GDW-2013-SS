package de.fhtrier.gdw.ss2013.gui.counter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class AutonomousRollerCounter extends RollerCounter {
    private int redundantCycles; //indicates of often the RollerCounter will cycle before displaying its actual Value
    private int desiredValue;
    private int startCountdown;
    private boolean started;
    
    public AutonomousRollerCounter()
    {
        
    }
    
    public AutonomousRollerCounter(Image[] image, Vector2f position, int value, int desiredValue, int redundantCycles, int startCountdown)
    {
        init(image, position, value, desiredValue, redundantCycles, startCountdown);
    }
    
    public void init(Image[] image, Vector2f position, int value, int desiredValue, int redundantCycles, int startCountdown)
    {
        started = false;
        this.desiredValue = desiredValue;
        this.redundantCycles = redundantCycles;
        super.init(image, position, value);
        this.startCountdown = startCountdown;
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
       super.update(container, game, delta);
       
       if(started)
       {
           if(startCountdown<=0)
           {
               if(redundantCycles>0)
               {
                   if(this.up(0.5f))
                   {
                       redundantCycles--;
                   }
               }
               else if (desiredValue != this.getValue())
               {
                   this.up(0.5f);
               }
           }
           else
           {
               startCountdown-=delta;
           }
       }
    }
    
    public void start()
    {
        started = true;
    }
}
