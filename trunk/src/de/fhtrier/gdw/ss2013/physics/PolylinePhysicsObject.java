//Author: Jerry

package de.fhtrier.gdw.ss2013.physics;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class PolylinePhysicsObject extends PhysicsObject {

	public PolylinePhysicsObject() {
		this(DEFAULT_BODYTYPE);
	}

	public PolylinePhysicsObject(BodyType bodyType) {
		this(bodyType, new ArrayList<Point>());
	}

	public PolylinePhysicsObject(BodyType bodyType, List<Point> pointList) {
		this(bodyType, pointList, DEFAULT_RESTITUTION);
	}

	public PolylinePhysicsObject(BodyType bodyType, List<Point> pointList, float restitution) {
		this(bodyType, pointList, restitution, DEFAULT_DENSITY);
	}
	
	public PolylinePhysicsObject(BodyType bodyType, List<Point> pointList, boolean isSensor) {
        this(bodyType, pointList, DEFAULT_RESTITUTION, DEFAULT_DENSITY, DEFAULT_FRICTION,isSensor);
    }

	public PolylinePhysicsObject(BodyType bodyType, List<Point> pointList, float restitution, float density) {
		this(bodyType, pointList, restitution, density, DEFAULT_FRICTION);
	}

	public PolylinePhysicsObject(BodyType bodyType, List<Point> pointList, float restitution, float density, float friction) {
		this(bodyType, pointList, restitution, density, friction, false);
	}

	public PolylinePhysicsObject(BodyType bodyType, List<Point> pointList, float restitution, float density, float friction,
			boolean isSensor) {
		super(restitution, density, friction, isSensor);
		ChainShape myShape = new ChainShape();
		myShape.m_vertices = Transform.pointListToVec2Arr(pointList);
		init(myShape, bodyType,new Vec2(0,0));
	}

}
