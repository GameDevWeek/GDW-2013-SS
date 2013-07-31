package de.fhtrier.gdw.ss2013.menu;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;


/**
 * Text Animated for a menu page
 * 
 * @author Lusito
 * @todo file paths need to be adapted when resource loader is ready.
 */
public class Animated extends Widget {
	/** Animation to display */
	public Animation animation;
	public float rotation;
	public float angle;
	private long lastRenderTime;
	private PathMover follow;

	private Animated() {
	}

	public void init(GameContainer container) throws SlickException {
	}

	public void render(Graphics g) {
		long currentTime = System.currentTimeMillis();
		if(lastRenderTime == 0)
			lastRenderTime = currentTime;
		long delta = currentTime - lastRenderTime;
		lastRenderTime = currentTime;
		
		// rotate around the center of the image
		if(rotation != 0)
			angle +=  rotation * delta / 1000.0f;

		float finalAngle = angle;
		if(follow != null) {
			follow.update((int)delta);
			Vector2f pos = follow.getPosition();
			rect.setLocation(Math.round(pos.x - rect.getWidth()/2), Math.round(pos.y - rect.getHeight()/2));
			finalAngle += follow.getAngle();
		}
		
		g.pushTransform();
		// move to the position where it should be drawn
		g.translate(rect.getX(), rect.getY());
		// rotate around the center of the image
		if(finalAngle != 0)
			g.rotate(rect.getWidth()/2, rect.getHeight()/2, finalAngle);

		animation.draw(0, 0, rect.getWidth(), rect.getHeight());
		
		g.popTransform();
	}

	/**
	 * Change the position of this Animated
	 * 
	 * @param x the offset from left in pixels
	 * @param y the offset from top in pixels
	 * @return this
	 */
	public Animated position(float x, float y) {
		rect.setLocation(x, y);
		return this;
	}

	/**
	 * Change the size of this Animated
	 * 
	 * @param width in pixels
	 * @param width in pixels
	 * @return this
	 */
	public Animated size(float width, float height) {
		rect.setSize(width, height);
		return this;
	}

	/**
	 * Change the animation
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Animated animation(Animation value) {
		animation = value;
		return this;
	}

	/**
	 * Change the rotation
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Animated rotation(float value) {
		rotation = value;
		return this;
	}

	/**
	 * Change the angle
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Animated angle(float value) {
		angle = value;
		return this;
	}

	/**
	 * Follow a path mover
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Animated follow(PathMover value) {
		follow = value;
		return this;
	}

	/**
	 * Clone this Animated
	 * 
	 * @return A new Animated with the same values as this
	 */
	public Animated clone() {
		Animated b = Animated.create(animation, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		return b;
	}


	/**
	 * Create a new Animated
	 * 
	 * @param text the text to display
	 * @param x offset from left in pixels
	 * @param y offset from top in pixels
	 * @param width in pixels
	 * @param height in pixels
	 * @return the new Animated
	 */
	public static Animated create(Animation animation, float x, float y, float width, float height) {
		Animated Animated = new Animated();
		Animated.animation(animation);
		Animated.position(x,y);
		Animated.size(width, height);
		return Animated;
	}
}
