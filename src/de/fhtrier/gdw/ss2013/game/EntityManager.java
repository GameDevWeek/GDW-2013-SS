/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;

//TODO filter für getEntities
public class EntityManager {
    // static protected EntityManager managerInstance;
    protected ArrayList<Entity> entityList;
    protected HashMap<Class<? extends RecycleableEntity>, LinkedList<Entity>> recycleMap;
    protected Queue<Entity> removalQueue;

    public EntityManager() {
        entityList = new ArrayList<>();
        recycleMap = new HashMap<>();
        removalQueue = new LinkedList<>();
    }

    private void internalRemove() {
        while (!removalQueue.isEmpty()) {
            Entity e = removalQueue.poll();
            if (e instanceof RecycleableEntity) {
                LinkedList<Entity> recycleList = recycleMap.get(e.getClass());
                if (recycleList == null) {
                    recycleList = new LinkedList<>();
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

    /**
     * 
     * @param position
     * @return entity reference an position, null falls keine entity an position
     */
    public Entity getEntityAtPosition(Vector2f position) {
        for (Entity e : entityList) {
            if (position.equals(e.position)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 
     * @param position
     * @param radius
     *            radius der überprüfung
     * @return alle entities die in einem radius um einen Punkt liegen
     */
    public ArrayList<Entity> getClosestEntitiesAtPosition(Vector2f position,
            float radius) {
        ArrayList<Entity> entities = new ArrayList<>();
        final float EPSILON = 0.001f;
        for (Entity e : entityList) {
            if ((position.distance(e.position) - radius) < EPSILON) {
                entities.add(e);
            }
        }
        return entities;
    }

    public ArrayList<Entity> getEntitiesByFilter(Vector2f position,
            float radius, EntityFilter filter) {
        ArrayList<Entity> filteredList = new ArrayList<>();
        return null;
    }

    private void addEntity(Entity e) {
        entityList.add(e);
    }

    public void removeEntity(Entity e) {
        removalQueue.add(e);
    }

    private Entity internalCreate(Class<? extends Entity> entityClass) {
        Entity e = null;
        try {
            assert (entityClass.getConstructor() != null);
            e = entityClass.newInstance();
            addEntity(e);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    private Entity internalCreateAt(Class<? extends Entity> entityClass,
            Vector2f position) {

        Entity e = null;
        try {
            assert (entityClass.getConstructor(Vector2f.class) != null);
            e = entityClass.getConstructor(Vector2f.class)
                    .newInstance(position);
            addEntity(e);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    private boolean testRecyceability(Class<? extends Entity> entityClass) {
        if (entityClass.isInstance(RecycleableEntity.class)) {
            if (recycleMap.get(entityClass) != null
                    && !recycleMap.get(entityClass).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T createEntity(Class<? extends Entity> entityClass) {
        if (testRecyceability(entityClass)) {
            Entity e = recycleMap.get(entityClass).poll();
            assert (e != null);
            addEntity(e); // TODO delay add, falls möglich
            return (T) e;
        }

        Entity e = internalCreate(entityClass);
        assert (e != null);
        return (T) e;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T createEntityAt(
            Class<? extends Entity> entityClass, Vector2f position) {
        if (testRecyceability(entityClass)) {
            Entity e = recycleMap.get(entityClass).poll();
            assert (e != null);
            e.position = position;
            addEntity(e); // TODO delay add
            return (T) e;
        }
        Entity e = internalCreateAt(entityClass, position);
        e.position = position;
        return (T) e;
    }

}
