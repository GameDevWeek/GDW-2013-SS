package de.fhtrier.gdw.ss2013.game.world.spawner;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.SpawnerConstants;
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
		initialize();
	}
	
	public MeteoriteSpawner() {
		this(SpawnerConstants.METEORIT_DEFAULT_SPAWN_DELAY);
		initialize();
	}
	
	/**
	 * {@inheritDoc}
	 */
	protected void initialize() {
	    timeSinceLastSpawn = 0;
	}
	
	@Override
	public void update(GameContainer container, int delta) {
		timeSinceLastSpawn += delta;
		
		if (timeSinceLastSpawn > spawnDuration) {
			spawnMeteorite();
		}
	}
	
	private void spawnMeteorite() {
		EntityManager entityManager = World.getInstance().getEntityManager();
		PhysixManager physicsManager = World.getInstance().getPhysicsManager();
		
		// Dirty hack to get width and height of an Meteorite
		int width = AssetLoader.getInstance().getAnimation("meteorite").getWidth();
		int height = AssetLoader.getInstance().getAnimation("meteorite").getWidth();
		
		// Create a meteorite entity
		Entity entity = entityManager.createEntity(Meteroid.class);
        PhysixBox box = new PhysixBox(physicsManager, getPosition().x, getPosition().y, width, height,
                BodyType.KINEMATIC, 1, 0.5f, false);
        entity.setPhysicsObject(box);
		timeSinceLastSpawn -= spawnDuration;
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// do not render me!
	}
}
