package de.fhtrier.gdw.ss2013.game.world.objects;

import java.awt.Point;
import java.util.ArrayList;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;

public class PlatformPath extends Entity implements EntityFilter{
    private ArrayList<Point> line;

    public PlatformPath() {
        super();
        line = new ArrayList<Point>();
    }
    
    public ArrayList<Point> getLine() {
        return line;
    }

    public void setLine(ArrayList<Point> line) {
        this.line = line;
    }
}
