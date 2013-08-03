package de.fhtrier.gdw.ss2013.physix;

import de.fhtrier.gdw.ss2013.game.Entity;
import java.awt.Point;
import java.util.List;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class PhysixShapeConfig {
    final Entity entity;
    final PhysixManager manager;
    final BodyType bodyType;
    float x;
    float y;
    float halfWidth;
    float halfHeight;
    float density;
    float friction;
    boolean sensor;
    boolean active = true;
    short categoryBits = -1;
    short maskBits = -1;
    Vec2[] points;
    boolean fixedRotation = true;
    float linearDamping;
    float angularDamping;

    public PhysixShapeConfig(PhysixManager manager, Entity entity, BodyType type, float x, float y) {
        this.manager = manager;
        this.entity = entity;
        this.x = PhysixUtil.toBox2D(x);
        this.y = PhysixUtil.toBox2D(y);
        bodyType = type;
    }

    public PhysixShapeConfig density(float value) {
        density = value;
        return this;
    }

    public PhysixShapeConfig friction(float value) {
        friction = value;
        return this;
    }

    public PhysixShapeConfig sensor(boolean value) {
        sensor = value;
        return this;
    }

    public PhysixShapeConfig active(boolean value) {
        active = value;
        return this;
    }
    
    public PhysixShapeConfig category(short bits) {
        if(categoryBits == -1)
            categoryBits = bits;
        else
            categoryBits |= bits;
        return this;
    }
    
    public PhysixShapeConfig mask(short bits) {
        if(maskBits == -1)
            maskBits = bits;
        else
            maskBits |= bits;
        maskBits |= bits;
        return this;
    }
    
    public PhysixShapeConfig fixedRotation(boolean value) {
        fixedRotation = value;
        return this;
    }
    
    public PhysixShapeConfig linearDamping(float value) {
        linearDamping = value;
        return this;
    }
    
    public PhysixShapeConfig angularDamping(float value) {
        angularDamping = value;
        return this;
    }

    public void asBox(float width, float height) {
        halfWidth = PhysixUtil.toBox2D(width) * 0.5f;
        halfHeight = PhysixUtil.toBox2D(height) * 0.5f;
        
        if(entity != null && entity.isBottomPositioned() ){
            y -= halfHeight;
        }
        
        PhysixShape object = new PhysixBox(this);
        if(entity != null)
            entity.setPhysicsObject(object);
    }

    public void asCircle(float radius) {
        halfWidth = halfHeight = PhysixUtil.toBox2D(radius);
        PhysixShape object = new PhysixCircle(this);
        if(entity != null)
            entity.setPhysicsObject(object);
    }

    public void asPolygon(List<Point> points) {
        this.points = PhysixUtil.toBox2D(points);
        PhysixShape object = new PhysixPolygon(this);
        if(entity != null)
            entity.setPhysicsObject(object);
    }

    public void asPolyline(List<Point> points) {
        this.points = PhysixUtil.toBox2D(points);
        PhysixShape object = new PhysixPolyline(this);
        if(entity != null)
            entity.setPhysicsObject(object);
    }

    public void asPlayer(float width, float height) {
        halfWidth = PhysixUtil.toBox2D(width) * 0.5f;
        halfHeight = PhysixUtil.toBox2D(height) * 0.5f;
        PhysixShape object = new PhysixBoxPlayer(this);
        if(entity != null)
            entity.setPhysicsObject(object);
    }
}
