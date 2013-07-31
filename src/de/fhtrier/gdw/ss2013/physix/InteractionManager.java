package de.fhtrier.gdw.ss2013.physix;

import java.util.HashSet;

import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.ActivateableByAstronaut;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;

/** 
 * Manager for Entities, which can be activated by the Astronaut by using the action key.
 * Since those Entities can only be activated, while they collide with the Astronaut,
 * this keeps a list of those colliding and activatable Entities.
 * 
 * @author BreakingTheHobbit
 */
public class InteractionManager implements ICollisionListener {

    /** set of activateable Entities */
    HashSet<ActivateableByAstronaut> interactables;
    
    public InteractionManager() {
        interactables = new HashSet<ActivateableByAstronaut>();
    }

    @Override
    public void beginContact(Contact contact) {
        PhysixObject objectA = (PhysixObject) contact.getFixtureA().getBody().getUserData();
        PhysixObject objectB = (PhysixObject) contact.getFixtureB().getBody().getUserData();
        
        //since not every Body seems to have its userData set, return, if it is null
        if(objectA == null || objectB == null) {
            return;
        }
        
        Entity entityA = objectA.getOwner();
        Entity entityB = objectB.getOwner();
        
        // check, if the collided objects include exactly one Astronaut
        if (!(entityA instanceof Astronaut ^ entityB instanceof Astronaut)) {
            return;
        }
        
        // if the colliding entity is activateable by the Astronaut, add it to the set
        if (entityA instanceof Astronaut) { // objectA is the Astronaut
            if(entityB instanceof ActivateableByAstronaut) {
                interactables.add((ActivateableByAstronaut) entityB);
            }
        } else { // objectB is the Astronaut
            if(entityA instanceof ActivateableByAstronaut) {
                interactables.add((ActivateableByAstronaut) entityA);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        PhysixObject objectA = (PhysixObject) contact.getFixtureA().getBody().getUserData();
        PhysixObject objectB = (PhysixObject) contact.getFixtureB().getBody().getUserData();
        
        //since not every Body seems to have its userData set, return, if it is null
        if(objectA == null || objectB == null) {
            return;
        }
        
        Entity entityA = objectA.getOwner();
        Entity entityB = objectB.getOwner();
        
        // check, if the collided objects include exactly one Astronaut
        if (!(entityA instanceof Astronaut ^ entityB instanceof Astronaut)) {
            return;
        }
        
        // if the not anymore colliding entity is activateable by the Astronaut, remove it from the set
        if (entityA instanceof Astronaut) { // objectA is the Astronaut
            if(entityB instanceof ActivateableByAstronaut) {
                interactables.remove((ActivateableByAstronaut) entityB);
            }
        } else { // objectB is the Astronaut
            if(entityA instanceof ActivateableByAstronaut) {
                interactables.remove((ActivateableByAstronaut) entityA);
            }
        }
    }
    
    /** activate all listed Entities, when Astronaut performs action() */
    public void activateAll() {
        for(ActivateableByAstronaut a: interactables) {
            a.activate();
        }
    }
}
