package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.collision.shapes.CircleShape;

public class PhysixCircle extends PhysixShape {
    
    PhysixCircle(PhysixShapeConfig config) {
        super(config);
        
        CircleShape shape = new CircleShape();
        shape.m_radius = config.halfWidth;
        dimension.set(shape.m_radius, shape.m_radius);
        
        init(config, shape, config.x, config.y);
    }
}
