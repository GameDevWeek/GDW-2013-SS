//Author: Jerry

package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.geom.Vector2f;

public class RectanglePhysicsObject extends PhysicsObject {

    public RectanglePhysicsObject() {
        this(DEFAULT_BODYTYPE);
    }

    public RectanglePhysicsObject(BodyType bodyType) {
        this(bodyType, new Vec2(10, 10));
    }

    public RectanglePhysicsObject(BodyType bodyType, Vec2 rec) {
        this(bodyType, rec, new Vec2());
    }

    public RectanglePhysicsObject(Vec2 rec, Vec2 pos) {
        this(DEFAULT_BODYTYPE, rec, pos);
    }

    public RectanglePhysicsObject(BodyType bodyType, Vec2 rec, Vec2 pos) {
        this(bodyType, rec, pos, DEFAULT_RESTITUTION);
    }

    public RectanglePhysicsObject(BodyType bodyType, Vec2 rec, Vec2 pos,
            float restitution) {
        this(bodyType, rec, pos, restitution, DEFAULT_DENSITY);
    }

    public RectanglePhysicsObject(BodyType bodyType, Vec2 rec, Vec2 pos,
            boolean isSensor) {
        this(bodyType, rec, pos, DEFAULT_RESTITUTION, DEFAULT_DENSITY,
                DEFAULT_FRICTION, isSensor);
    }

    private RectanglePhysicsObject(BodyType bodyType, Vec2 rec, Vec2 pos,
            float restitution, float density) {
        this(bodyType, rec, pos, restitution, density, DEFAULT_FRICTION);
    }

    public RectanglePhysicsObject(BodyType bodyType, Vec2 rec, Vec2 pos,
            float restitution, float density, float friction) {
        this(bodyType, rec, pos, restitution, density, friction,
                DEFAULT_ISSENSOR);
    }

    public RectanglePhysicsObject(BodyType bodyType, Vec2 rec, Vec2 pos,
            float restitution, float density, float friction, boolean isSensor) {
        super(restitution, density, friction, isSensor);
        PolygonShape myShape = new PolygonShape();
        myShape.setAsBox(rec.x, rec.y);
        init(myShape, bodyType, pos);
    }

    public RectanglePhysicsObject(Vector2f dimension, Vector2f topLeftPos) {
        this(DEFAULT_BODYTYPE, dimension, topLeftPos, 0, 0, 0, false);
    }

    public RectanglePhysicsObject(BodyType bodyType, Vector2f halfExtent,
            Vector2f worldPos) {
        this(bodyType, halfExtent, worldPos, 0, 0, 0, false);
    }

    public RectanglePhysicsObject(BodyType bodyType, Vector2f halfExtent,
            Vector2f worldPos, float restitution, float density, float friction) {
        this(bodyType, halfExtent, worldPos, restitution, density, friction,
                false);
    }

    public RectanglePhysicsObject(BodyType bodyType, Vector2f dimension,
            Vector2f topLeftPos, float restitution, float density,
            float friction, boolean isSensor) {
        super(restitution, density, friction, isSensor);
        PolygonShape myShape = new PolygonShape();
        Vec2 pHE = new Vec2(dimension.x, dimension.y);
        pHE = pHE.mul(0.5f);
        myShape.setAsBox(pHE.x, pHE.y);
        Vec2 pPos = new Vec2(topLeftPos.x, topLeftPos.y);
        init(myShape, bodyType, pPos = pPos.add(pHE));
    }
}
