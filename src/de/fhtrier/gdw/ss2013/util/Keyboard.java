package de.fhtrier.gdw.ss2013.util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;



/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public class Keyboard extends InputDevice {

    public Keyboard(GameContainer gc) {
        super(gc);
    }
    
    
    @Override
    public void update() {
        
        if(container.getInput().isKeyDown(Input.KEY_D))
        {
            //astronautListener.moveForward();
        }
        
        else if (container.getInput().isKeyDown(Input.KEY_A))
        {
            //astronautListener.moveBackward();
        }
        
        else if (container.getInput().isKeyPressed(Input.KEY_SPACE))
        {
            //astronautListener.jump();
        }
        
        else if (container.getInput().isKeyPressed(Input.KEY_E))
        {
            //astronautListener.action();
        }
        

    }

}
