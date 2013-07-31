package de.fhtrier.gdw.ss2013.physix;

import java.util.HashMap;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

public class JumpTestListener implements ContactListener {

    HashMap<PhysixObject, Integer> contactCount = new HashMap<>();
    
    
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
                if(contactCount.get(objectA)==null) {
                    contactCount.put(objectA, new Integer(0));
                }
                contactCount.put(objectA, contactCount.get(objectA) + 1);
                ((PhysixBoxPlayer)objectA).isGrounded = true;
                
//                System.out.println(objectA + " is Grounded");
            }
        }
        else { // b is sensor
            System.out.println(b.getBody().getUserData());
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
            
            if(objectB instanceof PhysixBoxPlayer) {
                if(contactCount.get(objectB)==null) {
                    contactCount.put(objectB, new Integer(0));
                }
                contactCount.put(objectB, contactCount.get(objectB) + 1);
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
                if(contactCount.get(objectA)==null) {
                    ((PhysixBoxPlayer)objectA).isGrounded = false;
                }
                else {
//                    System.out.println(contactCount.get(objectA) - 1);
                    contactCount.put(objectA, contactCount.get(objectA) - 1);
                    ((PhysixBoxPlayer)objectA).isGrounded = contactCount.get(objectA) > 0;
                }
//                System.out.println(objectA + " is not Grounded");
            }
        }
        else { // b is sensor
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
            if(objectB instanceof PhysixBoxPlayer) {
                if(contactCount.get(objectB)==null) {
                    ((PhysixBoxPlayer)objectB).isGrounded = false;
                }
                else {
//                    System.out.println(contactCount.get(objectB) - 1);
                    contactCount.put(objectB, contactCount.get(objectB) - 1);
                    ((PhysixBoxPlayer)objectB).isGrounded = contactCount.get(objectB) > 0;
                }
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
