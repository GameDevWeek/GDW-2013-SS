//Author: Jerry

package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class RectanglePhysicsObject extends PhysicsObject {

	public RectanglePhysicsObject() {
		this(BodyType.STATIC);
	}

	public RectanglePhysicsObject(BodyType bodyType) {
		this(bodyType, new Vec2(10, 10));
	}

	public RectanglePhysicsObject(BodyType bodyType, Vec2 rec) {
		this(bodyType, rec, new Vec2());
	}
	
   public RectanglePhysicsObject(Vec2 rec, Vec2 pos) {
        this(BodyType.STATIC, rec, pos);
    }

	public RectanglePhysicsObject(BodyType bodyType, Vec2 rec,
			Vec2 pos) {
		this(bodyType, rec, pos, 1);
	}

	public RectanglePhysicsObject(BodyType bodyType, Vec2 rec,
			Vec2 pos, float restitution) {
		this(bodyType, rec, pos, restitution, 1);
	}
	
	public RectanglePhysicsObject(BodyType bodyType, Vec2 rec,
            Vec2 pos, boolean isSensor) {
        this(bodyType, rec, pos, 0, 0, 0,isSensor);
    }

	public RectanglePhysicsObject(BodyType bodyType, Vec2 rec,
			Vec2 pos, float restitution, float density) {
		this(bodyType, rec, pos, restitution, density, 1);
	}

	public RectanglePhysicsObject(BodyType bodyType, Vec2 rec,
			Vec2 pos, float restitution, float density, float friction) {
		this(bodyType, rec, pos, restitution, density, friction, false);
	}

	public RectanglePhysicsObject(BodyType bodyType, Vec2 rec,
			Vec2 pos, float restitution, float density, float friction,
			boolean isSensor) {
		super(restitution, density, friction, isSensor);
		PolygonShape myShape = new PolygonShape();
		myShape.setAsBox(rec.x, rec.y);
		init(myShape, bodyType,pos);
	}

}
