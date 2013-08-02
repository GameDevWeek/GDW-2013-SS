package de.fhtrier.gdw.ss2013.game.world.objects;

import java.awt.Point;
import java.util.ArrayList;

import de.fhtrier.gdw.commons.utils.SafeProperties;

public class FollowPath{
    private ArrayList<Point> points;
    private SafeProperties properties;

    public FollowPath(ArrayList<Point> points, SafeProperties properties) {
        this.points = points;
        this.properties = properties;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public SafeProperties getProperties() {
        return properties;
    }
}
