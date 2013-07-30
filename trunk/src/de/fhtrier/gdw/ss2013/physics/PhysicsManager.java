package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class PhysicsManager implements ContactListener {
    public static PhysicsManager getInstance() {
        if (_physicsManagerSingleton == null) {
            _physicsManagerSingleton = new PhysicsManager();
        }
        return _physicsManagerSingleton;
    }
    
    public World getPhysicsWorld() {
        return _physicsWorld;
    }

    private PhysicsManager() {
        _physicsWorld = new World(_defaultGravity);
        //_physicsWorld.setDebugDraw(new DebugDrawer());
        _physicsWorld.setContactListener(this);
    }

    public boolean reset() {
        // It is not allowed to remove bodies from the world while it is locked.
        // Check locking state to prevent assertion in native library.
        if (_physicsWorld.isLocked()) {
            return false;
        }
        // Make sure we delete the first body as well.
        Body body = _physicsWorld.getBodyList();
        for (Body bodyIterator = _physicsWorld.getBodyList(); bodyIterator
                != null; body = bodyIterator.getNext())
        {
            if (body != null) {
                _physicsWorld.destroyBody(body);
                body = null;
            }
        }
        _physicsWorld = new World(_defaultGravity);
        return true;
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
        _physicsWorld.step(delta, 6, 3);
        /*
        for (Contact c1 = _physicsWorld.getContactList(); c1 != null; c1 = c1
                .getNext()) {
            PhysicsObject objectA = (PhysicsObject) c1.m_fixtureA.m_body.m_userData;
            PhysicsObject objectB = (PhysicsObject) c1.m_fixtureB.m_body.m_userData;
            objectA.onCollide(objectB);
            objectB.onCollide(objectA);
        }
        */
        //_physicsWorld.drawDebugData();
    }

    public Body enableSimulation(PhysicsObject object) {
        return _physicsWorld.createBody(object.getBodyDef());
    }

    public void disableSimulation(PhysicsObject object) {
        _physicsWorld.destroyBody(object.getBody());
    }
    
    @Override
    public void beginContact(Contact contact) {
        PhysicsObject objectA = (PhysicsObject) contact.m_fixtureA.m_body.m_userData;
        PhysicsObject objectB = (PhysicsObject) contact.m_fixtureB.m_body.m_userData;
        objectA.beginContact(objectB);
        objectB.beginContact(objectA);
    }

    @Override
    public void endContact(Contact contact) {
        PhysicsObject objectA = (PhysicsObject) contact.m_fixtureA.m_body.m_userData;
        PhysicsObject objectB = (PhysicsObject) contact.m_fixtureB.m_body.m_userData;
        objectA.endContact(objectB);
        objectB.endContact(objectA);
    }

    @Override
    public void postSolve(Contact arg0, ContactImpulse arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void preSolve(Contact arg0, Manifold arg1) {
        // TODO Auto-generated method stub
        
    }

    private static PhysicsManager _physicsManagerSingleton = null;
    private World _physicsWorld;
    private final Vec2 _defaultGravity = new Vec2(0.0f, -9.81f);
    private boolean _debugDraw;
}
