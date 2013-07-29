package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;
import de.fhtrier.gdw.ss2013.game.*;
/**
 * Switch Class
 * @author Kevin, Georg
 *
 */
public class Switch extends Entity {
    
    boolean activated = false;
    
    public Switch(Vector2f pos) {        
        super(pos);
    }
    
    public Switch() {
        this(new Vector2f());
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated() {
        this.activated = !this.activated;
    }
    
}
