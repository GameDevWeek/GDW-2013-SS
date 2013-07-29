package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;

/**
 * Switch Class
 * 
 * @author Kevin, Georg
 * 
 */
public class Switch extends Entity {

	boolean activated = false;

	public Switch(Vector2f pos) {
		super(pos.copy());
	}

	public Switch() {
		this(new Vector2f());
	}

	public boolean isActivated() {
		return activated;
	}

	public void switchActivated() {
		this.activated = !this.activated;
	}

	public void setActivated(boolean a) {
		this.activated = a;
	}
}
