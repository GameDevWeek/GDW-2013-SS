package de.fhtrier.gdw.ss2013.game;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EntityManager {

    protected ArrayList<Entity> entityList;
    static protected EntityManager managerInstance;

    public EntityManager() {
        entityList = new ArrayList<>();
    }

    /**
     * 
     * @param delta
     *            deltaTime in miliseconds
     * @throws SlickException
     */
    public void update(GameContainer c, int delta) throws SlickException {
        for (Entity e : entityList)
            e.update(c, delta);
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        for (Entity e : entityList)
            e.render(container, g);
    }

    public Entity getEntityAtPosition(float px, float py) {
        return null;
    }

    public void addEntity(Entity e) {
        entityList.add(e);
    }

    public void removeEntity(Entity e) {
        // TODO: removelist for removal pre update
    }
}
