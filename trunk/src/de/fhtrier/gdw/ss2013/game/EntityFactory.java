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

	private Entity internalCreate(Class<? extends Entity> entityClass) {
		Entity e = null;
		try {
			assert (entityClass.getConstructor() != null);
			e = entityClass.newInstance();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T createEntity(Class<? extends Entity> entityClass) {
		Entity e;
		e = internalCreate(entityClass);
		return (T) e;
	}
}
