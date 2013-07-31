package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;

/**
 * Box class
 * 
 * @author Kevin, Georg
 * 
 */
public class Box extends Entity {

	public void onCollision(Entity e) {
		if (e instanceof Astronaut) {
		}
	}
}
