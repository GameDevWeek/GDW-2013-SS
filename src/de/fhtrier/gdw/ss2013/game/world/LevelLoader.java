package de.fhtrier.gdw.ss2013.game.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.commons.tiled.Layer;
import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.GameDataInfo;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.world.enemies.FlyingEnemy;
import de.fhtrier.gdw.ss2013.game.world.objects.Door;
import de.fhtrier.gdw.ss2013.game.world.objects.FollowPath;
import de.fhtrier.gdw.ss2013.game.world.objects.MovingPlatform;
import de.fhtrier.gdw.ss2013.game.world.objects.ObjectController;
import de.fhtrier.gdw.ss2013.game.world.objects.Teleporter;
import de.fhtrier.gdw.ss2013.gui.TooltipManager;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.physix.PhysixPolyline;
import de.fhtrier.gdw.ss2013.renderer.DynamicParticleSystem;

/**
 * 
 * @author Santo
 */
public class LevelLoader {
    private static EntityManager entityManager;
    private static PhysixManager physicsManager;
    private static Vector2f startpos;
    private static GameDataInfo.WorldInfo worldInfo;
    private static HashMap<String, FollowPath> followPaths;

    public static void load(TiledMap map, EntityManager entityManager,
            PhysixManager physicsManager) {

        worldInfo = AssetLoader.getInstance().getGameData().world;
        followPaths = new HashMap<String, FollowPath>();

        LevelLoader.entityManager = entityManager;
        LevelLoader.physicsManager = physicsManager;
        entityManager.reset();
        physicsManager.reset();

        for (Layer layer : map.getLayers()) {
            if (layer.isObjectLayer()) {
                loadObjectLayer(layer);
            }
        }

        entityManager.initalUpdate();
        conntactInteractions();
        bindEntityToPath();
        setTeleporterTargets();
    }

