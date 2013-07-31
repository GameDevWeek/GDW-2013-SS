package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.world.enemies.AbstractEnemy;

public class EnemyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.isSensor() ^ b.isSensor()) {
            return;
        }

        if (a.isSensor()) {
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
            if (objectA.getOwner() instanceof AbstractEnemy) {
                AbstractEnemy enemy = (AbstractEnemy) objectA.owner;
                
            }

        } else {
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
            if (objectB.getOwner() instanceof AbstractEnemy) {
                AbstractEnemy enemy = (AbstractEnemy) objectB.owner;
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
