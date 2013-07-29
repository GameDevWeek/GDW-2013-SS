package de.fhtrier.gdw.ss2013.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EntityManager {
    // static protected EntityManager managerInstance;
    protected ArrayList<Entity> entityList;
    protected HashMap<Class<? extends RecycleableEntity>, ArrayList<Entity>> recycleMap;
    protected Queue<Entity> removalQueue;

    public EntityManager() {
        entityList = new ArrayList<>();
        recycleMap = new HashMap<>();
        removalQueue = new LinkedList<>();
    }

    protected void internalRemove() {
        while (!removalQueue.isEmpty()) {
            Entity e = removalQueue.poll();
            if (e instanceof RecycleableEntity) {
                ArrayList<Entity> recycleList = recycleMap.get(e.getClass());
                if (recycleList == null) {
                    recycleList = new ArrayList<>();
                }
                recycleList.add(e);
            }
            entityList.remove(removalQueue.poll());
        }

    }

    /**
     * 
     * @param delta
     *            deltaTime in miliseconds
     * @throws SlickException
     */
    public void update(GameContainer c, int delta) throws SlickException {
        internalRemove();
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
        removalQueue.add(e);
    }

    public void createEntity(Class<?> entityClass) {
        if (entityClass.isInstance(RecycleableEntity.class)) {

        }
    }
}
