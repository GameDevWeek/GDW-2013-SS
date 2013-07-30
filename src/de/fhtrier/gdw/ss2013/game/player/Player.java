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

	private Animation animation;
	private String zustand = "animtest";
	private Vector2f velocity;

	public Player(Vector2f position) {
		super(position);
		velocity = new Vector2f();
		animation = AssetLoader.getInstance().getAnimation(zustand);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		animation.draw(position.x - animation.getWidth() / 2,
				position.y - animation.getHeight() / 2);
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
