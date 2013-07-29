/**
 * DeadZone class
 * @author Justin, Sandra 
 * 
 * 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;

public class DeadZone extends Entity {

	public DeadZone(Vector2f position) {
		super(position);
	}

	public DeadZone() {

	}

	public void touched(Astronaut astro) {

		astro.setOxygen(0);

	}

}
