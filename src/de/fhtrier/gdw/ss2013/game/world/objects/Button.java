package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

/**
 * Switch class
 * 
 * @author Kevin, Georg
 * 
 */
public class Button extends ObjectController implements ICollidable {

	public Button() {
	    // super(AssetLoader.getInstance().getImage("button")); // Image does not exist yet
	}
	
	@Override
	public void onCollision(Entity e) {
//		if (e instanceof Astronaut || e instanceof Box) {
//			collisionState = !collisionState;
//			if(collisionState) {
//			    activate();
//			} else {
//			    deactivate();
//			}
//		}
	}
	
	public void setActivated(boolean active) {
	    if(isActivated != active) {
	        if(active) {
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

	@Override
	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}
}
