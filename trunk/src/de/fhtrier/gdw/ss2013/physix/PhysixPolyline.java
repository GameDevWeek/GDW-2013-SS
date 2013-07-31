package de.fhtrier.gdw.ss2013.physix;

import java.awt.Point;
import java.util.List;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.dynamics.BodyType;

public class PhysixPolyline extends PhysixObject {
    
    public PhysixPolyline(PhysixManager physicsManager, List<Point> points, BodyType type, float density, float friction, boolean sensor) {
        super(density, friction, sensor);
        
        ChainShape shape = new ChainShape();
        shape.createChain(PhysixUtil.toBox2D(points), points.size());
        
        init(physicsManager.getWorld(), shape, type, 0, 0);
    }
}
