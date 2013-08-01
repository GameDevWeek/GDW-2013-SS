package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.enemies.Meteroid;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;

public class MeteoriteSpawner extends Entity {
	private int spawnDuration;
	private int timeSinceLastSpawn;
	
	/**
	 * @param spawnDuration Time between spawns of different meteorites in millisecs
	 */
	public MeteoriteSpawner(int spawnDuration) {
		super();
		this.spawnDuration = spawnDuration;
		this.timeSinceLastSpawn = 0;
	}
	
	public MeteoriteSpawner() {
		this(5000);
	}
	
	@Override
	public void update(GameContainer container, int delta) {
		timeSinceLastSpawn += delta;
		
		if (timeSinceLastSpawn > spawnDuration) {
			spawnMeteorite();
		}
	}
	
	private void spawnMeteorite() {
		System.out.println(System.currentTimeMillis()+": spawn it, baby!");
		EntityManager entityManager = World.getInstance().getEntityManager();
		PhysixManager physicsManager = World.getInstance().getPhysicsManager();
		
		// Dirty hack to get width and height of an Meteorite
		int width = AssetLoader.getInstance().getAnimation("meteorite").getWidth();
		int height = AssetLoader.getInstance().getAnimation("meteorite").getWidth();
		
		// Create a meteorite entity
		Entity entity = entityManager.createEntity(Meteroid.class);
        PhysixBox box = new PhysixBox(physicsManager, getPosition().x, getPosition().y+200, width, height,
                BodyType.DYNAMIC, 1, 0.5f, false);
        entity.setPhysicsObject(box);
		timeSinceLastSpawn -= spawnDuration;
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// do not render me!
	}
}
