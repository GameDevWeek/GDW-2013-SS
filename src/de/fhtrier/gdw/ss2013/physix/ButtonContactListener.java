/**
 * 
 * @author Thomas M.
 * 
 * Listener, which activates and deactivates Buttons
 *
 */

package de.fhtrier.gdw.ss2013.physix;

import java.util.HashSet;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.game.world.objects.Box;
import de.fhtrier.gdw.ss2013.game.world.objects.Button;

public class ButtonContactListener implements ICollisionListener {

    HashSet<Entity> contactCount;
    
    public ButtonContactListener() {
        contactCount = new HashSet<Entity>();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Entity entityA = ((PhysixObject) a.getBody().getUserData()).getOwner();
        Entity entityB = ((PhysixObject) b.getBody().getUserData()).getOwner();
        // check, if the collided objects include exactly one Button
        if (!(entityA instanceof Button ^ entityB instanceof Button)) {
            return;
        }
        
        //activate only if nothing relevant touched the button before
        boolean wasInactive = contactCount.isEmpty();
        
        //save which entity is the button and
        //activate Button after! adding the other entity to the set of colliding entities
        //to avoid side effects
        boolean isEntityAButton = false;
        if (entityA instanceof Button) { // objectA is the Button
            if(entityB instanceof Player || entityB instanceof Box) {
                contactCount.add(entityB);
            }
            isEntityAButton = true;
        } else { // objectB is the Button
            if(entityA instanceof Player || entityA instanceof Box) {
                contactCount.add(entityA);
            }
        }
        //activate the button, but only if nothing touched the button before, but does now
        if(wasInactive && !contactCount.isEmpty()) {
            if(isEntityAButton) {
                ((Button) entityA).activate();
            } else {
                ((Button) entityB).activate();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Entity entityA = ((PhysixObject) a.getBody().getUserData()).getOwner();
        Entity entityB = ((PhysixObject) b.getBody().getUserData()).getOwner();
        // check, if the collided objects include exactly one Button
        if (!(entityA instanceof Button ^ entityB instanceof Button)) {
            return;
        }
        
        //deactivate only if something relevant touched the button before
        boolean wasActive = !contactCount.isEmpty();
        
        //save which entity is the button and
        //deactivate Button after! removing the other entity out of the set of colliding entities
        //to avoid side effects
        boolean isEntityAButton = false;
        if (entityA instanceof Button) { // objectA is the Button
            if(entityB instanceof Player || entityB instanceof Box) {
                contactCount.remove(entityB);
            }
            isEntityAButton = true;
        } else { // objectB is the Button
            if(entityA instanceof Player || entityA instanceof Box) {
                contactCount.remove(entityA);
            }
        }
        //activate the button, but only if nothing touched the button before, but does now
        if(wasActive && contactCount.isEmpty()) {
            if(isEntityAButton) {
                ((Button) entityA).deactivate();
            } else {
                ((Button) entityB).deactivate();
            }
        }
    }
}
