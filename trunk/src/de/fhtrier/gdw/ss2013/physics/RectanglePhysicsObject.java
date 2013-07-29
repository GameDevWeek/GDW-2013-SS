package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.collision.Collision;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import de.fhtrier.gdw.ss2013.game.Entity;

public class RectanglePhysicsObject extends PhysicsObject {

    
    
    protected RectanglePhysicsObject(boolean isSensor, float restitution,float density,float friction,Vec2 pos,Vec2 rec,BodyType bodyType, Entity owner) {
        super(owner);
        EdgeShape myShape = new EdgeShape();
        myShape.set(pos,new Vec2(pos.x+rec.x, pos.y+rec.y));
        
        FixtureDef myFixtureDef = new FixtureDef();
        myFixtureDef.shape = myShape;
        myFixtureDef.restitution = restitution;
        myFixtureDef.density = density;
        myFixtureDef.friction = friction;
        myFixtureDef.isSensor = isSensor;
        
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = bodyType;
        
        setBodyDef(myBodyDef);
        enableSimulation();
        setFixtureDef(myFixtureDef);
    }
    
    

}
