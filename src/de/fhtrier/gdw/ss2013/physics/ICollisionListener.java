package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.callbacks.ContactListener;

public abstract class ICollisionListener implements ContactListener {
    
    public abstract void beginContact(PhysicsObject object);
    
    public abstract void endContact(PhysicsObject object);
}
