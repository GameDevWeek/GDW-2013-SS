package de.fhtrier.gdw.ss2013.game;

import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.renderer.MapRenderer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class World {

    private TiledMap map;
    private MapRenderer mapRender;
    private final Camera camera;
    private Vector2f player = new Vector2f();
    private final Input input;

    public World(GameContainer container, StateBasedGame game) throws SlickException {
        input = container.getInput();
        try {
            map = new TiledMap("res/maps/demo.tmx", LayerObject.PolyMode.ABSOLUTE);
            mapRender = new MapRenderer(map);
            camera = new Camera(map);
        } catch (Exception e) {
            throw new SlickException(e.toString());
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        camera.update(container.getWidth(), container.getHeight(), player.x, player.y);

        mapRender.renderTileLayers(g,
                -camera.getTileOverlapX(), -camera.getTileOverlapY(),
                camera.getTileX(), camera.getTileY(),
                camera.getNumTilesX(), camera.getNumTilesY());
        g.pushTransform();
        g.translate(-camera.getOffsetX(), -camera.getOffsetY());
        g.drawRect(player.x - 5, player.y - 5, 10, 10);
        g.popTransform();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        float speed = 6;
        if (input.isKeyDown(Input.KEY_UP)) {
            player.y -= speed;
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            player.y += speed;
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            player.x -= speed;
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            player.x += speed;
        }
    }
}
