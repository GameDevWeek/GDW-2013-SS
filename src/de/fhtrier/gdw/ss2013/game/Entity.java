/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Entity base class
 */
public abstract class Entity {

    protected PhysixObject physicsObject;

    final static float DEBUG_ENTITY_HALFEXTEND = 5;

    public Entity() {
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
    }

    public Vector2f getPosition() {
        return physicsObject.getPosition();
    }

    public Vector2f getVelocity() {
        return physicsObject.getLinearVelocity();
    }

    public void setVelocity(Vector2f velocity) {
        physicsObject.setLinearVelocity(velocity);
    }

    public void setVelocityX(float x) {
        physicsObject.setLinearVelocityX(x);
    }

    public void setVelocityY(float y) {
        physicsObject.setLinearVelocityY(y);
    }
    
    /**
     * Override to provide default values and call in constructor
     */
    public void initialize() {

    }

    /**
     * Override to provide destroy code for world objects (physic mostly)
     */
    public void dispose() {

    }

    public void setPhysicsObject(PhysixObject physicsObject) {
        physicsObject.setOwner(this);
        this.physicsObject = physicsObject;
    }
}
