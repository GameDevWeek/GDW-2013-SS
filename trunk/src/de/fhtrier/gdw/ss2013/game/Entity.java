package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/**
 * Entity base class
 */
public class Entity {

    protected final Vector2f position = new Vector2f();

    final static float DEBUG_ENTITY_HALFEXTEND = 5;

    public Entity() {
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        g.drawRect(position.x - DEBUG_ENTITY_HALFEXTEND, position.y
                - DEBUG_ENTITY_HALFEXTEND, DEBUG_ENTITY_HALFEXTEND * 2,
                DEBUG_ENTITY_HALFEXTEND * 2);

        // g.drawString(this.hashCode(), position.x, position.y);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {

        float dt = delta / 1000.f;
        // TODO clamp dt if dt > 1/60.f ?

    }

    public Vector2f getPosition() {
        return position;
    }
}
