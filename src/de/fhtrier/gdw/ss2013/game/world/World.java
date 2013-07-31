package de.fhtrier.gdw.ss2013.game.world;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.camera.Camera;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.enemies.FlyingEnemy;
import de.fhtrier.gdw.ss2013.game.world.enemies.GroundEnemy;
import de.fhtrier.gdw.ss2013.game.world.enemies.Meteroid;
import de.fhtrier.gdw.ss2013.game.world.objects.OxygenFlower;
import de.fhtrier.gdw.ss2013.game.world.objects.OxygenBubble;
import de.fhtrier.gdw.ss2013.input.InputManager;
import de.fhtrier.gdw.ss2013.physics.DebugDrawer;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixBoxPlayer;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;
import de.fhtrier.gdw.ss2013.renderer.MapRenderer;
import de.fhtrier.gdw.ss2013.settings.DebugModeStatus;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.services.DefaultSoundPlayer;

public class World {

    private TiledMap map;
    private MapRenderer mapRender;
    private Camera camera;
    private Astronaut astronaut;
    private Alien alien;
    private FlyingEnemy enemy[] = new FlyingEnemy[10];
    private GroundEnemy genemy[] = new GroundEnemy[10];
    private Meteroid metro[] = new Meteroid[3];
    private Input input;
    private OxygenFlower oxyFlower;
    private OxygenBubble bubble;
    private static World instance;
    
    // physics debug
    private DebugDrawer physicDebug;
    public boolean debugDraw = DebugModeStatus.isTest();
    private EntityManager entityManager;
    private final PhysixManager physicsManager;

    public World(GameContainer container, StateBasedGame game) {
        input = container.getInput();
        instance=this;
        map = null;
		entityManager = new EntityManager();
		physicsManager = new PhysixManager(container);
		try {
			map = AssetLoader.getInstance().loadMap("demo_sidescroller");
			LevelLoader.load(map, entityManager, physicsManager);

			mapRender = new MapRenderer(map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		camera = new Camera(map);

		// physic debug stuff
		if (debugDraw) {
			physicsManager.enableDebugDraw(container);
		}

		astronaut = entityManager.createEntity(Astronaut.class);
          PhysixObject physicsObject = new PhysixBoxPlayer(physicsManager, 400, 200,
                  50, 150);
          
		astronaut.setPhysicsObject(physicsObject);

		

        // astronaut.setPhysicsObject(new
        // RectanglePhysicsObject(BodyType.DYNAMIC, new Vec2(95,105), new
        // Vec2(astronaut.getPosition().x,astronaut.getPosition().y)));

        alien = entityManager.createEntity(Alien.class);
        physicsObject = new PhysixBox(physicsManager, 400, 200,
                10, 10, BodyType.DYNAMIC, 1, 0.5f, true);
        alien.setPhysicsObject(physicsObject);
        alien.setContainer(container);

      
        SoundLocator.provide(new DefaultSoundPlayer(astronaut));

//        oxyFlower = (OxygenFlower) entityManager.createEntity(
//                OxygenFlower.class);
//        physicsObject = new Vector2f(300, 300);
//        oxyFlower.setPhysicsObject(physicsObject);
//        for (int i = 0; i < enemy.length; i++) {
//            enemy[i] = entityManager.createEntity(FlyingEnemy.class);
//            physicsObject = new Vector2f(500 + i * 50, 500 + i * 50)
//            enemy[i].setPhysicsObject(physicsObject);
//            enemy[i].setReferences(entityManager, astronaut);
//            genemy[i] = entityManager.createEntity(GroundEnemy.class);
//            physicsObject = new Vector2f(100 + i * 100, 800 - i * 50)
//            genemy[i].setPhysicsObject(physicsObject);
//            genemy[i].setReferences(astronaut);
//        }
//        genemy[0] = entityManager.createEntity(GroundEnemy.class);
//        physicsObject = new Vector2f(100, 800)
//        genemy[0].setPhysicsObject(physicsObject);
//        genemy[0].setReferences(astronaut);
//        for (int i = 0; i < metro.length; i++) {
//            metro[i] = (Meteroid) entityManager.createEntity(Meteroid.class);
//            physicsObject = new Vector2f(200 + i * 100, 0)
//            metro[i].setPhysicsObject(physicsObject);
//            metro[i].setReferences(entityManager);
//        }
        // Ground Enemy AI test
//        genemy[0] = entityManager.createEntity(GroundEnemy.class);
//        physicsObject = new PhysixBox(physicsManager, 800, 800, 71, 64, BodyType.DYNAMIC, 1, 0.5f, false);
//        genemy[0].setPhysicsObject(physicsObject);
//        genemy[0].setReferences(astronaut);
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        Vector2f astronautPos = astronaut.getPosition();
        camera.update(container.getWidth(), container.getHeight(),
                astronautPos.x, astronautPos.y);

        // Background image TODO: translate
        g.drawImage(AssetLoader.getInstance().getImage("world_background"), 0,
                0);

        mapRender
                .renderTileLayers(g, -camera.getTileOverlapX(),
                        -camera.getTileOverlapY(), camera.getTileX(),
                        camera.getTileY(), camera.getNumTilesX(),
                        camera.getNumTilesY());

        g.pushTransform();
        g.translate(-camera.getOffsetX(), -camera.getOffsetY());

        // draw entities
        entityManager.render(container, g);

        if (debugDraw) {
            physicsManager.render();

        }
        
       
        g.popTransform();
    }
    
    public void onEnter()
    {
        InputManager.getInstance().setAstronautController(astronaut);
        InputManager.getInstance().setAlienController(alien);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
        physicsManager.update(delta);
        entityManager.update(container, delta);
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

    public Astronaut getAstronaut() {
        return astronaut;
    }

    public Alien getAlien() {
        return alien;
    }

    public Camera getCamera() {
        return camera;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public PhysixManager getPhysicsManager() {
        return physicsManager;
    }
    
    public static World getInstance()
    {
        return instance;
    }
    
   /* public void FrameSound()
    {
      System.out.println(astronaut.getAnimation().getFrame());
      if(alien.getAnimation().getFrame()%2==0)
      {
          Sound schritt3 = SoundLocator.loadSound("schritt1");
          SoundLocator.getPlayer().playSound(schritt3);
      }else
      {
          Sound schritt3 = SoundLocator.loadSound("schritt2");
          SoundLocator.getPlayer().playSound(schritt3);
      }
    }*/

  
}
