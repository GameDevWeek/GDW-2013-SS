package de.fhtrier.gdw.ss2013.game.world;

import de.fhtrier.gdw.commons.tiled.Layer;
import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.commons.utils.SafeProperties;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Santo
 */
public class LevelLoader {

    public static void load(TiledMap map) {
        /// TODO: clear all entities, clear physics
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
                    createPoint(object.getType(), object.getX(), object.getY(), object.getProperties());
                    break;
                case RECT:
                    createRect(object.getType(), object.getX(), object.getY(), object.getWidth(), object.getHeight(), object.getProperties());
                    break;
                case TILE:
                    createTile(object.getType(), object.getX(), object.getY(), object.getWidth(), object.getHeight(), object.getProperties());
                    break;
                case POLYGON:
                    createPolygon(object.getType(), object.getPoints(), object.getProperties());
                    break;
                case POLYLINE:
                    createPolyLine(object.getType(), object.getPoints(), object.getProperties());
                    break;
            }
        }
    }

    /**
     * Create paths, ground, etc here
     *
     * @param type the type set in the editor
     * @param points the points of the line (absolute points)
     * @param properties the object properties
     */
    private static void createPolyLine(String type, ArrayList<Point> points, SafeProperties properties) {
        switch (type) {
            case "solid":
                /// TODO: create a solid line (static)
                break;
        }
    }

    /**
     * Create dangerzones, triggers, etc here
     *
     * @param type the type set in the editor
     * @param points the points of the line (absolute points)
     * @param properties the object properties
     */
    private static void createPolygon(String type, ArrayList<Point> points, SafeProperties properties) {
        switch (type) {
            case "solid":
                /// TODO: create a solid (static) object
                break;
            case "killzone":
                /// TODO: create a physics trigger, create an entity for it
                break;
        }
    }

    /**
     * Create rectangle dangerzones, triggers, etc here
     *
     * @param type the type set in the editor
     * @param x the distance from left in pixels
     * @param y the distance from top in pixels
     * @param width width in pixels
     * @param height height in pixels
     * @param properties the object properties
     */
    private static void createRect(String type, int x, int y, int width, int height, SafeProperties properties) {
        switch (type) {
            case "solid":
                /// TODO: create a solid (static) object
                break;
            case "killzone":
                /// TODO: create a physics trigger, create an entity for it
                break;
        }
    }

    /**
     * Create items, enemies, etc here
     *
     * @param type the type set in the editor
     * @param x the distance from left in pixels
     * @param y the distance from top in pixels
     * @param width width in pixels
     * @param height height in pixels
     * @param properties the object properties
     */
    private static void createTile(String type, int x, int y, int width, int height, SafeProperties properties) {
        /// TODO: create a physics object, create an entity for it
    }

    /**
     * Currently no plan for use
     *
     * @param type the type set in the editor
     * @param x the distance from left in pixels
     * @param y the distance from top in pixels
     * @param properties the object properties
     */
    private static void createPoint(String type, int x, int y, SafeProperties properties) {
    }
}
