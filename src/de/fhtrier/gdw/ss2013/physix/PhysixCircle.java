package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;

public class PhysixCircle extends PhysixObject {
    
    public PhysixCircle(PhysixManager physicsManager, float x, float y, float radius, BodyType type, float density, float friction, boolean sensor) {
        super(density, friction, sensor);
        
        CircleShape shape = new CircleShape();
        shape.m_radius = PhysixUtil.toBox2D(radius);
        dimension.set(shape.m_radius, shape.m_radius);
        
        init(physicsManager.getWorld(), shape, type, PhysixUtil.toBox2D(x), PhysixUtil.toBox2D(y));
    }
}
