package de.fhtrier.gdw.ss2013.game;

import java.util.HashMap;
import java.util.LinkedList;

public class EntityFactory {

    protected HashMap<Class<? extends Entity>, LinkedList<Entity>> recycleMap;

    public EntityFactory() {
        recycleMap = new HashMap<>();
    }

    public void recycle(Entity e) {
        LinkedList<Entity> recycleList = recycleMap.get(e.getClass());
        if (recycleList == null) {
            recycleList = new LinkedList<>();
            recycleMap.put(e.getClass(), recycleList);
        }
        recycleList.add(e);
    }

    private boolean testRecyceability(Class<? extends Entity> entityClass) {
        
        if (RecycleableEntity.class.isAssignableFrom(entityClass)) {
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
}
