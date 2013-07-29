package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
/**
 * Moving Platform class
 * @author Kevin, Georg
 *
 */
public class MovingPlatform extends Entity {
    private Vector2f velocity;
    
    public  MovingPlatform (Vector2f position){
        super (position);
        
    }
    
    public MovingPlatform(Vector2f pos, Vector2f velo) {
        super(pos);
        velocity = velo;
    }
    public MovingPlatform() {
        this(new Vector2f(), new Vector2f());
    }
    public void onCollide() {}
}
