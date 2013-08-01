package de.fhtrier.gdw.ss2013.game.world.objects;

import java.awt.Point;
import java.util.ArrayList;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;

public class PlatformPath{
    private ArrayList<Point> points;
    private SafeProperties properties;

    public PlatformPath(ArrayList<Point> points, SafeProperties properties) {
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
