package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.world.objects.Switch;

public class SwitchContactListener implements ICollisionListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (!(a.isSensor() ^ b.isSensor())) {
            return;
        }

        if (a.isSensor()) { // a has check contact
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();

            if (objectA.owner instanceof Switch) {
                Switch switchA = (Switch) objectA.owner;
                switchA.setCanBeTtriggerd(true);
            }
        } else { // b is sensor
            System.out.println(a.getBody().getUserData());
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();

            if (objectB.owner instanceof Switch) {
                Switch switchB = (Switch) objectB.owner;
                switchB.setCanBeTtriggerd(true);
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

            if (objectA.owner instanceof Switch) {
                Switch switchA = (Switch) objectA.owner;
                switchA.setCanBeTtriggerd(false);
            }
        } else { // b is sensor
            System.out.println(a.getBody().getUserData());
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();

            if (objectB.owner instanceof Switch) {
                Switch switchB = (Switch) objectB.owner;
                switchB.setCanBeTtriggerd(false);
            }
        }
    }

}
