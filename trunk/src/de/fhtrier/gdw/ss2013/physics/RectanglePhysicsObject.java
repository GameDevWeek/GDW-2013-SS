package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import de.fhtrier.gdw.ss2013.game.Entity;

public class RectanglePhysicsObject extends PhysicsObject {

    
    public RectanglePhysicsObject(Entity owner)
    {
        this(owner, BodyType.STATIC);
    }
    
    public RectanglePhysicsObject(Entity owner,BodyType bodyType)
    {
        this(owner, bodyType,  new Vec2(10,10));
    }
    
    public RectanglePhysicsObject(Entity owner,BodyType bodyType, Vec2 rec)
    {
        this(owner, bodyType,  rec,  new Vec2());
    }
    
    public RectanglePhysicsObject(Entity owner,BodyType bodyType, Vec2 rec, Vec2 pos)
    {
        this(owner, bodyType,  rec,  pos, 0);
    }
    
    public RectanglePhysicsObject(Entity owner,BodyType bodyType, Vec2 rec, Vec2 pos, float restitution)
    {
        this(owner, bodyType,  rec,  pos,  restitution, 0);
    }
    
    public RectanglePhysicsObject(Entity owner,BodyType bodyType, Vec2 rec, Vec2 pos, float restitution,float density)
    {
        this(owner, bodyType,  rec,  pos,  restitution, density, 0);
    }
    
    public RectanglePhysicsObject(Entity owner,BodyType bodyType, Vec2 rec, Vec2 pos, float restitution,float density,float friction )
    {
        this(owner, bodyType,  rec,  pos,  restitution, density, friction,true);
    }
    
    public RectanglePhysicsObject(Entity owner,BodyType bodyType, Vec2 rec, Vec2 pos, float restitution,float density,float friction,boolean isSensor ) {
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
