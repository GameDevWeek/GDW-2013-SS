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
public class Button extends Switch implements ICollidable {
	private boolean collision, lastFrameCollision;

	public Button() {
	    // super(AssetLoader.getInstance().getImage("button")); // Image does not exist yet
		collision = lastFrameCollision = false;
	}
	
	@Override
	public void onCollision(Entity e) {
		if (e instanceof Astronaut || e instanceof Box) {
			this.setSwitch(true);
			collision = true;
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (lastFrameCollision) {
			this.setSwitch(true);
		} else {
			this.setSwitch(false);
		}
		lastFrameCollision = collision;
		collision = false;
	}

	@Override
	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}
}
