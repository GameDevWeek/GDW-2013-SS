package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class PhysicsManager {
    public PhysicsManager() {
        this(new Vec2(0.0f, -9.81f));
    }
    
    public PhysicsManager(Vec2 gravity) {
        _physicsWorld = new World(gravity);
    }
    
    public Vec2 getGravity() {
        return _physicsWorld.getGravity();
    }
    
    public void setGravity(Vec2 gravity) {
        _physicsWorld.setGravity(gravity);
    }
    
    public void update(GameContainer c, int delta) throws SlickException {
        _physicsWorld.step(delta, 6, 3);
        for(Contact c1 = _physicsWorld.getContactList(); c1 != null; c1 = c1.getNext()) {
           PhysicsObject objectA = (PhysicsObject)c1.m_fixtureA.m_body.m_userData;
           PhysicsObject objectB = (PhysicsObject)c1.m_fixtureB.m_body.m_userData;
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
    
    private World _physicsWorld;
}
