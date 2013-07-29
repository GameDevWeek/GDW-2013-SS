package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.game.entities.Meteroid;
import de.fhtrier.gdw.ss2013.game.entities.OxygenFlower;
import de.fhtrier.gdw.ss2013.game.entities.FlyingEnemy;
import de.fhtrier.gdw.ss2013.physics.PhysicsManager;
import de.fhtrier.gdw.ss2013.renderer.MapRenderer;

public class World {

    private final TiledMap map;
    private final MapRenderer mapRender;
    private final Camera camera;
    private final Player player;
    private final FlyingEnemy enemy;
    private final Meteroid metro[];
    private final Input input;
    private final OxygenFlower oxyFlower;;
    // private final List<Entity> entities = new LinkedList<>();
    EntityManager entityManager;
    PhysicsManager physicsManager;

    public World(GameContainer container, StateBasedGame game)
            throws SlickException {
        input = container.getInput();
        try {
            map = new TiledMap("res/maps/demo.tmx",
                    LayerObject.PolyMode.ABSOLUTE);
            mapRender = new MapRenderer(map);
            camera = new Camera(map);
            metro = new Meteroid[3];
            entityManager = new EntityManager();
            physicsManager = new PhysicsManager();
            player = (Player) entityManager.createEntityAt(Player.class,
                    new Vector2f(200, 200));
            
            oxyFlower = (OxygenFlower) entityManager.createEntityAt(OxygenFlower.class, new Vector2f (300,300));

            enemy = (FlyingEnemy) entityManager.createEntityAt(FlyingEnemy.class, new Vector2f(500,500));
            for (int i = 0; i < 3; i++) {
                metro[i] = (Meteroid) entityManager.createEntityAt(Meteroid.class, new Vector2f(200+i*100,0));
            }
        } catch (Exception e) {
            throw new SlickException(e.toString());
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

        g.popTransform();
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
        // update entities
        entityManager.update(container, delta);

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
        if (input.isKeyPressed(Input.KEY_F)){
            enemy.shoot(player, entityManager);
        }
        
        if (input.isKeyPressed(Input.KEY_B))
        {
            oxyFlower.shootBubbles(entityManager);
        }
    }
    
    public Camera getCamera()
    {
    	return camera;
    }
    
    public PhysicsManager getPhysicsManager() {
        return physicsManager;
    }
}
