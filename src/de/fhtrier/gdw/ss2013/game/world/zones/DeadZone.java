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
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;

public class DeadZone extends AbstractZone {

    public DeadZone() {
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut) {
            ((Astronaut) other).setOxygen(0);
        }
        if (other instanceof Alien) {
            World.getInstance().getAstronaut().teleportAlienback();
        }
    }

    @Override
    public void endContact(Contact object) {
    }
}
