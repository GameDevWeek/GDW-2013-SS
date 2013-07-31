package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

public class JumpTestListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(!(a.isSensor() ^ b.isSensor())) {
            return;
        }
        
        System.out.println(contact);
        if(a.isSensor()) { // a has check contact
            System.out.println(a.getBody().getUserData());
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
            
            if(objectA instanceof PhysixBoxPlayer) {
                ((PhysixBoxPlayer)objectA).isGrounded = true;
//                System.out.println(objectA + " is Grounded");
            }
        }
        else { // b is sensor
            System.out.println(b.getBody().getUserData());
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
            
            if(objectB instanceof PhysixBoxPlayer) {
                ((PhysixBoxPlayer)objectB).isGrounded = true;
//                System.out.println(objectB + " is Grounded");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(!(a.isSensor() ^ b.isSensor())) {
            return;
        }
        
        if(a.isSensor()) { // a has check contact
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
            if(objectA instanceof PhysixBoxPlayer) {
                ((PhysixBoxPlayer)objectA).isGrounded = false;
//                System.out.println(objectA + " is not Grounded");
            }
        }
        else { // b is sensor
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
            if(objectB instanceof PhysixBoxPlayer) {
                ((PhysixBoxPlayer)objectB).isGrounded = false;
//                System.out.println(objectB + " is not Grounded");
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }

}
