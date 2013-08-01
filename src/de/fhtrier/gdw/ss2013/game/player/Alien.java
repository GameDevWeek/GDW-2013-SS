package de.fhtrier.gdw.ss2013.game.player;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.camera.Camera;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.objects.Box;
import de.fhtrier.gdw.ss2013.input.AlienController;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;

public class Alien extends Player implements AlienController {

	private int selectedAbility;
	private float mana;
	private float maxMana;
	private final Vector2f cursor = new Vector2f();
	private GameContainer container;

	private EntityManager entityManager = World.getInstance().getEntityManager();
	// telekinese values
	private Camera camera = World.getInstance().getCamera();
	private Box currentSelectedBox;
	private float selectionRadius;
	private final Vector2f oldcursor = new Vector2f();
	private final Vector2f dragDirection = new Vector2f();

	private long lastShotTime;

	public Alien() {
		setAnimation(AssetLoader.getInstance().getAnimation("alien_standing"));
		// Alien does NOT have different movestates! byRobin
		initialize();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize() {
	    super.initialize();
	    currentSelectedBox = null;
	    selectionRadius = 50;
	    lastShotTime = 0;
	    selectedAbility = 1;
        maxMana = 0.0f;
        mana = maxMana;
	}

	public void setContainer(GameContainer container) {
		this.container = container;
	}

	public float getMana() {
		return mana;
	}

	public void setMana(float mana) {
		this.mana = mana;
	}

	public float getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(float maxMana) {
		this.maxMana = maxMana;
	}

	@Override
	public void shoot() {
		if (System.currentTimeMillis() > lastShotTime + PlayerConstants.SHOTDELAY) {
			Vector2f playerPos;
			if (World.getInstance().getAstronaut().isCarryAlien()) {
				playerPos = World.getInstance().getAstronaut().getPosition(); // notwendig, weil alienPos net stimmt
			}
			else {
				playerPos = getPosition();
			}
			Vector2f shootDirection = new Vector2f(World.getInstance().screenToWorldPosition(getCursor()));
			shootDirection.sub(playerPos);
			
			shootDirection = shootDirection.normalise().scale(PlayerConstants.BULLET_SPEED);

			EntityManager entityManager = World.getInstance().getEntityManager();
			PhysixManager physicsManager = World.getInstance().getPhysicsManager();

			// Dirty hack to get width and height of an Meteorite
			int width = AssetLoader.getInstance().getImage("boltEnemy").getWidth();
			int height = AssetLoader.getInstance().getImage("boltEnemy").getHeight();

			// Create a meteorite entity
			Entity entity = entityManager.createEntity(PlayerBullet.class);
			PhysixBox box = new PhysixBox(physicsManager, getAstronaut().getPosition().x,
					getAstronaut().getPosition().y, width, height, BodyType.KINEMATIC, 1, 0.5f, true);
			entity.setPhysicsObject(box);
			entity.setVelocity(shootDirection);

			lastShotTime = System.currentTimeMillis();
		}
	}

	private Astronaut getAstronaut() {
		return World.getInstance().getAstronaut();
	}

	@Override
	public void nextAbility() {
		selectedAbility = (selectedAbility % 3) + 1;

		Log.debug("rotate ability");
	}

	@Override
	public void previousAbility() {

		/*
		 * if (selectedAbility > 0){ selectedAbility--; }else{ selectedAbility =
		 * 3; }
		 */

		selectedAbility = ((selectedAbility + 1) % 3) + 1;

	}

	public int getselectedAbility() {
		return selectedAbility;
	}

	@Override
	public void useAbility() {
		Log.debug("using ability");

		switch (selectedAbility) {
		case 1:
			// telekinese
			if (currentSelectedBox == null) {
				for (Entity e : entityManager.getClosestEntitiesAtPosition(
						cursor.add(new Vector2f(camera.getOffsetX(), camera.getOffsetY())), selectionRadius)) {
					System.out.println(e.getName());
					if (e instanceof Box) {
						currentSelectedBox = (Box) e;
					}
				}

			}
			else {
				currentSelectedBox = null;
			}
			break;
		}
	}

	Vector2f screenCursor = new Vector2f();
	@Override
	public void setCursor(int x, int y) {
//	    screenCursor.set(x, y);
//	    Vector2f worldSpace = World.getInstance().screenToWorldPosition(screenCursor);
//	    cursor.set(worldSpace.x, worldSpace.y);
		cursor.set(Math.min(container.getWidth(), Math.max(0, x)), Math.min(container.getHeight(), Math.max(0, y)));
		// TODO Auto-generated method stub
		// Log.debug("target direction");
		// setZustand("animtest");
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub
		super.update(container, delta);

		// if (World.getInstance().getAstronaut().isCarryAlien() == true) {
		// this.setPosition(World.getInstance().getAstronaut().getPosition().x,
		// (World.getInstance().getAstronaut().getPosition().y));
		// }

		switch (selectedAbility) {
		case 1:
			// telekinese
			if (currentSelectedBox != null) {
				dragDirection.x = cursor.x + camera.getOffsetX() - currentSelectedBox.getPosition().x;
				dragDirection.y = cursor.y + camera.getOffsetY() - currentSelectedBox.getPosition().y;
				currentSelectedBox.setVelocity(dragDirection);
				if (currentSelectedBox.isPlayerOnBox()) {
					currentSelectedBox.setVelocity(new Vector2f(0, -10));
					currentSelectedBox = null;
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		Astronaut astronaut = World.getInstance().getAstronaut();
		// Just render alien if astronaut does not carry the alien
		if (astronaut != null && !astronaut.isCarryAlien()) {
			super.render(container, g);
		}
	}

	@Override
	public void cursorLeft(float scale) {
		cursor.x = Math.min(container.getWidth(), Math.max(0, cursor.x - 10.0f * scale));
	}

	@Override
	public void cursorRight(float scale) {
		cursor.x = Math.min(container.getWidth(), Math.max(0, cursor.x + 10.0f * scale));
	}

	@Override
	public void cursorUp(float scale) {
		cursor.y = Math.min(container.getHeight(), Math.max(0, cursor.y - 10.0f * scale));
	}

	@Override
	public void cursorDown(float scale) {
		cursor.y = Math.min(container.getHeight(), Math.max(0, cursor.y + 10.0f * scale));
	}

	public Vector2f getCursor() {
		return cursor.copy();
	}

}
