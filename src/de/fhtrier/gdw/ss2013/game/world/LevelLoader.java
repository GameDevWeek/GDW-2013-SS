package de.fhtrier.gdw.ss2013.game.world;

import java.awt.Point;
import java.util.ArrayList;

import de.fhtrier.gdw.commons.tiled.Layer;
import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixCircle;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.physix.PhysixPolyline;
import org.jbox2d.dynamics.BodyType;

/**
 * 
 * @author Santo
 */
public class LevelLoader {
    private static EntityManager entityManager;
    private static PhysixManager physicsManager;

    public static void load(TiledMap map, EntityManager entityManager,
            PhysixManager physicsManager) {
        LevelLoader.entityManager = entityManager;
        LevelLoader.physicsManager = physicsManager;
        entityManager.reset();
        physicsManager.reset();

        for (Layer layer : map.getLayers()) {
            if (layer.isObjectLayer()) {
                loadObjectLayer(layer);
            }
        }
    }

    private static void loadObjectLayer(Layer layer) {
        for (LayerObject object : layer.getObjects()) {
            switch (object.getPrimitive()) {
            case POINT:
                createPoint(object.getType(), object.getX(), object.getY(),
                        object.getProperties());
                break;
            case RECT:
                createRect(object.getType(), object.getX(), object.getY(),
                        object.getWidth(), object.getHeight(),
                        object.getProperties());
                break;
            case TILE:
                createTile(object.getType(), object.getX(), object.getY(),
                        object.getWidth(), object.getHeight(),
                        object.getProperties());
                break;
            case POLYGON:
                createPolygon(object.getType(), object.getPoints(),
                        object.getProperties());
                break;
            case POLYLINE:
                createPolyLine(object.getType(), object.getPoints(),
                        object.getProperties());
                break;
            }
        }
    }

    /**
     * Create ground, paths, etc here
     * 
     * @param type
     *            the type set in the editor
     * @param points
     *            the points of the line (absolute points)
     * @param properties
     *            the object properties
     */
    private static void createPolyLine(String type, ArrayList<Point> points,
            SafeProperties properties) {
        switch (type) {
        case "solid":
            new PhysixPolyline(physicsManager, points, BodyType.STATIC, 1, 0.5f, false);
            break;
        }
    }

    /**
     * Create deadzones, triggers, etc here
     * 
     * @param type
     *            the type set in the editor
     * @param points
     *            the points of the line (absolute points)
     * @param properties
     *            the object properties
     */
    private static void createPolygon(String type, ArrayList<Point> points,
            SafeProperties properties) {
        Entity entity;
        switch (type) {
        case "solid":

            // / TODO: create a solid (static) object
            break;
        case "deadzone":
            entity = entityManager.createEntity(type, properties);
            // / TODO: create a physics trigger
            entity.setPhysicsObject(null);
            break;
        }
    }

    /**
     * Create rectangle deadzones, triggers, etc here
     * 
     * @param type
     *            the type set in the editor
     * @param x
     *            the distance from left in pixels
     * @param y
     *            the distance from top in pixels
     * @param width
     *            width in pixels
     * @param height
     *            height in pixels
     * @param properties
     *            the object properties
     */
    private static void createRect(String type, int x, int y, int width,
            int height, SafeProperties properties) {
        Entity entity;
        switch (type) {
        case "solid":
            System.out
                    .println("Create solid box (" + x + "," + y + "((" + width + "; " + height);
            new PhysixBox(physicsManager, x, y, width, height, BodyType.STATIC, 1, 0.5f, false);
            break;
        case "deadzone":
            entity = entityManager.createEntity(type, properties);
            PhysixBox box = new PhysixBox(physicsManager, x, y, width, height, BodyType.STATIC, 1, 0.5f, true);
            entity.setPhysicsObject(box);
            break;
        default:
            System.err.println("Unknown Rect-Object in Map, type: "+type);
            break;
        }
    }

    /**
     * Create items, enemies, etc here
     * 
     * @param type
     *            the type set in the editor
     * @param x
     *            the distance from left in pixels
     * @param y
     *            the distance from top in pixels
     * @param width
     *            width in pixels
     * @param height
     *            height in pixels
     * @param properties
     *            the object properties
     */
    private static void createTile(String type, int x, int y, int width,
            int height, SafeProperties properties) {
        // tiles must not have a type, but a type-property
        if (type == null)
            type = properties.getProperty("type");
        
        // x|y are in Tiled the left bottom(!) corner, fix that:
        y -= height;
        
        Entity entity = entityManager.createEntity(type, properties);
        if (properties.getBoolean("circle", false)) {
            float radius = Math.max(width, height) / 2;
            PhysixCircle circle = new PhysixCircle(physicsManager, x, y, radius, BodyType.DYNAMIC, 1, 0.5f, false);
            entity.setPhysicsObject(circle);
        } else {
            PhysixBox box = new PhysixBox(physicsManager, x, y, width, height, BodyType.DYNAMIC, 1, 0.5f, false);
            entity.setPhysicsObject(box);
        }
    }

    /**
     * Currently no plan for use
     * 
     * @param type
     *            the type set in the editor
     * @param x
     *            the distance from left in pixels
     * @param y
     *            the distance from top in pixels
     * @param properties
     *            the object properties
     */
    private static void createPoint(String type, int x, int y,
            SafeProperties properties) {
    }
}
