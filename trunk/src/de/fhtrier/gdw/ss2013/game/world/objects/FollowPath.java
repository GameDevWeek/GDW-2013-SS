package de.fhtrier.gdw.ss2013.game.world.objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import de.fhtrier.gdw.commons.utils.SafeProperties;

public class FollowPath{
    public static HashMap<String, FollowPath> paths = new HashMap<String, FollowPath>();
    private ArrayList<Point> points;
    private SafeProperties properties;
    private String name;

    public FollowPath(ArrayList<Point> points, SafeProperties properties, String name) {
        this.name = name;
        this.points = points;
        this.properties = properties;
        FollowPath.paths.put(this.name, this);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public SafeProperties getProperties() {
        return properties;
    }
}
