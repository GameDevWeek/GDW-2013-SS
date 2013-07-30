//Author: Jerry

package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class CirclePhysicsObject extends PhysicsObject {

	public CirclePhysicsObject() {
		this(BodyType.STATIC);
	}

	public CirclePhysicsObject(BodyType bodyType) {
		this(bodyType, 1);
	}

	public CirclePhysicsObject(BodyType bodyType,float radius) {
		this(bodyType, radius, new Vec2());
	}
	
   public CirclePhysicsObject(float radius, Vec2 pos) {
        this(BodyType.STATIC, radius, pos);
    }

	public CirclePhysicsObject(BodyType bodyType, float radius,
			Vec2 pos) {
		this(bodyType, radius, pos, 1);
	}

	public CirclePhysicsObject(BodyType bodyType, float radius,
			Vec2 pos, float restitution) {
		this(bodyType, radius, pos, restitution, 1);
	}
	
	public CirclePhysicsObject(BodyType bodyType, float radius,
            Vec2 pos, boolean isSensor) {
        this(bodyType, radius, pos, 0, 0, 0,isSensor);
    }

	public CirclePhysicsObject(BodyType bodyType, float radius,
			Vec2 pos, float restitution, float density) {
		this(bodyType, radius, pos, restitution, density, 1);
	}

	public CirclePhysicsObject(BodyType bodyType, float radius,
			Vec2 pos, float restitution, float density, float friction) {
		this(bodyType, radius, pos, restitution, density, friction, false);
	}

	public CirclePhysicsObject(BodyType bodyType, float radius,
			Vec2 pos, float restitution, float density, float friction,
			boolean isSensor) {
		super(restitution, density, friction, isSensor);
		CircleShape myShape = new CircleShape();
		myShape.m_radius = radius;
		init(myShape, bodyType,pos);
	}

}
