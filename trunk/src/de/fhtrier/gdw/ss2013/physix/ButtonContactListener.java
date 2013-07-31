package de.fhtrier.gdw.ss2013.physix;

import java.util.HashMap;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.world.objects.Button;

public class ButtonContactListener implements ICollisionListener {

    HashMap<PhysixObject, Integer> contactCount = new HashMap<>();

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (!(a.isSensor() ^ b.isSensor())) {
            return;
        }

        if (a.isSensor()) { // a has check contact
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();

            if (objectA.owner instanceof Button) {
                if (contactCount.get(objectA) == null) {
                    contactCount.put(objectA, new Integer(0));
                }
                Button buttonA = (Button) objectA.owner;
                buttonA.setSwitch(true);
            }
        } else { // b is sensor
            System.out.println(a.getBody().getUserData());
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();

            if (objectB.owner instanceof Button) {
                if (contactCount.get(objectB) == null) {
                    contactCount.put(objectB, new Integer(0));
                }
                Button buttonB = (Button) objectB.owner;
                buttonB.setSwitch(true);
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (!(a.isSensor() ^ b.isSensor())) {
            return;
        }

        if (a.isSensor()) { // a has check contact
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();

            if (objectA.owner instanceof Button) {
                if (contactCount.get(objectA) == null) {
                    contactCount.put(objectA, new Integer(0));
                }
                if (contactCount.size() <= 0) {
                    Button buttonA = (Button) objectA.owner;
                    buttonA.setSwitch(false);
                }
            }
        } else { // b is sensor
            System.out.println(a.getBody().getUserData());
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();

            if (objectB.owner instanceof Button) {
                if (contactCount.get(objectB) == null) {
                    contactCount.put(objectB, new Integer(0));
                }
                if (contactCount.size() <= 0) {
                    Button buttonB = (Button) objectB.owner;
                    buttonB.setSwitch(false);
                }
            }
        }
    }
}
