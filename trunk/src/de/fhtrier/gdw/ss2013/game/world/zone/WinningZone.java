package de.fhtrier.gdw.ss2013.game.world.zone;

import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Player;

public class WinningZone extends EntityCollidable {
	
	public WinningZone() {
		super();
	}

	@Override
	public void beginContact(Contact contact) {
		if (getOtherEntity(contact) instanceof Player) {
			
		}
	}

	@Override
	public void endContact(Contact object) {
	}

}
