package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.callbacks.ContactListener;

public abstract class ICollisionListener implements ContactListener {
    
    public abstract void beginContact(PhysixObject object);
    
    public abstract void endContact(PhysixObject object);
}
