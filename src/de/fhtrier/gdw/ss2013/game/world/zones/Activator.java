/**
 * DeadZone class
 *
 * @author Justin, Sandra
 *
 *
 */
package de.fhtrier.gdw.ss2013.game.world.zones;

import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

public class Activator extends AbstractZone {

    public Activator() {
        super();
    }
    
    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Interactable) {
            ((Interactable) other).activate();
        }
    }

    @Override
    public void endContact(Contact object) {
    }
}
