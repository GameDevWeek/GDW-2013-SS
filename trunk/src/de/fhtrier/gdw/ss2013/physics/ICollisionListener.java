package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.callbacks.ContactListener;

public abstract class ICollisionListener implements ContactListener {
    
    abstract void beginContact(PhysicsObject object);
    
    abstract void endContact(PhysicsObject object);
}
