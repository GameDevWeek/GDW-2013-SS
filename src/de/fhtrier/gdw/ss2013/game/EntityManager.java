/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;
import de.fhtrier.gdw.ss2013.math.MathConstants;
import de.fhtrier.gdw.ss2013.physics.PhysicsObject;

//TODO filter für getEntities
public class EntityManager {
    // static protected EntityManager managerInstance;
    protected ArrayList<Entity> entityList;
    protected Queue<Entity> removalQueue;
    protected Queue<Entity> insertionQueue;

    private EntityFactory factory;

    public EntityManager() {
        entityList = new ArrayList<>();

        removalQueue = new LinkedList<>();
        insertionQueue = new LinkedList<>();
        factory = new EntityFactory();
    }

    public void reset() {
    }

    private void internalRemove() {
        while (!removalQueue.isEmpty()) {
            Entity e = removalQueue.poll();
            if (e instanceof RecycleableEntity) {
                factory.recycle(e);
            }
            entityList.remove(removalQueue.poll());
        }
    }

    private void internalInsert() {
        while (!insertionQueue.isEmpty()) {
            Entity e = insertionQueue.poll();
            entityList.add(e);
        }
    }

    /**
     * 
     * @param delta
     *            deltaTime in miliseconds
     * @throws SlickException
     */
    public void update(GameContainer c, int delta) throws SlickException {
        internalInsert();
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

        for (Entity e : entityList) {
            if ((position.distance(e.position) - radius) < MathConstants.EPSILON_F) {
                entities.add(e);
            }
        }
        return entities;
    }

    public ArrayList<Entity> getEntitiesByFilter(Vector2f position,
            EntityFilter filter) {
        ArrayList<Entity> filteredList = new ArrayList<>();
        for (Entity e : entityList) {
            if (e.getClass().isInstance(filter)) {
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public ArrayList<Entity> getClosestEntitiesByFilter(Vector2f position,
            float radius, EntityFilter filter) {
        ArrayList<Entity> filteredList = new ArrayList<>();
        for (Entity e : entityList) {
            if (e.getClass().isInstance(filter)) {
                if ((position.distance(e.position) - radius) < MathConstants.EPSILON_F) {
                    filteredList.add(e);
                }
            }
        }
        return filteredList;
    }

    private void addEntity(Entity e) {
        insertionQueue.add(e);
    }

    public void removeEntity(Entity e) {
        removalQueue.add(e);
    }

    public <T extends Entity> T createEntity(Class<? extends Entity> entityClass) {
        T e = factory.createEntity(entityClass);
        addEntity(e);
        assert (e != null);
        return e;
    }

    public <T extends Entity> T createEntityAt(
            Class<? extends Entity> entityClass, Vector2f position) {
        T e = factory.createEntityAt(entityClass, position);
        e.setPosition(position);
        addEntity(e);
        return e;
    }
    
    public Entity createEntity(String className, SafeProperties properties, PhysicsObject physicsObject) {
        Class<? extends Entity> entityClass = null;
        Entity e = factory.createEntity(entityClass);
        e.setPhysicsObject(physicsObject);
        physicsObject.setOwner(e);
        addEntity(e);
        return e;
    }
}
