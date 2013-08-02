package de.fhtrier.gdw.ss2013.game.world;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.camera.Camera;
import de.fhtrier.gdw.ss2013.game.camera.CameraInfo;
import de.fhtrier.gdw.ss2013.game.camera.ThreePointCamera;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.score.ScoreCounter;
import de.fhtrier.gdw.ss2013.input.InputManager;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.renderer.DynamicParticleSystem;
import de.fhtrier.gdw.ss2013.renderer.MapRenderer;
import de.fhtrier.gdw.ss2013.settings.DebugModeStatus;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.services.DefaultSoundPlayer;

public class World {

    private boolean shallBeReseted;

    private TiledMap map;
    private MapRenderer mapRender;
    private Camera camera;
    private ThreePointCamera tpCamera;
    private Astronaut astronaut;
    private Alien alien;
    private static ScoreCounter scoreCounter;

    private static World instance;

    public boolean debugDraw = DebugModeStatus.isTest();

    private EntityManager entityManager;
    private final PhysixManager physicsManager;

    private ArrayList<DynamicParticleSystem> particleList;
    private final GameContainer container;

    private String levelName;

    public World(GameContainer container, StateBasedGame game) {
        this.container = container;
        instance = this;
        levelName = DebugModeStatus.getLevelName();
        map = null;
        entityManager = new EntityManager();
        physicsManager = new PhysixManager(container);
        particleList = new ArrayList<>();
        scoreCounter = new ScoreCounter();
        reset();
    }

    private void reset() {
        entityManager.reset();
        physicsManager.reset();
        particleList.clear();

        try {
            map = AssetLoader.getInstance().loadMap(levelName);
            LevelLoader.load(map, entityManager, physicsManager);

            mapRender = new MapRenderer(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (DebugModeStatus.isTPCamera()) {
            CameraInfo info = new CameraInfo(2, map);
            tpCamera = new ThreePointCamera(info);
            tpCamera.setZoom(-100.00f);

            // tpCamera.addPointOfInterest(new PointOfInterest(1000, 500, 1,
            // 1000));
        }
        camera = new Camera(map);

        // physic debug stuff
        physicsManager.enableDebugDraw(container);

		Vector2f startpos = LevelLoader.getStartPosition();
        
		astronaut = entityManager.createEntity(Astronaut.class);
        astronaut.setOrigin(startpos);
		
		// astronaut.setPhysicsObject(new
		// RectanglePhysicsObject(BodyType.DYNAMIC, new Vec2(95,105), new
		// Vec2(astronaut.getPosition().x,astronaut.getPosition().y)));

		alien = entityManager.createEntity(Alien.class);
        alien.setOrigin(startpos);
		alien.setContainer(container);
        astronaut.setAlien(alien);

        SoundLocator.provide(new DefaultSoundPlayer(astronaut));

        InputManager.getInstance().setAstronautController(astronaut);
        InputManager.getInstance().setAlienController(astronaut);

        scoreCounter.reset();
        
        entityManager.initalUpdate();
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        Vector2f astronautPos = astronaut.getPosition();

        // Background image TODO: translate
        g.drawImage(AssetLoader.getInstance().getImage("world_background"), 0,
                0);
        if (DebugModeStatus.isTPCamera()) {
            tpCamera.pushViewMatrix(g);

            tpCamera.debugdraw(g, astronaut.getPosition().x + astronaut.getPhysicsObject().getDimension().x/2, astronaut.getPosition().y+astronaut.getPhysicsObject().getDimension().y/2);

            float ox = tpCamera.getTileX() * map.getTileWidth();
            float oy = tpCamera.getTileY() * map.getTileHeight();
            // mapRender
            // .renderTileLayers(g, (int)ox, (int)oy,
            // tpCamera.getTileX(), tpCamera.getTileY(),
            // map.getWidth(), map.getHeight());
            mapRender.renderTileLayers(g, 0, 0, 0, 0, map.getWidth(),
                    map.getHeight());
        } else {
            camera.update(container.getWidth(), container.getHeight(),
                    astronautPos.x, astronautPos.y);

            mapRender.renderTileLayers(g, -camera.getTileOverlapX(),
                    -camera.getTileOverlapY(), camera.getTileX(),
                    camera.getTileY(), camera.getNumTilesX(),
                    camera.getNumTilesY());

            g.pushTransform();
            g.translate(-camera.getOffsetX(), -camera.getOffsetY());

        }

        entityManager.render(container, g);
        for (DynamicParticleSystem p : particleList) {
            p.render();
        }

        if (debugDraw) {
            physicsManager.render();
        }

        g.popTransform();
        scoreCounter.render(g);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
        if (shallBeReseted) {
            reset();
            shallBeReseted = false;
        }

        physicsManager.update(delta);

        entityManager.update(container, delta);

        if (DebugModeStatus.isTPCamera()) {
            tpCamera.update(
                    delta,
                    container.getWidth(),
                    container.getHeight(),
                    astronaut.getPosition().x
                            + astronaut.getPhysicsObject().getDimension().x / 2,
                    astronaut.getPosition().y
                            + astronaut.getPhysicsObject().getDimension().y / 2);

            if (container.getInput().isKeyDown(Input.KEY_1)) {
                tpCamera.zoom(0.01f);
            }
            if (container.getInput().isKeyDown(Input.KEY_2)) {
                tpCamera.zoom(-0.01f);
            }

        }

        for (DynamicParticleSystem p : particleList) {
            p.update(delta);
        }

    }

    public Vector2f screenToWorldPosition(Vector2f screenPosition) {
        if (DebugModeStatus.isTPCamera()) {
            return tpCamera.screenToWorldPositionFromTarget(screenPosition);
        }
        /**
         * Top-left (0,0) / Bottom-right (width,height)
         */
        Vector2f worldPos = new Vector2f(camera.getOffsetX(),
                camera.getOffsetY());

        return worldPos.add(screenPosition);

    }

    public Vector2f worldToScreenPosition(Vector2f worldPosition) {
        if (DebugModeStatus.isTPCamera()) {
            return tpCamera.worldToScreenPosition(worldPosition);
        }

        Vector2f screenPos = new Vector2f(-camera.getOffsetX(),
                -camera.getOffsetY());

        return screenPos.add(worldPosition);
    }

    public Astronaut getAstronaut() {
        return astronaut;
    }

    public Alien getAlien() {
        return alien;
    }

    public Camera getCamera() {
        return camera;
    }
    
    public ThreePointCamera getTPCamera() {
    	return tpCamera;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public PhysixManager getPhysicsManager() {
        return physicsManager;
    }

    public static World getInstance() {
        return instance;
    }

    public void addParticle(DynamicParticleSystem p) {
        particleList.add(p);
    }

    public void removeParticle(DynamicParticleSystem p) {
        particleList.remove(p);
    }




	public void shallBeReseted(boolean shallBeReseted) {
		this.shallBeReseted = shallBeReseted;
	}
	
	public static ScoreCounter getScoreCounter() {
		return scoreCounter;
	}
	
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}
