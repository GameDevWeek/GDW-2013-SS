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

    public Entity() {
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
    }

    public void update(GameContainer container, int delta) throws SlickException {
    }

    public Vector2f getPosition() {
        return position;
    }
}
