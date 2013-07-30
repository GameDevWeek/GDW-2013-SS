package de.fhtrier.gdw.ss2013.math;

import org.newdawn.slick.geom.Vector2f;

final public class AABB extends GeometricObject {
    private float halfExtentX;
    private float halfExtentY;

    public AABB(Vector2f center, float halfExtentX, float halfExtentY) {
        super(center);
        this.halfExtentX = halfExtentX;
        this.halfExtentY = halfExtentY;

    }

    @Override
    public boolean containsPoint(Vector2f p) {
        // TODO Auto-generated method stub
        return false;
    }
}
