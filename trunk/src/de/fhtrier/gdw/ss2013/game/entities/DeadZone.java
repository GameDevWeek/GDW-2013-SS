/**
 * DeadZone class
 * @author Justin, Sandra 
 * 
 * 
 */

package de.fhtrier.gdw.ss2013.game.entities;
import de.fhtrier.gdw.ss2013.game.Astronaut;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;


public class DeadZone extends Entity{
    
    public DeadZone(Vector2f position)
    {
        super(position);
    }
    
    public DeadZone() {
        
    }
    
    public void touched(Astronaut astro) {
        
        astro.setOxygen(0);
        
    }

}
