package de.fhtrier.gdw.ss2013.physix;


import org.jbox2d.collision.shapes.PolygonShape;

public class PhysixPolygon extends PhysixShape {
    
    PhysixPolygon(PhysixShapeConfig config) {
        super(config);
        
		PolygonShape shape = new PolygonShape();
		shape.set(config.points, config.points.length);
        
        init(config, shape, 0, 0);
        
        //TODO calculate dimension
    }
}
