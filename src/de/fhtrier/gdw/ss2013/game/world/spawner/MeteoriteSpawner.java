package de.fhtrier.gdw.ss2013.game.world.spawner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import de.fhtrier.gdw.ss2013.constants.SpawnerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.enemies.Meteroid;

public class MeteoriteSpawner extends Entity {
	private int spawnDelay;
	private int startDelay;
	private int timeSinceLastSpawn;
	boolean init = false;
	
	/**
	 * @param spawnDuration Time between spawns of different meteorites in millisecs
	 */
	public MeteoriteSpawner(int spawnDuration) {
		super();
		this.spawnDelay = spawnDuration;
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
		if (!init) {
			if (getProperties().getProperty("delay") != null) {
				String newDelay = getProperties().getProperty("delay");
				try {
					this.spawnDelay = new Integer(newDelay);
				}
				catch (Exception e) {
					System.err.println("Warning: A MeteoriteSpawner got a wrong spawn delay value.");
				}
			}
			if (getProperties().getProperty("startDelay") != null) {
				String startDelay = getProperties().getProperty("startDelay");
				try {
					this.startDelay = new Integer(startDelay);
				}
				catch (Exception e) {
					System.err.println("Warning: A MeteoriteSpawner got a wrong spawn startDelay value.");
				}
			}
		}
		init = true;
		
		timeSinceLastSpawn += delta;
		
		if (timeSinceLastSpawn > spawnDelay+startDelay) {
			spawnMeteorite();
		}
	}
	
	private void spawnMeteorite() {
		EntityManager entityManager = World.getInstance().getEntityManager();
		
		// Create a meteorite entity
		Meteroid entity = entityManager.createEntity(Meteroid.class);
        entity.setSpawnPosition(getPosition().x,getPosition().y);

        timeSinceLastSpawn -= spawnDelay;
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// do not render me!
	}
}
