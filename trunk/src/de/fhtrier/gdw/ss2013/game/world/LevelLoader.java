package de.fhtrier.gdw.ss2013.game.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

import de.fhtrier.gdw.commons.tiled.Layer;
import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.world.objects.MovingPlatform;
import de.fhtrier.gdw.ss2013.game.world.objects.ObjectController;
import de.fhtrier.gdw.ss2013.physix.ButtonContactListener;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.physix.PhysixPolyline;
import de.fhtrier.gdw.ss2013.physix.SwitchContactListener;

/**
 * 
 * @author Santo
 */
public class LevelLoader {
    private static EntityManager entityManager;
    private static PhysixManager physicsManager;
    private static Vector2f startpos;

    // NOT THE NICE WAY!!!!!!
    private static HashMap<String, ObjectController> controllerbuff = new HashMap<>();
    private static HashMap<String, Interactable> interactablebuff = new HashMap<>();

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

        conntactInteractions();
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
                createTile(object.getType(), object.getX(), object.getLowestY(),
                        object.getWidth(), object.getHeight(),
                        object.getProperties(), object.getName());
                break;
            case POLYGON:
                createPolygon(object.getType(), object.getPoints(),
                        object.getProperties());
                break;
            case POLYLINE:
                createPolyLine(object.getType(), object.getPoints(),
                        object.getProperties(), object.getName());
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
            SafeProperties properties, String name) {
        Entity entity;
        switch (type) {
        case "solid":
            new PhysixPolyline(physicsManager, points, BodyType.STATIC, 1,
                    0.5f, false);
            break;
        case "platformLine":
            entity = entityManager.createEntity("movingPlatform", properties,
                    name);
            entity.setPhysicsObject(new PhysixBox(physicsManager,
                    points.get(0).x, points.get(0).y, 170, 36,
                    BodyType.KINEMATIC, 1, 0.5f, false));
            ((MovingPlatform) entity).initLine(points, properties);

            interactablebuff.put(entity.getName(), (Interactable) entity);
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
            System.out.println("Create solid box (" + x + "," + y + "(("
                    + width + "; " + height);
            new PhysixBox(physicsManager, x, y, width, height, BodyType.STATIC,
                    1, 0.5f, false);
            break;
        case "deadzone":
            entity = entityManager.createEntity(type, properties);
            PhysixBox box = new PhysixBox(physicsManager, x, y, width, height,
                    BodyType.STATIC, 1, 0.5f, true);
            entity.setPhysicsObject(box);
            break;
        default:
            System.err.println("Unknown Rect-Object in Map, type: " + type);
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
            int height, SafeProperties properties, String name) {
        // tiles must not have a type, but a type-property
        if (type == null)
            type = properties.getProperty("type");

        Entity entity;
        switch (type) {
        case "button":
            entity = entityManager.createEntity(type, properties, name);
            PhysixBox buttonBox = new PhysixBox(physicsManager, x, y, width,
                    height, BodyType.STATIC, 1, 0.5f, true);
            entity.setPhysicsObject(buttonBox);
            controllerbuff.put(entity.getName(), (ObjectController) entity);
            interactablebuff.put(entity.getName(), (Interactable) entity);
            break;
        case "switch":
            entity = entityManager.createEntity(type, properties, name);
            PhysixBox switchBox = new PhysixBox(physicsManager, x, y, width,
                    height, BodyType.STATIC, 1, 0.5f, true);
            switchBox.addCollisionListener(new SwitchContactListener());
            entity.setPhysicsObject(switchBox);
            controllerbuff.put(entity.getName(), (ObjectController) entity);
            interactablebuff.put(entity.getName(), (Interactable) entity);
            break;
        case "door":
            entity = entityManager.createEntity(type, properties, name);
            PhysixBox doorBox = new PhysixBox(physicsManager, x, y, width,
                    height, BodyType.STATIC, 1, 0.5f, false);
            entity.setPhysicsObject(doorBox);
            interactablebuff.put(entity.getName(), (Interactable) entity);
            break;
        case "start":
            startpos = new Vector2f(x, y);
            break;
        // WTF!!!
        // case "circle":
        // float radius = Math.max(width, height) / 2;
        // PhysixCircle circle = new PhysixCircle(physicsManager, x, y, radius,
        // BodyType.DYNAMIC, 1, 0.5f, false);
        // entity.setPhysicsObject(circle);
        // break;
        case "particle":
            String pname = properties.getProperty("name");
            if (pname != null) {
                ParticleSystem p = AssetLoader.getInstance().getParticle(pname);
                p.setPosition(x + (width / 2), y + (height / 2));
                World.getInstance().addParticle(p);
            }
            break;
        default:
            entity = entityManager.createEntity(type, properties, name);
            PhysixBox box = new PhysixBox(physicsManager, x, y, width, height,
                    BodyType.DYNAMIC, 1, 0.5f, false);
            entity.setPhysicsObject(box);
            break;
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

    private static void conntactInteractions() {
        // The Ugly Way
        for (ObjectController control : controllerbuff.values()) {
            System.out.println(control.getName());
            SafeProperties prop = control.getProperties();
            if (prop != null) {
                String[] triggers = prop.getProperty("trigger", "").split(",");
                for (String triggerName : triggers) {
                    Interactable toTrigger = interactablebuff.get(triggerName);
                    if (toTrigger != null) {
                        control.connectEntity((Interactable) toTrigger);
                    } else {
                        System.err.println(control.getName()
                                + " trys to connect a Not Interactable Entity "
                                + triggerName);
                    }
                }
            }
        }

        // The Nice Way
        /*
         * ArrayList<Entity> controller = entityManager.getEntitiesByFilter( new
         * Vector2f(), ObjectController.class);
         * 
         * for (Entity e : controller) { ObjectController control =
         * (ObjectController) e; SafeProperties prop = e.getProperties(); if
         * (prop != null) { String[] triggers = prop.getProperty("trigger",
         * "").split(","); for (String triggerName : triggers) { Entity
         * toTrigger = entityManager .getEntityByName(triggerName); if
         * (toTrigger instanceof Interactable) {
         * control.connectEntity((Interactable) toTrigger); } else {
         * System.err.println(e.getName() +
         * "trys to connect a Not Interactable Entey"); } } } // String[] args =
         * }
         */

    }

    /**
     * Returns startPosition, if one has been loaded, otherwise you'll get some
     * bullshit
     */
    public static Vector2f getStartPosition() {
        if (startpos == null) {
            return new Vector2f(400, 200);
        }
        return startpos;
    }
}
