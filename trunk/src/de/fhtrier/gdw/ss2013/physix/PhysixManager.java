package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class PhysixManager implements ContactListener {

    private static final float GRAVITY = 9.8f;
    private World world;
    private final Input input;

    public PhysixManager(GameContainer container) {
        this.input = container.getInput();
        world = new World(new Vec2(0, GRAVITY));
    }

    public void update(int delta) {

        world.step(1 / 30.0f, 10, 10); //performs a time step in the box2d simulation
        world.clearForces(); //used to clear the forces after performing the time step

    }

    public void render() {
        world.drawDebugData();
    }

    @Override
    public void beginContact(Contact contact) {
        PhysixObject objectA = (PhysixObject) contact.getFixtureA().getBody().getUserData();
        PhysixObject objectB = (PhysixObject) contact.getFixtureB().getBody().getUserData();
        objectA.beginContact(objectA, objectB);
        objectB.beginContact(objectA, objectB);
    }

    @Override
    public void endContact(Contact contact) {
        PhysixObject objectA = (PhysixObject) contact.getFixtureA().getBody().getUserData();
        PhysixObject objectB = (PhysixObject) contact.getFixtureB().getBody().getUserData();
        objectA.endContact(objectA, objectB);
        objectB.endContact(objectA, objectB);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    public void reset() {
		// It is not allowed to remove bodies from the world while it is locked.
		// Check locking state to prevent assertion in native library.
		if (!world.isLocked()) {
            Body body = world.getBodyList();
            while (body != null) {
                world.destroyBody(body);
                body = body.getNext();
            }
        }
    }

    public void enableDebugDraw(GameContainer container) {
        IViewportTransform viewportTransform = new OBBViewportTransform();
        world.setDebugDraw(new PhysixDebugDraw(viewportTransform, container.getGraphics()));
    }

    World getWorld() {
        return world;
    }
}
