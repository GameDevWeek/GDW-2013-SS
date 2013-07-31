/**
 * DeadZone class
 * @author Justin, Sandra 
 * 
 * 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

public class DeadZone extends Entity implements ICollidable{

	public DeadZone() {

	}

	public void onCollision(Entity e) {

	    if(e instanceof Astronaut)
	    {
	        ((Astronaut)e).setOxygen(0);
	    }
	}

	@Override
	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}

}
