//Author: Jerry

package de.fhtrier.gdw.ss2013.physics;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class PolygonPhysicsObject extends PhysicsObject {

	public PolygonPhysicsObject() {
		this(DEFAULT_BODYTYPE);
	}

	public PolygonPhysicsObject(BodyType bodyType) {
		this(bodyType, new ArrayList<Point>());
	}

	public PolygonPhysicsObject(BodyType bodyType, List<Point> pointList) {
		this(bodyType, pointList, DEFAULT_RESTITUTION);
	}

	public PolygonPhysicsObject(BodyType bodyType, List<Point> pointList, float restitution) {
		this(bodyType, pointList, restitution, DEFAULT_DENSITY);
	}
	
	public PolygonPhysicsObject(BodyType bodyType, List<Point> pointList, boolean isSensor) {
        this(bodyType, pointList, DEFAULT_RESTITUTION, DEFAULT_DENSITY, DEFAULT_FRICTION,isSensor);
    }

	public PolygonPhysicsObject(BodyType bodyType, List<Point> pointList, float restitution, float density) {
		this(bodyType, pointList, restitution, density, DEFAULT_FRICTION);
	}

	public PolygonPhysicsObject(BodyType bodyType, List<Point> pointList, float restitution, float density, float friction) {
		this(bodyType, pointList, restitution, density, friction, false);
	}

	public PolygonPhysicsObject(BodyType bodyType, List<Point> pointList, float restitution, float density, float friction,
			boolean isSensor) {
		super(restitution, density, friction, isSensor);
		PolygonShape myShape = new PolygonShape();
		myShape.set(PhysicsTools.pointListToVec2Arr(pointList), pointList.size());
		init(myShape, bodyType,new Vec2(0,0));
	}

}
