package de.fhtrier.gdw.ss2013.physix;

import java.awt.Point;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;

public class PhysixPolygon extends PhysixObject {
    
    public PhysixPolygon(PhysixManager physicsManager, List<Point> points, BodyType type, float density, float friction, boolean sensor) {
        super(density, friction, sensor);
        
		PolygonShape shape = new PolygonShape();
		shape.set(PhysixUtil.toBox2D(points), points.size());
        
        init(physicsManager.getWorld(), shape, type, 0, 0);
        
        //TODO calculate dimension
    }
}
