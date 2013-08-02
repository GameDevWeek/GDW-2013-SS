package de.fhtrier.gdw.ss2013.physix;

import java.util.HashSet;

import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.ActivatableByAstronaut;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;

/** 
 * Manager for Entities, which can be activated by the Astronaut by using the action key.
 * Since those Entities can only be activated, while they collide with the Astronaut,
 * this keeps a list of those colliding and activatable Entities.
 * 
 * @author BreakingTheHobbit
 * 
 * @see ActivatableByAstronaut
 * @see Astronaut
 */
public class InteractionManager implements ICollisionListener {

    /** set of activatable Entities */
    HashSet<ActivatableByAstronaut> interactables;
    
    public InteractionManager() {
        interactables = new HashSet<>();
    }

    /**
     * if Astronaut starts colliding with an Entity, which can be activated by the Astronaut,<br>
     * add that Entity to the set of activatable Entities in range<br>
     */
    @Override
    public void beginContact(Contact contact) {
        PhysixShape objectA = (PhysixShape) contact.getFixtureA().getBody().getUserData();
        PhysixShape objectB = (PhysixShape) contact.getFixtureB().getBody().getUserData();
        
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
        
        // if the colliding entity is activatable by the Astronaut, add it to the set
        if (entityA instanceof Astronaut) { // objectA is the Astronaut
            if(entityB instanceof ActivatableByAstronaut) {
                interactables.add((ActivatableByAstronaut) entityB);
            }
        } else { // objectB is the Astronaut
            if(entityA instanceof ActivatableByAstronaut) {
                interactables.add((ActivatableByAstronaut) entityA);
            }
        }
    }

    /**
     * if Astronaut stops colliding with an Entity, which can be activated by the Astronaut,<br>
     * remove that Entity from the set of activatable Entities in range<br>
     */
    @Override
    public void endContact(Contact contact) {
        PhysixShape objectA = (PhysixShape) contact.getFixtureA().getBody().getUserData();
        PhysixShape objectB = (PhysixShape) contact.getFixtureB().getBody().getUserData();
        
        //since not every Body seems to have its userData set, return, if it is null
        if(objectA == null || objectB == null) {
            return;
        }
        
        Entity entityA = objectA.getOwner();
        Entity entityB = objectB.getOwner();
        
        // check, if the not anymore colliding objects include exactly one Astronaut
        if (!(entityA instanceof Astronaut ^ entityB instanceof Astronaut)) {
            return;
        }
        
        // if the not anymore colliding entity is activatable by the Astronaut, remove it from the set
        if (entityA instanceof Astronaut) { // objectA is the Astronaut
            if(entityB instanceof ActivatableByAstronaut) {
                interactables.remove((ActivatableByAstronaut) entityB);
            }
        } else { // objectB is the Astronaut
            if(entityA instanceof ActivatableByAstronaut) {
                interactables.remove((ActivatableByAstronaut) entityA);
            }
        }
    }
    
    /**
     * - activate all Entities in range<br>
     * - called when Astronaut performs action()
     * */
    public void activateAll() {
        for(ActivatableByAstronaut a: interactables) {
            a.activate();
        }
    }
}
