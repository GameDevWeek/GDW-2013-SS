package de.fhtrier.gdw.ss2013.game.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.camera.Camera;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.game.world.enemies.FlyingEnemy;
import de.fhtrier.gdw.ss2013.game.world.enemies.Meteroid;
import de.fhtrier.gdw.ss2013.game.world.objects.OxygenFlower;
import de.fhtrier.gdw.ss2013.physics.DebugDrawer;
import de.fhtrier.gdw.ss2013.physics.PhysicsManager;
import de.fhtrier.gdw.ss2013.renderer.MapRenderer;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;

public class World {

	private TiledMap map;
	private MapRenderer mapRender;
	private Camera camera;
	private Player player;
	private FlyingEnemy enemy;
	private Meteroid metro[] = new Meteroid[3];
	private Input input;
	private OxygenFlower oxyFlower;

	// physics debug
	private DebugDrawer physicDebug;
	public boolean debugDraw = true;

	EntityManager entityManager;

	public World(GameContainer container, StateBasedGame game) {
		input = container.getInput();
		map = null;
		try {
			map = new TiledMap("res/maps/demo.tmx",
					LayerObject.PolyMode.ABSOLUTE);

			mapRender = new MapRenderer(map);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		camera = new Camera(map);

		entityManager = new EntityManager();

		// physic debug stuff
		if (debugDraw) {
			physicDebug = new DebugDrawer(container, camera);
			PhysicsManager.getInstance()._physicsWorld
					.setDebugDraw(physicDebug);
		}

		player = entityManager.createEntityAt(Player.class, new Vector2f(200,
				200));

		oxyFlower = (OxygenFlower) entityManager.createEntityAt(
				OxygenFlower.class, new Vector2f(300, 300));

		enemy = (FlyingEnemy) entityManager.createEntityAt(FlyingEnemy.class,
				new Vector2f(500, 500));
		for (int i = 0; i < metro.length; i++) {
			metro[i] = (Meteroid) entityManager.createEntityAt(Meteroid.class,
					new Vector2f(200 + i * 100, 0));
		}
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
		Vector2f playerPos = player.getPosition();
		camera.update(container.getWidth(), container.getHeight(), playerPos.x,
				playerPos.y);

		mapRender
				.renderTileLayers(g, -camera.getTileOverlapX(),
						-camera.getTileOverlapY(), camera.getTileX(),
						camera.getTileY(), camera.getNumTilesX(),
						camera.getNumTilesY());

		g.pushTransform();
		g.translate(-camera.getOffsetX(), -camera.getOffsetY());

		// draw entities
		entityManager.render(container, g);

		if (debugDraw)
			PhysicsManager.getInstance()._physicsWorld.drawDebugData();

		g.popTransform();
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
		// update entities
		entityManager.update(container, delta);

		PhysicsManager.getInstance().update(container, delta);

		// This is just a placeholder, not for actual use.
		Vector2f playerPos = player.getPosition();
		float speed = 6;
		if (input.isKeyDown(Input.KEY_UP)) {
			playerPos.y -= speed;
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			playerPos.y += speed;
		}
		if (input.isKeyDown(Input.KEY_LEFT)) {
			playerPos.x -= speed;
		}
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			playerPos.x += speed;
		}
		if (input.isKeyPressed(Input.KEY_F)) {
			enemy.shoot(player, entityManager);
		}
		Sound a = SoundLocator.loadSound("teamworld_testsound");
		SoundLocator.getPlayer().playSoundAt(a,
				new Entity(new Vector2f(100, 100)), player);

		if (input.isKeyPressed(Input.KEY_B)) {
			oxyFlower.shootBubbles(entityManager);
		}
	}

	public Vector2f screenToWorldPosition(Vector2f screenPosition) {
		/**
		 * Top-left (0,0) / Bottom-right (width,height)
		 */
		Vector2f worldPos = new Vector2f(camera.getOffsetX(),
				camera.getOffsetY());

		return worldPos.add(screenPosition);

	}

	public Vector2f worldToScreenPosition(Vector2f worldPosition) {
		Vector2f screenPos = new Vector2f(-camera.getOffsetX(),
				-camera.getOffsetY());

		return screenPos.add(worldPosition);
	}

	public Camera getCamera() {
		return camera;
	}

}
