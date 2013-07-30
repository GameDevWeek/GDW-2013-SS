package de.fhtrier.gdw.ss2013.math;

import org.newdawn.slick.geom.Vector2f;

public abstract class GeometricObject {
    protected Vector2f center;

    public GeometricObject(Vector2f center) {
        this.center = center;
    }

    public final Vector2f getCenter() {
        return center;
    }

    abstract public boolean containsPoint(Vector2f p);
}
