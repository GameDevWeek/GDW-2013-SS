/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import de.fhtrier.gdw.ss2013.physics.PhysicsObject;
import de.fhtrier.gdw.ss2013.physics.RectanglePhysicsObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/**
 * Entity base class
 */
public abstract class Entity {

    protected final Vector2f position;

    final static float DEBUG_ENTITY_HALFEXTEND = 5;

    public Entity() {
        this(new Vector2f());
    }

    public Entity(Vector2f position) {
        this.position = position.copy();
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        g.drawRect(position.x - DEBUG_ENTITY_HALFEXTEND, position.y
                - DEBUG_ENTITY_HALFEXTEND, DEBUG_ENTITY_HALFEXTEND * 2,
                DEBUG_ENTITY_HALFEXTEND * 2);

        // g.drawString(this.hashCode() + "", position.x, position.y);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
    }

    public Vector2f getPosition() {
        return position;
    }
    
    public void setPosition(Vector2f v)
    {
        setPosition(v.x, v.y);
    }
    public void setPosition(float x, float y)
    {
        position.x = x;
        position.y = y;
    }
    

    /**
     * Override to provide default values and call in constructor
     */
    public void initialize() {

    }

    public void setPhysicsObject(PhysicsObject physicsObject) {
    }
}
