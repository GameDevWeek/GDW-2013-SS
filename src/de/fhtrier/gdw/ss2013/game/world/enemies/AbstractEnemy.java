package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.EntityCollidable;

/**
 * Abstract Enemy Class for Enemys and Meteroids
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class AbstractEnemy extends EntityCollidable {

	private float damage;
	private Animation current_ani;
	private Animation leftAnimation, rightAnimation;

	public AbstractEnemy(Animation moveToRightAnimation) {
		this.rightAnimation = moveToRightAnimation;
		leftAnimation = rightAnimation; // FIXME: flip me, baby!
		current_ani = rightAnimation;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float dmg) {
		damage = dmg;
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// Move right
		Vector2f velocity = getVelocity();
		if (velocity != null && velocity.x > 0 && current_ani.equals(rightAnimation)) {
			current_ani = rightAnimation;

		}
		// Move left
		if (velocity != null && velocity.x < 0 && current_ani.equals(leftAnimation)) {
			current_ani = leftAnimation;
		}

		if (current_ani != null) {
			current_ani.draw(this.getPosition().x - (current_ani.getWidth() / 2),
					this.getPosition().y - (current_ani.getHeight() / 2));
		}
	}
}
