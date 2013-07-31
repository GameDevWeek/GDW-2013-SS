package de.fhtrier.gdw.ss2013.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * Text & image button for a menu page
 * 
 * @author Lusito
 * @todo file paths need to be adapted when resource loader is ready.
 */
public class Slider extends Widget {
	/** An image for the thumb */
	public Image thumbImage;
	/** An update listener */
	public IUpdateListener listener;
	/** The percent value position of the thumb (0.0f-1.0f) */
	public float value = 0.0f;
	
	public boolean pressed;
	public boolean horizontal;
	
	private Slider(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public void init(GameContainer container) throws SlickException {
	}

	public void render(Graphics g) {
		if(thumbImage != null) {
			if(horizontal)
				thumbImage.draw(rect.getX() + value*(rect.getWidth() - thumbImage.getWidth()), rect.getY());
			else
				thumbImage.draw(rect.getX(), rect.getY() + value*(rect.getHeight() - thumbImage.getHeight()));
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if(pressed) {
			onMove(newx, newy);
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(pressed) {
			onMove(newx, newy);
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if(pressed) {
			onMove(x, y);
			pressed = false;
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if(contains(x,y)) {
			onMove(x, y);
			pressed = true;
		}
	}
	
	private void onMove(int mouseX, int mouseY) {
		// update value
		if(horizontal)
			value = Math.max(0.0f, Math.min(1.0f, (mouseX - rect.getX() -  thumbImage.getWidth()/2) / (float)(rect.getWidth() - thumbImage.getWidth())));
		else
			value = Math.max(0.0f, Math.min(1.0f, (mouseY - rect.getY() -  thumbImage.getHeight()/2) / (float)(rect.getHeight() - thumbImage.getHeight())));
		if(listener != null) {
			listener.onUpdate(new Float(value));
		}
	}
	
	/**
	 * Change the value (0.0f-1.0f) of the slider
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Slider value(float value) {
		this.value = Math.max(0, Math.min(value, 1.0f));
		if(listener != null)
			listener.onUpdate(new Float(this.value));
		return this;
	}

	public float getValue() {
		return value;
	}
	
	/**
	 * Change the position of this button
	 * 
	 * @param x the offset from left in pixels
	 * @param y the offset from top in pixels
	 * @return this
	 */
	public Slider position(float x, float y) {
		rect.setLocation(x, y);
		return this;
	}

	/**
	 * Change the size of this button
	 * 
	 * @param width in pixels
	 * @param width in pixels
	 * @return this
	 */
	public Slider size(float width, float height) {
		rect.setSize(width, height);
		return this;
	}


	/**
	 * Change the thumb image
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Slider thumbImage(Image value) {
		thumbImage = value;
		return this;
	}

	/**
	 * Change the update listener for when the slider changes
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Slider update(IUpdateListener value) {
		listener = value;
		return this;
	}
	
	/**
	 * Clone this button
	 * 
	 * @return A new Slider with the same values as this
	 */
	public Slider clone() {
		Slider b = Slider.create(value, horizontal, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

		b.thumbImage = thumbImage;
		
		return b;
	}


	/**
	 * Create a new button
	 * 
	 * @param text the text to display
	 * @param x offset from left in pixels
	 * @param y offset from top in pixels
	 * @param width in pixels
	 * @param height in pixels
	 * @return the new button
	 */
	public static Slider create(float value, boolean horizontal, float x, float y, float width, float height) {
		Slider slider = new Slider(horizontal);
		slider.value(value);
		slider.position(x,y);
		slider.size(width, height);
		return slider;
	}
}
