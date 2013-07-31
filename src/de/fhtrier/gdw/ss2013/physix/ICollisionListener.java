package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.callbacks.ContactListener;

public interface ICollisionListener {
    
    public void beginContact(PhysixObject object);
    
    public void endContact(PhysixObject object);
}
