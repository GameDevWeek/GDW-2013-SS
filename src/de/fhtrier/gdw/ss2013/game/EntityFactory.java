package de.fhtrier.gdw.ss2013.game;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.geom.Vector2f;

public class EntityFactory {

    protected HashMap<Class<? extends RecycleableEntity>, LinkedList<Entity>> recycleMap;

    public EntityFactory() {
        recycleMap = new HashMap<>();
    }

    public void recycle(Entity e) {
        LinkedList<Entity> recycleList = recycleMap.get(e.getClass());
        if (recycleList == null) {
            recycleList = new LinkedList<>();
        }
        recycleList.add(e);
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

    private Entity internalCreate(Class<? extends Entity> entityClass) {
        Entity e = null;
        try {
            assert (entityClass.getConstructor() != null);
            e = entityClass.newInstance();
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

    @SuppressWarnings("unchecked")
    public <T extends Entity> T createEntity(Class<? extends Entity> entityClass) {
        if (testRecyceability(entityClass)) {
            Entity e = recycleMap.get(entityClass).poll();
            assert (e != null);

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
            e.setPosition(position);
            return (T) e;
        }
        Entity e = internalCreateAt(entityClass, position);
        e.setPosition(position);
        return (T) e;
    }
}
