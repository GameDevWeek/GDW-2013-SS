package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.camera.Camera;

public class PhysicsManager {

	public World getPhysicsWorld() {
		return _physicsWorld;
	}

	public PhysicsManager() {
		_physicsWorld = new World(_defaultGravity);
	}

	public void reset() {
		// It is not allowed to remove bodies from the world while it is locked.
		// Check locking state to prevent assertion in native library.
		if (_physicsWorld.isLocked()) {
			return;
		}
		Body body = _physicsWorld.getBodyList();
		while (body != null) {
			_physicsWorld.destroyBody(body);
			body = body.getNext();
		}
		return;

	}

	public void enableDebugDraw(boolean enabled) {
		_debugDraw = enabled;
	}

	public boolean isDebugDrawEnabled() {
		return _debugDraw;
	}

	public Vec2 getGravity() {
		return _physicsWorld.getGravity();
	}

	public void setGravity(Vec2 gravity) {
		_physicsWorld.setGravity(gravity);
	}

	public void update(GameContainer c, int delta) throws SlickException {
		// _physicsWorld.step(delta, 6, 3);
		_physicsWorld.step(1 / 60.f, 4, 2);
		//
		Body body = _physicsWorld.getBodyList();
		while (body != null) {
//			((PhysicsObject) body.m_userData).update(c, delta);
			body = body.getNext();
		}
		_physicsWorld.clearForces();
		/*
		 * for (Contact c1 = _physicsWorld.getContactList(); c1 != null; c1 = c1
		 * .getNext()) { PhysicsObject objectA = (PhysicsObject)
		 * c1.m_fixtureA.m_body.m_userData; PhysicsObject objectB =
		 * (PhysicsObject) c1.m_fixtureB.m_body.m_userData;
		 * objectA.onCollide(objectB); objectB.onCollide(objectA); }
		 */
		// _physicsWorld.drawDebugData();
	}

	public void enableSimulation(PhysicsObject object) {
		Body body = _physicsWorld.createBody(object.getBodyDef());
		object.setBody(body);
		object.myFixture = body.createFixture(object.myFixtureDef);
	}

	public void disableSimulation(PhysicsObject object) {
		_physicsWorld.destroyBody(object.getBody());
	}

	// @Override
	// public void beginContact(Contact contact) {
	// PhysicsObject objectA = (PhysicsObject)
	// contact.m_fixtureA.m_body.m_userData;
	// PhysicsObject objectB = (PhysicsObject)
	// contact.m_fixtureB.m_body.m_userData;
	// objectA.beginContact(objectB);
	// objectB.beginContact(objectA);
	// }
	//
	// @Override
	// public void endContact(Contact contact) {
	// PhysicsObject objectA = (PhysicsObject)
	// contact.m_fixtureA.m_body.m_userData;
	// PhysicsObject objectB = (PhysicsObject)
	// contact.m_fixtureB.m_body.m_userData;
	// objectA.endContact(objectB);
	// objectB.endContact(objectA);
	// }

	// @Override
	// public void postSolve(Contact arg0, ContactImpulse arg1) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void preSolve(Contact arg0, Manifold arg1) {
	// // TODO Auto-generated method stub
	//
	// }

	private static PhysicsManager _physicsManagerSingleton = null;
	private World _physicsWorld;
	private final Vec2 _defaultGravity = new Vec2(0.0f, 2 * 9.81f);
	private boolean _debugDraw;

	private IViewportTransform viewport;

	public void setTransformViewport(IViewportTransform viewport) {
		this.viewport = viewport;

	}

	public void drawDebugData(GameContainer container, Camera camera) {
		viewport.setCenter(container.getWidth() / 2 + camera.getOffsetX(),
				container.getHeight() / 2 + camera.getOffsetY());

		getPhysicsWorld().drawDebugData();
	}

	protected Vec2 toPhysicsWorld(Vector2f v) {
		Vec2 r = new Vec2();
		r = new Vec2(v.x, v.y);
		return r;
	}
}
