package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;
import de.fhtrier.gdw.ss2013.game.*;

public class Lever extends Entity {
    
    boolean activated = false;
    
    public Lever(Vector2f pos) {        
        super(pos);
    }
    
    public Lever() {
        this(null);
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated() {
        this.activated = !this.activated;
    }
    
}
