/**
 * DeadZone class
 *
 * @author Justin, Sandra
 *
 *
 */
package de.fhtrier.gdw.ss2013.game.world.zones;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;

public class DeadZone extends AbstractZone {
	
	private boolean removeBox;
	private boolean init = false;

    public DeadZone() {
        super();
    	removeBox = true;
    }
    
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
    	if (!init && getProperties() != null && getProperties().getProperty("removeBox") != null) {
    		if (getProperties().getProperty("removeBox").equals("false")) {
    			removeBox = false;
    		}
    	}
		init = true;
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

	public boolean isRemoveBox() {
		return removeBox;
	}
}
