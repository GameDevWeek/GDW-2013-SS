package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.game.world.enemies.AbstractEnemy;

public class EnemyContactListener implements ICollisionListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.isSensor() ^ b.isSensor()) {
            return;
        }
        
        AbstractEnemy damageDealer = null;
        Player damageTaker = null;
        if (a.isSensor()) {
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
            
            if(objectA == null || objectB == null)
                return;
            
            if (objectB.getOwner() instanceof AbstractEnemy && objectA.getOwner() instanceof Player) {
                damageTaker = (Player) objectA.owner;
                damageDealer = (AbstractEnemy) objectB.owner;
            }

        } else { // b is sensor
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
            if(objectA == null || objectB == null)
                return;
            if (objectA.getOwner() instanceof AbstractEnemy && objectB.getOwner() instanceof Player) {
                damageDealer = (AbstractEnemy) objectA.owner;
                damageTaker = (Player) objectB.owner;
            }
        }
        if(damageDealer == null || damageTaker == null)
            return;
        // is above TODO(check for astronaut later..)
        if(damageTaker.getPosition().y < damageDealer.getPosition().y) {
            System.out.println(damageDealer+" hit by "+damageTaker);
        }
        else {
            damageTaker.die();
        }
        
        

    }

    @Override
    public void endContact(Contact contact) {

    }
}
