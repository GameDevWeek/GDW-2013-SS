/**
 * OxygenBubble class
 * @author Justin, Sandra
 * 
 * erzeugt Sauerstoffblasen und soll Sauerstoffvorrat erhöhen
 * 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.physics.ICollidable;


public class OxygenBubble extends Entity implements ICollidable{

	private float oxygenLevel;

	// Standard-Konstruktor
	public OxygenBubble(Vector2f position) {
		super(position);
		// Default
		oxygenLevel = 0;
	}

	public OxygenBubble(Vector2f position, float oxygenLevel) {
		super(position.copy());
		this.oxygenLevel = oxygenLevel;
	}
	
	@Override
    public void onCollision(Entity e)
    {
        if(e instanceof Astronaut)
        {
            if (((Astronaut)e).getOxygen() + oxygenLevel < ((Astronaut)e).getMaxOxygen())
            {
                ((Astronaut) e).setOxygen(((Astronaut)e).getOxygen()+oxygenLevel);
            }
            else{
                ((Astronaut)e).setOxygen(((Astronaut)e).getMaxOxygen());
            }
        }
    }
	
	public float getOxygenLevel() {
		return oxygenLevel;
	}

	public void setOxygenLevel(float oxygenLevel) {
		this.oxygenLevel = oxygenLevel;
	}

	@Override
	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}

}