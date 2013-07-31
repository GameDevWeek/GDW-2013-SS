package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;

public class PhysixBox extends PhysixObject {
    
    public PhysixBox(PhysixManager physicsManager, float x, float y, float width, float height, BodyType type, float density, float friction, boolean sensor) {
        super(density, friction, sensor);
        
        float px = PhysixUtil.toBox2D(x);
        float py = PhysixUtil.toBox2D(y);
        float halfWidth = PhysixUtil.toBox2D(width) * 0.5f;
        float halfHeight = PhysixUtil.toBox2D(height) * 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth, halfHeight);
        this.dimension.set(halfWidth, halfHeight);
        
        
        
        init(physicsManager.getWorld(), shape, type, px + halfWidth, py + halfHeight);
    }
}
