package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class PhysicsManager {
	public static PhysicsManager getInstance() {
		if (_physicsManagerSingleton == null) {
			_physicsManagerSingleton = new PhysicsManager();
		}
		return _physicsManagerSingleton;
	}

	private PhysicsManager() {
		_physicsWorld = new World(new Vec2(0.0f, 0f));
	}

	public Vec2 getGravity() {
		return _physicsWorld.getGravity();
	}

	public void setGravity(Vec2 gravity) {
		_physicsWorld.setGravity(gravity);
	}

	public void update(GameContainer c, int delta) throws SlickException {
		_physicsWorld.step(delta, 6, 3);
		for (Contact c1 = _physicsWorld.getContactList(); c1 != null; c1 = c1
				.getNext()) {
			PhysicsObject objectA = (PhysicsObject) c1.m_fixtureA.m_body.m_userData;
			PhysicsObject objectB = (PhysicsObject) c1.m_fixtureB.m_body.m_userData;
			objectA.onCollide(objectB);
			objectB.onCollide(objectA);
		}
	}

	public Body enableSimulation(PhysicsObject object) {
		return _physicsWorld.createBody(object.getBodyDef());
	}

	public void disableSimulation(PhysicsObject object) {
		_physicsWorld.destroyBody(object.getBody());
	}

	private static PhysicsManager _physicsManagerSingleton = null;
	// private
	public World _physicsWorld;
}
