package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.physix.ICollisionListener;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;
import org.jbox2d.dynamics.contacts.Contact;

/**
 * Button Entity<br>
 * <br>
 * Behavior:<br>
 * - gets activated when collision with first {@link Player} or {@link Box} starts<br>
 * - gets deactivated when all collisions with Players and Boxes stop<br>
 * - de/activates all connected Entities when getting de/activated
 * 
 * @author Kevin, Georg<br>
 * Editor: BreakingTheHobbit, Mr.X??
 * 
 * @see ObjectController
 * @see ICollisionListener
 */
public class Button extends ObjectController implements ICollisionListener {

    protected int pressContacts;

    public Button() {
        img = AssetLoader.getInstance().getImage("button_unpressed");
        // private Image pressedImg = AssetLoader.getInstance().getImage("button_pressed");
    }

    public void setActivated(boolean active) {
        if (isActivated != active) {
            if (active) {
                activate();
            } else {
                deactivate();
            }
            isActivated = active;
        }
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    }

    public boolean isPressed() {
        return pressContacts > 0;
    }

    @Override
    public void setPhysicsObject(PhysixObject physicsObject) {
        super.setPhysicsObject(physicsObject);
        this.physicsObject.addCollisionListener(this);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Entity entityA = ((PhysixObject) a.getBody().getUserData()).getOwner();
        Entity entityB = ((PhysixObject) b.getBody().getUserData()).getOwner();

        //activate only if nothing relevant touched the button before
        boolean wasInactive = pressContacts == 0;

        //save which entity is the button and
        //activate Button after! adding the other entity to the set of colliding entities
        //to avoid side effects
        if (entityA instanceof Button) { // objectA is the Button
            if (entityB instanceof Player || entityB instanceof Box) {
                pressContacts++;
            }
        } else { // objectB is the Button
            if (entityA instanceof Player || entityA instanceof Box) {
                pressContacts++;
            }
        }

        //activate the button, but only if nothing touched the button before, but does now
        if (wasInactive && pressContacts > 0) {
            activate();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Entity entityA = ((PhysixObject) a.getBody().getUserData()).getOwner();
        Entity entityB = ((PhysixObject) b.getBody().getUserData()).getOwner();
        
        //deactivate only if something relevant touched the button before
        boolean wasActive = pressContacts > 0;
        
        //save which entity is the button and
        //deactivate Button after! removing the other entity out of the set of colliding entities
        //to avoid side effects
        if (entityA instanceof Button) { // objectA is the Button
            if(entityB instanceof Player || entityB instanceof Box) {
                pressContacts--;
            }
        } else { // objectB is the Button
            if(entityA instanceof Player || entityA instanceof Box) {
                pressContacts--;
            }
        }
        
        //activate the button, but only if nothing touched the button before, but does now
        if(wasActive && pressContacts == 0) {
            deactivate();
        }
    }
}
