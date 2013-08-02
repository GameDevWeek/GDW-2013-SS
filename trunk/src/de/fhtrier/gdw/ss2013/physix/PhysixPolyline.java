package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.collision.shapes.ChainShape;

public class PhysixPolyline extends PhysixShape {
    
    PhysixPolyline(PhysixShapeConfig config) {
        super(config);
        
        ChainShape shape = new ChainShape();
        shape.createChain(config.points, config.points.length);
        
        init(config, shape, 0, 0);
    }
}
