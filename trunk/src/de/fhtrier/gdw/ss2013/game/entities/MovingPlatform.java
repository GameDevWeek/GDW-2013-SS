package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.Player;
/**
 * Moving Platform class
 * @author Kevin, Georg
 *
 */
public class MovingPlatform extends Entity {
    private Vector2f velocity;
    
    public MovingPlatform(Vector2f pos, Vector2f velo) {
        super(pos.copy());
        velocity = velo;
    }
    public MovingPlatform() {
        this(new Vector2f(), new Vector2f());
    }
    public MovingPlatform(Vector2f pos) {
        this(pos.copy(), new Vector2f());
    }
    public void onCollision(Entity e) {
        if(e instanceof Player || e instanceof Box) {
            e.getPosition().x += velocity.x;
            e.getPosition().y += velocity.y;
        }
    }
    public void reachEnd() {
        velocity.x = -velocity.x;
        velocity.y = -velocity.y;
    }
}
