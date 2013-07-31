package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

/**
 * Switch class
 * 
 * @author Kevin, Georg
 * edited by: Thomas M.
 * 
 * Button, which gets activated by collision with the player or a box,
 * 
 * 
 */
public class Button extends ObjectController implements ICollidable {

	public Button() {
        setImage(AssetLoader.getInstance().getImage("button_unpressed"));
        // private Image pressedImg = AssetLoader.getInstance().getImage("button_pressed");
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
