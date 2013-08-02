package de.fhtrier.gdw.ss2013.game.world.zones;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;

public class WinningZone extends EntityCollidable {
	
	private String newLevel;
	private boolean init = false;
	
	public WinningZone() {
		super();
	}
	
	@Override
	public void initialize() {
		super.initialize();
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		
		if (!init) {
			if (getProperties().getProperty("target") != null) {
				newLevel = getProperties().getProperty("target");
			}
			init = true;
		}
	}

	@Override
	public void beginContact(Contact contact) {
		Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut) {
            if (((Astronaut) other).isCarryAlien()) {
            	handleWin();
            }
        }
	}
	
	private void handleWin() {
		if (newLevel == null) {
			MainGame.changeState(MainGame.MAINMENUSTATE);
		}
		else {
			World.getInstance().setLevelName(newLevel);
			World.getInstance().shallBeReseted(true);
		}
	}

	@Override
	public void endContact(Contact object) {
	}

}
