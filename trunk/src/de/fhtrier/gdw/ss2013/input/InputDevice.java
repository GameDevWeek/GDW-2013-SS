package de.fhtrier.gdw.ss2013.input;

import java.util.HashMap;

import org.lwjgl.input.Controllers;
import org.newdawn.slick.GameContainer;

import de.fhtrier.gdw.ss2013.interfaces.AlienControls;
import de.fhtrier.gdw.ss2013.interfaces.AstronautControls;



/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public abstract class InputDevice {
    
    
    protected GameContainer container;
    protected AlienControls alienListener = null;
    protected AstronautControls astronautListener = null;

    //enum für actions
    enum ACTIONS {MOVEFORWARD, MOVEBACKWARD, JUMP, ACTION, SHOOT, TARGET, ROTATEABILITY, USEABILITY};
    
    
    //dynamsiche tastenzuweisungen
    //HashMap<K,V>
    
    public InputDevice(GameContainer gc) {
        container = gc;
    }
    
    
    public abstract void update();
    
    
    public void setAlienListener(AlienControls ac)
    {
        alienListener = ac;
    }
    
    public void setAstronautListener(AstronautControls ac)
    {
        astronautListener = ac;
    }
    

}
