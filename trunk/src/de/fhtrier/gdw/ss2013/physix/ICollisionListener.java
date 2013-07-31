package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.dynamics.contacts.Contact;

public interface ICollisionListener {
    
    public void beginContact(Contact object);
    
    public void endContact(Contact object);
}
