package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.collision.shapes.PolygonShape;

public class PhysixBox extends PhysixShape {
    
    PhysixBox(PhysixShapeConfig config) {
        super(config);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(config.halfWidth, config.halfHeight);
        this.dimension.set(config.halfWidth, config.halfHeight);
        
        init(config, shape, config.x, config.y);
    }
}