    private static void loadObjectLayer(Layer layer) {
        // Two lists for tooltip, has to be that way, sorry
        ArrayList<LayerObject> tooltipTrigger = new ArrayList<>(); // contains
                                                                   // all
                                                                   // trigger
                                                                   // for a
                                                                   // tooltip
                                                                   // image
        HashMap<String, ArrayList<LayerObject>> tooltipImagesMap = new HashMap<>(); // contains
                                                                                    // images
                                                                                    // fitting
                                                                                    // to
                                                                                    // a
                                                                                    // trigger
        
        for (LayerObject object : layer.getObjects()) {
            String type = object.getType();
            if (object.getPrimitive() == LayerObject.Primitive.TILE)
                type = object.getProperty("type", null);
            if (type == null) {
                System.out.println("Warning: type missing for object!");
                continue;
            }
            if(object.getProperties() != null) {
                object.getProperties().setProperty("renderLayer", String.valueOf(layer.getIndex()));
            }
//            object.getProperties().setProperty("renderLayer", String.valueOf());
            switch (object.getPrimitive()) {
            case POINT:
                createPoint(type, object.getX(), object.getY(),
                        object.getProperties());
                break;
            case RECT:
                createRect(type, object.getX(), object.getY(),
                        object.getWidth(), object.getHeight(),
                        object.getProperties());
                break;
            case TILE:
                if (!isTooltipObject(object)) {
                    createTile(type, object.getX(), object.getLowestY(),
                            object.getWidth(), object.getHeight(),
                            object.getProperties(), object.getName());
                } else {
                    handleTooltipObject(object, tooltipTrigger,
                            tooltipImagesMap);
                }
                break;
            case POLYGON:
                createPolygon(type, object.getPoints(), object.getProperties());
                break;
            case POLYLINE:
                createPolyLine(object.getType(), object.getPoints(),
                        object.getProperties(), object.getName());
                break;
            }
        }
        handleAllTooltips(tooltipTrigger, tooltipImagesMap);
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
        switch (type) {
        case "solid":
            new PhysixPolyline(physicsManager, points, BodyType.STATIC,
                    worldInfo.density, worldInfo.friction, false);
            break;
        case "FollowPath":
            followPaths.put(name, new FollowPath(points, properties));
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
        switch (type) {
        case "solid":

            // / TODO: create a solid (static) object
            break;
        case "deadzone":
        case "winningzone":
            entityManager.createEntity(type, properties);

            // TODO physix
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
            new PhysixBox(physicsManager, x, y, width, height, BodyType.STATIC,
                    worldInfo.density, worldInfo.friction, false);
            break;
        case "deadzone":
        case "winningzone":
            entity = entityManager.createEntity(type, properties);
            PhysixBox box = new PhysixBox(physicsManager, x, y, width, height,
                    BodyType.STATIC, worldInfo.density, worldInfo.friction,
                    true);
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
     *            width in pixels, NOT ACCURATE!!
     * @param height
     *            height in pixels, NOT ACCURATE!!
     * @param properties
     *            the object properties
     */
    private static void createTile(String type, int x, int y, int width,
            int height, SafeProperties properties, String name) {

		PhysixBox box;
		Entity entity;
		switch (type) {
		case "button":
			entity = entityManager.createEntity(type, properties, name);
			PhysixBox buttonBox = new PhysixBox(physicsManager, x, y, width,
					height, BodyType.STATIC, worldInfo.density,
					worldInfo.friction, true);
			entity.setPhysicsObject(buttonBox);
			break;
		case "switch":
			entity = entityManager.createEntity(type, properties, name);
			PhysixBox switchBox = new PhysixBox(physicsManager, x, y, width,
					height, BodyType.STATIC, worldInfo.density,
					worldInfo.friction, true);
			entity.setPhysicsObject(switchBox);
			break;
		case "door":
			entity = entityManager.createEntity(type, properties, name);
			Door door = (Door) entity;
			door.setSpawnX(x);
			door.setSpawnY(y);
			break;
		case "start":
			startpos = new Vector2f(x, y);
			break;
		case "boss":
			String n = "thawahle";
			if (properties.getProperty("name") != null) {
				n = properties.getProperty("name");
			}
			else {
				System.err.println("You haven't choosen the boss name! Take default: "+name);
			}
			entity = entityManager.createEntity(n, properties, name);
			box = new PhysixBox(physicsManager, x, y, width, height,
					BodyType.DYNAMIC, worldInfo.density, worldInfo.friction,
					false);
			entity.setPhysicsObject(box);
			break;
		case "meteroid":
			entity = entityManager.createEntity("meteoritespawner", properties,
					name);
			box = new PhysixBox(physicsManager, x, y, 0, 0, BodyType.STATIC, 1,
					0.5f, true);
			entity.setPhysicsObject(box);
			break;
		case "smallPlatform":
			entity = entityManager.createEntity("smallmovingplatform", properties,
					name);
			PhysixBox platformBoxSmall = new PhysixBox(physicsManager, x
					- width / 2, y - height / 2, width, height,
					BodyType.KINEMATIC, worldInfo.density, worldInfo.friction,
					false);
			entity.setPhysicsObject(platformBoxSmall);
			break;
		case "bigPlatform":
			entity = entityManager.createEntity("bigmovingplatform", properties,
					name);
			PhysixBox platformBoxBig = new PhysixBox(physicsManager, x - width
					/ 2, y - height / 2, width, height, BodyType.KINEMATIC,
					worldInfo.density, worldInfo.friction, false);
			entity.setPhysicsObject(platformBoxBig);
			break;
		case "teleporter":
			entity = entityManager.createEntity(type, properties, name);
			box = new PhysixBox(physicsManager, x, y, 0, 0, BodyType.STATIC, 1,
					0.5f, true);
			entity.setPhysicsObject(box);
			break;
		case "oxygenflower":
			entity = entityManager.createEntity(type, properties, name);
			box = new PhysixBox(physicsManager, x, y, 0, 0, BodyType.STATIC, 1,
					0.5f, true);
			entity.setPhysicsObject(box);
			break;
		case "smallflyingenemy":
		case "middleflyingenemy":
		case "bigflyingenemy":
			entity = entityManager.createEntity(type, properties, name);
			box = new PhysixBox(physicsManager, x, y, width, height,
					BodyType.KINEMATIC, worldInfo.density, worldInfo.friction,
					false);
			entity.setPhysicsObject(box);
			break;
		case "particle":
			String pname = properties.getProperty("name");
			if (pname != null) {
				DynamicParticleSystem p = AssetLoader.getInstance()
						.getParticle(pname);
				p.setPosition(x + (width / 2), y + (height / 2));
				World.getInstance().addParticle(p);
			}
			break;
		default:
			entity = entityManager.createEntity(type, properties, name);
			box = new PhysixBox(physicsManager, x, y, width, height,
					BodyType.DYNAMIC, worldInfo.density, worldInfo.friction,
					false);
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

    private static void bindEntityToPath() {
        ArrayList<Entity> followingEntities = entityManager
                .getEntitiesByFilter(MovingPlatform.class);
        followingEntities.addAll(entityManager.getEntitiesByFilter(FlyingEnemy.class));
        
        for (Entity e : followingEntities) {
            if (e.getProperties() != null) {
                String name = e.getProperties().getProperty("path");
                if (name != null) {
                    FollowPath path = followPaths.get(name);
                    if (path != null) {
                        if (e instanceof FlyingEnemy) {
                            ((FlyingEnemy)e).initLine(path.getPoints(), path.getProperties());
                        } else {
                            ((MovingPlatform)e).initLine(path.getPoints(), path.getProperties());
                        }
                    } else {
                        System.err.println("Didn't find path " + name + "!");
                    }
                } else {
                    if (e instanceof MovingPlatform) {
                        System.err.println("Missing path option in "
                                + e.getName() + "!");
                    }
                }
            } else {
                System.err.println("No options in " + e.getName() + "!");
            }
        }
    }

    private static void setTeleporterTargets() {
        ArrayList<Entity> controller = entityManager
                .getEntitiesByFilter(Teleporter.class);

        for (Entity e : controller) {
            Teleporter teleporter = (Teleporter) e;
            SafeProperties prop = e.getProperties();
            if (prop != null) {
                String target = prop.getProperty("target");
                Entity targetTeleporter = entityManager.getEntityByName(target);
                if (targetTeleporter instanceof Teleporter) {
                    teleporter
                            .setTargetTeleporter((Teleporter) targetTeleporter);
                } else {
                    System.err.println(e.getName() + "can not find " + target);
                }
            }
        }
    }

    private static void conntactInteractions() {
        // The Ugly Way
        // for (ObjectController control : controllerbuff.values()) {
        // SafeProperties prop = control.getProperties();
        // if (prop != null) {
        // String[] triggers = prop.getProperty("trigger", "").split(",");
        // for (String triggerName : triggers) {
        // Interactable toTrigger = interactablebuff.get(triggerName);
        // if (toTrigger != null) {
        // control.connectEntity((Interactable) toTrigger);
        // } else {
        // System.err.println(control.getName()
        // + " trys to connect a Not Interactable Entity "
        // + triggerName);
        // }
        // }
        // }
        // }

        // The Nice Way

        ArrayList<Entity> controller = entityManager
                .getEntitiesByFilter(ObjectController.class);

        for (Entity e : controller) {
            ObjectController control = (ObjectController) e;
            SafeProperties prop = e.getProperties();
            if (prop != null) {
                String[] triggers = prop.getProperty("trigger", "").split(",");
                for (String triggerName : triggers) {
                    Entity toTrigger = entityManager
                            .getEntityByName(triggerName);
                    if (toTrigger instanceof Interactable) {
                        control.connectEntity((Interactable) toTrigger);
                    } else {
                        System.err.println(e.getName()
                                + " trys to connect a Not Interactable Entity "
                                + triggerName);
                    }
                }
            } // String[] args =
        }

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

    /**
     * Check, if an layerobject has to do something with tooltips
     * 
     * @author Robin
     */
    private static boolean isTooltipObject(LayerObject object) {
        String type = object.getProperties().getProperty("type");
        if (type != null) {
            return type.equals("tt_pic") || type.equals("tt_trigger");
        }
        return false;
    }

    /**
     * Handle different tooltip objects during insertion process
     * 
     * @author Robin
     */
    private static void handleTooltipObject(LayerObject object,
            ArrayList<LayerObject> tooltipTrigger,
            HashMap<String, ArrayList<LayerObject>> tooltipImagesMap) {
        String type = object.getProperties().getProperty("type");
        if (!isTooltipObject(object)) {
            System.err.println("Wrong tooltip object!");
            return;
        }
        if (type != null) {
            if (type.equals("tt_pic")) {
                String name = object.getName();
                if (name == null || name.equals("")) {
                    System.err
                            .println("You have failed during try to use tooltips! Object with type \'tt_pic\' has no name! Correct that!");
                    return;
                }
                ArrayList<LayerObject> tooltipImages = tooltipImagesMap
                        .get("name");
                if (tooltipImages == null) {
                    ArrayList<LayerObject> l = new ArrayList<>();
                    l.add(object);
                    tooltipImagesMap.put(name, l);
                } else {
                    tooltipImages.add(object);
                }
            } else {
                String triggerName = object.getProperties().getProperty(
                        "trigger");
                if (triggerName == null || triggerName.equals("")) {
                    System.err
                            .println("You have failed during try to use tooltips! Object with type \'tt_trigger\' has no trigger aim! Correct that!");
                    return;
                }
                tooltipTrigger.add(object);
            }
        }
    }

    /**
     * Give all correct tooltips to TooltipManager
     * 
     * @author Robin
     */
    private static void handleAllTooltips(
            ArrayList<LayerObject> tooltipTrigger,
            HashMap<String, ArrayList<LayerObject>> tooltipImagesMap) {
        TooltipManager ttm = TooltipManager.getInstance();

        for (LayerObject trigger : tooltipTrigger) {
            String triggerAim = trigger.getProperties().getProperty("trigger");
            ArrayList<LayerObject> triggeredImages = tooltipImagesMap
                    .get(triggerAim);
            if (triggeredImages == null) {
                System.err
                        .println("You have failed during try to use tooltips! There is no trigger-target with name "
                                + triggerAim);
                break;
            }
            for (LayerObject triggeredImage : triggeredImages) {
                int img_x = triggeredImage.getX();
                int img_y = triggeredImage.getY();
                Vector2f img_pos = new Vector2f(img_x, img_y);

                int trigger_x = trigger.getX();
                int trigger_y = trigger.getY();
                Vector2f trigger_pos = new Vector2f(trigger_x, trigger_y);

                String imageName = triggeredImage.getProperties().getProperty(
                        "image");
                if (imageName == null || imageName.equals("")) {
                    System.err
                            .println("You have failed during try to use tooltips! tt_pic does have no image-attribute.");
                    break;
                }
                Image img = AssetLoader.getInstance().getImage(imageName);
                if (img == null) {
                    System.err
                            .println("You have failed during try to use tooltips! tt_pic does use the image "
                                    + imageName + ", but that doesn't exist.");
                    break;
                }
                String radius = trigger.getProperties().getProperty("radius");
                if (radius == null || radius.equals("")) {
                    ttm.addTooltip(img_pos, img, trigger_pos);
                } else {
                    try {
                        Integer iRadius = new Integer(radius);
                        ttm.addTooltip(img_pos, img, trigger_pos, iRadius);
                    } catch (Exception e) {
                        System.err
                                .println("You have failed during try to use tooltips! tt_trigger uses the radius \'"
                                        + radius + "\', which is no number.");
                        break;
                    }
                }
            }
        }
    }
}
