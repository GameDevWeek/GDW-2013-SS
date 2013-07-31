/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;


import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Entity base class
 */
public abstract class Entity {

    protected PhysixObject physicsObject;
    protected Image img;
   

    final static float DEBUG_ENTITY_HALFEXTEND = 5;

    /* every Entity-class needs a constructor without any parameters! */
    public Entity() {
    }
    
    public Entity(Image img) {
        this.img = img;
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        if (img != null) {
            g.drawImage(img, getPosition().x-(img.getWidth()/2), getPosition().y-(img.getHeight()/2));
        }
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
    }

    public Vector2f getPosition() {
        return physicsObject.getPosition();
    }

    
    
    public Vector2f getVelocity() {
        if (physicsObject == null) {
            throw new NullPointerException("Physics object does not exist.");
        }
        return physicsObject.getLinearVelocity();
    }

    public void setVelocity(Vector2f velocity) {
        if (physicsObject == null) {
            throw new NullPointerException("Physics object does not exist.");
        }
        physicsObject.setLinearVelocity(velocity);
    }

    public void setVelocityX(float x) {
        if (physicsObject == null) {
            throw new NullPointerException("Physics object does not exist.");
        }
        physicsObject.setLinearVelocityX(x);
    }

    public void setVelocityY(float y) {
        if (physicsObject == null) {
            throw new NullPointerException("Physics object does not exist.");
        }
        physicsObject.setLinearVelocityY(y);
    }
    
    /**
     * Override to provide default values and call in constructor
     */
    protected void initialize() {

    }

    /**
     * Override to provide destroy code for world objects (physic mostly)
     */
    protected void dispose() {

    }

    public void setPhysicsObject(PhysixObject physicsObject) {
        physicsObject.setOwner(this);
        this.physicsObject = physicsObject;
    }
    
    public PhysixObject getPhysicsObject() {
        return this.physicsObject;
    }
    
    public void setImage(Image img) {
    	this.img = img;
    }
    
    public Image getImage() {
    	return img;
    }
}
