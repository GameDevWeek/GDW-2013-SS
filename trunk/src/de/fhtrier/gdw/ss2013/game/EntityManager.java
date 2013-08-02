/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    protected LinkedList<Entity> entityList;
    protected HashMap<String, Entity> entityMap;
    protected Queue<Entity> removalQueue;
    protected Queue<Entity> insertionQueue;
    protected HashMap<String, Class<? extends Entity>> classMap = new HashMap<>();

    private EntityFactory factory;

    public EntityManager() {
        entityMap = new HashMap<>();
        entityList = new LinkedList<>();

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
        
        entityList.clear();
        entityMap.clear();
        removalQueue.clear();
        insertionQueue.clear();
    }

    private boolean internalRemove() {
        boolean listChanged = false;
        while (!removalQueue.isEmpty()) {
            listChanged = true;
            Entity e = removalQueue.poll();
            e.dispose();
            entityMap.remove(e.getName());
            entityList.remove(e);
        }
        return listChanged;
    }

    private boolean internalInsert() {
        boolean listChanged = false;
        while (!insertionQueue.isEmpty()) {
            listChanged = true;
            Entity e = insertionQueue.poll();

            if (entityMap.containsKey(e.getName())) {
                String oldName = e.getName();
                e.setName();
                System.err.println("Warning: Changed " + oldName + " to "
                        + e.getName() + " to prevent Name Duplication");
            }
            e.initialize();
            entityMap.put(e.getName(), e);
            entityList.add(e);
        }
        return listChanged;
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
        boolean iR_listChanged = internalRemove();
        boolean iI_listChanged = internalInsert();
        
        if(iR_listChanged || iI_listChanged) {
            sortEntityList();
            System.out.println(entityList.get(0));
        }
        
        for (Entity e : entityList)
            e.update(c, delta);

    }

    private void sortEntityList() {
        /**
         * höherer layer wird später gerendert
         */
        
        Collections.sort(entityList, new Comparator<Entity>() {

            @Override
            public int compare(Entity o1, Entity o2) {
               return o1.renderLayer==o2.renderLayer ? 0 : 
                   o1.renderLayer < o2.renderLayer ? -1 : 1;
            }

            
        });
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
        if(properties!=null) {
            e.setRenderLayer(properties.getInt("renderLayer", 0));
        } else {
            e.setRenderLayer(99);
        }
        e.setProperties(properties);
        addEntity(e);
        return e;
    }
}
