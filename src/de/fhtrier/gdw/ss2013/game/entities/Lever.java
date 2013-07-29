package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;
import de.fhtrier.gdw.ss2013.game.*;

public class Lever extends Entity {
    
    boolean activated;
    
    public Lever(Vector2f pos) {
        
        super(pos);
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    
}
