/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;

/**
 * Player class
 */
public abstract class Player extends Entity {

	Animation bewegungs_ani;
	private String zustand = "animtest";
	private Vector2f velocity;

	public Player(Vector2f position) {
		super(position);
		velocity = new Vector2f();
		bewegungs_ani = AssetLoader.getInstance().getAnimation(zustand);

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		bewegungs_ani.draw(position.x - bewegungs_ani.getWidth() / 2,
				position.y - bewegungs_ani.getHeight() / 2);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public String getZustand() {
		return zustand;
	}

	public void setZustand(String zustand) {
		this.zustand = zustand;
	}

}
