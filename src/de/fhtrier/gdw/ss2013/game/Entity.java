/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;
import de.fhtrier.gdw.ss2013.renderer.DynamicParticleSystem;

/**
 * Entity base class
 */
public abstract class Entity {

    protected PhysixObject physicsObject;
    protected Image img;
    protected String name;
    protected SafeProperties properties;

    private static int entity = 0;

    final static float DEBUG_ENTITY_HALFEXTEND = 5;

    protected DynamicParticleSystem particle;
    protected AssetLoader asset;

    /* every Entity-class needs a constructor without any parameters! */
    public Entity() {
        setName();
    }

    public Entity(Image img) {
        this();
        this.img = img;
    }

    public Entity(Image img, String name) {
        this.img = img;
        this.name = name;
    }

    public Entity(String name) {
        this.name = name;
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        if (img != null) {
            g.drawImage(img, getPosition().x - (img.getWidth() / 2),
                    getPosition().y - (img.getHeight() / 2));
        }
        if (particle != null)
            particle.render();
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
        if (particle != null&& img!=null) {
            particle.update(delta);
            particle.setPosition(this.getPosition().x-this.img.getWidth()/3, this.getPosition().y+this.img.getHeight()/2);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        } else {
            this.name = String.valueOf(entity++);
        }

    }

    public void setProperties(SafeProperties properties) {
        this.properties = properties;
    }

    public SafeProperties getProperties() {
        return this.properties;
    }

    public void setName() {
        setName(null);
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

    public DynamicParticleSystem getParticle() {
        return particle;
    }

    public void setParticle(DynamicParticleSystem particle) {
        this.particle = particle;
    }

    public void setImage(Image img) {
        this.img = img;
    }

    public Image getImage() {
        return img;
    }
}
