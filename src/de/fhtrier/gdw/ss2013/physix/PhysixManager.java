package de.fhtrier.gdw.ss2013.physix;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
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

public class PhysixManager implements ContactListener {

    private World world;
    private final int UPDATE_TIME = 20;
    private long lastTime;

    public PhysixManager(GameContainer container) {
        float gravity = AssetLoader.getInstance().getGameData().world.gravity;
        world = new World(new Vec2(0, gravity));

        
        world.setContactListener(this);
//        world.setContactListener(new JumpTestListener()); // listener for jumptest of playerboxes
//        world.setContactListener(new EnemyContactListener());
        
    }

    public void update(int delta) {
        long time = System.currentTimeMillis();
        long timeDiff = time - lastTime;
        if(timeDiff > UPDATE_TIME) {
            lastTime = time;
            world.step((float)Math.min(60, timeDiff) * 0.002f, 10, 10);
            //System.out.println(delta + "/" +timeDiff);
            world.clearForces();
        } else {
            //System.out.println("---");
        }
    }

    public void render() {
        world.drawDebugData();
    }

    @Override
    public void beginContact(Contact contact) {
        PhysixObject objectA = (PhysixObject) contact.getFixtureA().getBody()
                .getUserData();
        PhysixObject objectB = (PhysixObject) contact.getFixtureB().getBody()
                .getUserData();
        if (objectA != null)
            objectA.beginContact(contact);
        if (objectB != null)
            objectB.beginContact(contact);
    }

    @Override
    public void endContact(Contact contact) {
        PhysixObject objectA = (PhysixObject) contact.getFixtureA().getBody()
                .getUserData();
        PhysixObject objectB = (PhysixObject) contact.getFixtureB().getBody()
                .getUserData();
        if (objectA != null)
            objectA.endContact(contact);
        if (objectB != null)
            objectB.endContact(contact);
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
        world.setDebugDraw(new PhysixDebugDraw(viewportTransform, container
                .getGraphics()));
    }

    World getWorld() {
        return world;
    }
}
