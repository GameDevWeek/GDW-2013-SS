/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.commons.utils.ClassUtils;
import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.constants.MathConstants;
import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;

//TODO filter für getEntities
public class EntityManager {
    // static protected EntityManager managerInstance;
    protected HashMap<String, Entity> entityMap;
    protected Queue<Entity> removalQueue;
    protected Queue<Entity> insertionQueue;
    protected HashMap<String, Class<? extends Entity>> classMap = new HashMap<>();

    private EntityFactory factory;

    public EntityManager() {
        entityMap = new HashMap<>();

        removalQueue = new LinkedList<>();
        insertionQueue = new LinkedList<>();
        factory = new EntityFactory();

        try {
            for (Class c : ClassUtils
                    .findClassesInPackage("de.fhtrier.gdw.ss2013.game.world")) {
                if (Entity.class.isAssignableFrom(c))
                    classMap.put(c.getSimpleName().toLowerCase(), c);
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't find entity classes", e);
        }
    }

    public void reset() {
    	//FIXME offensichtlich wird hier nichts disposed
        for(Entity e: entityMap.values()) {
            factory.recycle(e);
        }
        
        for(Entity e: insertionQueue) {
            factory.recycle(e);
        }
        
        entityMap.clear();
        removalQueue.clear();
        insertionQueue.clear();
    }

    private void internalRemove() {

        while (!removalQueue.isEmpty()) {
            Entity e = removalQueue.poll();
            e.dispose();
            entityMap.remove(e.getName());            
        }
    }

    private void internalInsert() {
        while (!insertionQueue.isEmpty()) {
            Entity e = insertionQueue.poll();
            if (entityMap.containsKey(e.getName())) {
                String oldName = e.getName();
                e.setName();
                System.err.println("Warning: Changed " + oldName + " to "
                        + e.getName() + " to prevent Name Duplication");
            }
            e.initialize();
            entityMap.put(e.getName(), e);
        }
    }

    public void initalUpdate() {
        internalInsert();
        internalRemove();
    }

    /**
     * 
     * @param delta
     *            deltaTime in miliseconds
     * @throws SlickException
     */
    public void update(GameContainer c, int delta) throws SlickException {
        internalRemove();
        internalInsert();
        
        for (Entity e : entityMap.values())
            e.update(c, delta);

    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        for (Entity e : entityMap.values())
            e.render(container, g);
    }

    /**
     * 
     * @param position
     * @return entity reference an position, null falls keine entity an position
     */
    public Entity getEntityAtPosition(Vector2f position) {
        for (Entity e : entityMap.values()) {
            if (position.equals(e.getPosition())) {
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

        for (Entity e : entityMap.values()) {
            if ((position.distance(e.getPosition()) - radius) < MathConstants.EPSILON_F) {
                entities.add(e);
            }
        }
        return entities;
    }

    public Entity getEntityByName(String name) {
        return entityMap.get(name);
    }

    public ArrayList<Entity> getEntitiesByFilter(Class<? extends EntityFilter> filter) {
        ArrayList<Entity> filteredList = new ArrayList<>();
        for (Entity e : entityMap.values()) {
            if (filter.isAssignableFrom(e.getClass())) {
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public ArrayList<Entity> getClosestEntitiesByFilter(Vector2f position,
            float radius, Class<? extends EntityFilter> filter) {
        ArrayList<Entity> filteredList = new ArrayList<>();
        for (Entity e : entityMap.values()) {
            if (filter.isAssignableFrom(e.getClass())) {
                if ((position.distance(e.getPosition()) - radius) < MathConstants.EPSILON_F) {
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

    public Entity createEntity(String className, SafeProperties properties) {
        return createEntity(className, properties, null);
    }

    public Entity createEntity(String className, SafeProperties properties,
            String name) {
        Class<? extends Entity> entityClass = classMap.get(className
                .toLowerCase());
        if (entityClass == null) {
            throw new RuntimeException("Could not find entity class for: "
                    + className);
        }
        Entity e = factory.createEntity(entityClass);
        e.setName(name);
        e.setProperties(properties);
        addEntity(e);
        return e;
    }
}
