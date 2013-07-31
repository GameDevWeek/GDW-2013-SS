package de.fhtrier.gdw.ss2013.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;


/**
 * Text & image button for a menu page
 * 
 * @author Lusito
 * @todo file paths need to be adapted when resource loader is ready.
 */
public class ToggleButton extends Widget {
	/** The font to write the message with */
	private Font font;
	/** A text for each state */
	public String texts[];
	public Color color;
	public Color hoverColor;
	/** An action listener */
	public IUpdateListener listener;
	public int state;
	public boolean hover;
	public Align align = Align.CENTER;

	private boolean useTextSize;
	
	private ToggleButton() {
	}

	public void init(GameContainer container) throws SlickException {
		if (font == null)
			font = AssetLoader.getInstance().getFont("verdana_46");
	}

	public void render(Graphics g) {
		g.setFont(font);
		float w = font.getWidth(texts[state]);
		float h = font.getLineHeight();

		if(useTextSize) {
			rect.setWidth(w);
			rect.setHeight(h);
		}
		
		if (hover && hoverColor != null)
			g.setColor(hoverColor);
		else
			g.setColor(color);
		
		switch (align) {
		case LEFT:
			g.drawString(texts[state], rect.getX(), rect.getCenterY() - h / 2);
			break;
		case RIGHT:
			g.drawString(texts[state], rect.getMaxX() - w, rect.getCenterY() - h / 2);
			break;
		case CENTER:
			g.drawString(texts[state], rect.getCenterX() - w / 2,
					rect.getCenterY() - h / 2);
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_ENTER) {
			toggle(1);
		}
	}

	public void toggle(int dir) {
		state(state + dir);
	}

	public ToggleButton state(int value) {
		state = value;
		if (state >= texts.length)
			state = 0;
		else if (state < 0)
			state = texts.length - 1;
		if (listener != null)
			listener.onUpdate(state);
		
		return this;
	}
	
	public int getState() {
		return state;
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		hover = contains(newx, newy);
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if (contains(x, y)) {
			toggle(button == 0 ? 1 : -1);
		}
	}

	/**
	 * Change the position of this button
	 * 
	 * @param x
	 *            the offset from left in pixels
	 * @param y
	 *            the offset from top in pixels
	 * @return this
	 */
	public ToggleButton position(float x, float y) {
		rect.setLocation(x, y);
		return this;
	}

	/**
	 * Change the size of this button
	 * 
	 * @param width
	 *            in pixels
	 * @param width
	 *            in pixels
	 * @return this
	 */
	public ToggleButton size(float width, float height) {
		rect.setSize(width, height);
		return this;
	}
	
	/**
	 * Change the size depending on text size (happens on update)
	 * 
	 * @param value the new value
	 * @return this
	 */
	public ToggleButton useTextSize(boolean value) {
		useTextSize = value;
		return this;
	}

	/**
	 * Change the font of the text
	 * 
	 * @param value
	 *            the new value
	 * @return this
	 */
	public ToggleButton font(Font value) {
		font = value;
		return this;
	}

	/**
	 * Change the align of the text
	 * 
	 * @param value
	 *            the new value
	 * @return this
	 */
	public ToggleButton align(Align value) {
		align = value;
		return this;
	}

	/**
	 * Change the texts
	 * 
	 * @param value
	 *            the new value
	 * @return this
	 */
	public ToggleButton texts(String[] value) {
		texts = value;
		state(state);
		return this;
	}

	/**
	 * Change the text color
	 * 
	 * @param value
	 *            the new value
	 * @return this
	 */
	public ToggleButton color(Color value) {
		color = value;
		return this;
	}

	/**
	 * Change the text color for State.HOVER
	 * 
	 * @param value
	 *            the new value
	 * @return this
	 */
	public ToggleButton hoverColor(Color value) {
		hoverColor = value;
		return this;
	}

	/**
	 * Change the update listener
	 * 
	 * @param value
	 *            the new value
	 * @return this
	 */
	public ToggleButton update(IUpdateListener value) {
		listener = value;
		return this;
	}

	/**
	 * Clone this button
	 * 
	 * @return A new ToggleButton with the same values as this
	 */
	// public ToggleButton clone() {
	// ToggleButton b = ToggleButton.create(text, rect.getX(), rect.getY(),
	// rect.getWidth(), rect.getHeight());
	//
	// b.font = font;
	//
	// int numStates = State.values().length;
	// for(int i=0; i<numStates; i++) {
	// b.colors[i] = colors[i];
	// b.images[i] = images[i];
	// }
	//
	// return b;
	// }

	/**
	 * Create a new button
	 * 
	 * @param text
	 *            the text to display
	 * @param x
	 *            offset from left in pixels
	 * @param y
	 *            offset from top in pixels
	 * @param width
	 *            in pixels
	 * @param height
	 *            in pixels
	 * @return the new button
	 */
	public static ToggleButton create(String texts[], Color color, float x,
			float y, float width, float height) {
		ToggleButton button = new ToggleButton();
		button.texts(texts);
		button.color(color);
		button.position(x, y);
		button.size(width, height);
		return button;
	}
}
