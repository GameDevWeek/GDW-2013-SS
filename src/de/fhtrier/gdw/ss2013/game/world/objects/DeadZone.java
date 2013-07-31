/**
 * DeadZone class
 *
 * @author Justin, Sandra
 *
 *
 */
package de.fhtrier.gdw.ss2013.game.world.objects;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import org.jbox2d.dynamics.contacts.Contact;

public class DeadZone extends EntityCollidable {

    public DeadZone() {
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut) {
            ((Astronaut) other).setOxygen(0);
        }
    }

    @Override
    public void endContact(Contact object) {
    }
}
