package de.fhtrier.gdw.ss2013.game.world.zones;

import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;

public class WinningZone extends EntityCollidable {
	
	public WinningZone() {
		super();
	}

	@Override
	public void beginContact(Contact contact) {
		Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut) {
            if (((Astronaut) other).isCarryAlien()) {
            	MainGame.changeState(MainGame.WINSTATE);
            }
        }
	}

	@Override
	public void endContact(Contact object) {
	}

}
