package de.fhtrier.gdw.ss2013.math;

import org.newdawn.slick.geom.Vector2f;

final public class Circle extends GeometricObject {
    float radius;

    public Circle(Vector2f center, float radius) {
        super(center);
        this.radius = radius;
    }

    @Override
    public boolean containsPoint(Vector2f p) {
        return Math.abs(p.distance(center) - radius) < MathConstants.EPSILON_F;
    }
}
