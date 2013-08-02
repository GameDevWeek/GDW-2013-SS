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
    final float x;
    final float y;
    float halfWidth;
    float halfHeight;
    float density;
    float friction;
    boolean sensor;
    Vec2[] points;

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

    public void asBox(float width, float height) {
        halfWidth = PhysixUtil.toBox2D(width) * 0.5f;
        halfHeight = PhysixUtil.toBox2D(height) * 0.5f;
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
