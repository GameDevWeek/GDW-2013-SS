package de.fhtrier.gdw.ss2013.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;



/**
 * Text field input for a menu page
 * 
 * @author Lusito
 * @todo file paths need to be adapted when resource loader is ready.
 */
public class TextField extends Widget {
	/** The font to write the message with */
	private Font font;
	/** Text to display */
	public String text = "";
	/** Text to display grayed out when field is empty */
	public String hint = "";
	public Color color;
	public Color hintColor;
	public Image image;
	public Image focusImage;
	/** An update listener */
	public IUpdateListener listener;
	private static int CURSOR_BLINK_TIME = 500;
	private long lastCursorTime;
	private boolean cursorVisible;
	private int cursorPos;
	private float xOffset;

	private TextField() {
	}

    @Override
	public void init(GameContainer container) throws SlickException {
		if(font == null)
			font = AssetLoader.getInstance().getFont("verdana_46");
		if(color == null)
			color = Color.white;
	}

    @Override
	public void render(Graphics g) {
		g.setFont(font);
		Image img = image;
		if(focus && focusImage != null)
			img = focusImage;
		if(img != null) {
			img.draw(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		}

		Rectangle oldClip = g.getWorldClip();
		g.setWorldClip(rect);
		if(color != null) {
			float h = font.getLineHeight();
			if(text.isEmpty()) {
				g.setColor(hintColor);
				g.drawString(hint, rect.getX(), rect.getCenterY() - h/2);
				g.setColor(Color.darkGray);
				if(focus)
					showCursor(g, rect.getX() + 2, rect.getY() + 5, rect.getY() + h + 5);
			} else {
				g.setColor(color);
				g.drawString(text, rect.getX() + xOffset, rect.getCenterY() - h/2);

				if(focus) {
					String cs = text.substring(0, cursorPos);
					showCursor(g, rect.getX() + font.getWidth(cs) + 2 + xOffset, rect.getY() + 5, rect.getY() + h + 5);
				}
				
			}
		}
		g.setWorldClip(oldClip);
	}
	
	private void showCursor(Graphics g, float x, float y1, float y2) {
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastCursorTime > CURSOR_BLINK_TIME) {
			lastCursorTime = currentTime;
			cursorVisible = !cursorVisible;
		}
		if(cursorVisible) {
			g.setLineWidth(2);
			g.drawLine(x, y1, x, y2);
		}
	}
	
    @Override
	public void keyPressed(int key, char c, boolean repeated) {
		if (!this.focus)
		    return;
		
	    int oldCursorPos = cursorPos;
		if(key == Input.KEY_BACK) {
			if(!text.isEmpty() && cursorPos > 0) {
				if(cursorPos == text.length())
					text = text.substring(0, cursorPos-1);
				else
					text = text.substring(0, cursorPos-1) + text.substring(cursorPos);
				cursorPos--;
			}
		} else if(key == Input.KEY_DELETE) {
			if(!text.isEmpty() && cursorPos < text.length()) {
				if(cursorPos == 0)
					text = text.substring(1);
				else
					text = text.substring(0, cursorPos) + text.substring(cursorPos+1);
			}
		} else if(key == Input.KEY_RIGHT) {
			if(cursorPos < text.length())
				cursorPos++;
		} else if(key == Input.KEY_LEFT) {
			if(cursorPos > 0)
				cursorPos--;
		} else if(key == Input.KEY_END) {
			cursorPos = text.length();
		} else if(key == Input.KEY_HOME) {
			cursorPos = 0;
		} else if(key == Input.KEY_ENTER) {
            this.setFocus(false);
		} else if (key ==  Input.KEY_TAB) {
		    // Ignore Tab
		} else if(c != '\0') {
			if(cursorPos == text.length())
				text += c;
			else
				text = text.substring(0, cursorPos) + c + text.substring(cursorPos);
			cursorPos++;
		}
		
		// force cursor visible if cursor position changed
		if(cursorPos != oldCursorPos) {
			lastCursorTime = System.currentTimeMillis();
			cursorVisible = true;
		}
		
		recalculateOffset();
	}
	
	public void recalculateOffset() {
		float totalWidth = font.getWidth(text);
		if(totalWidth < rect.getWidth())
			xOffset = 0;
		else {
			String cs = text.substring(0, cursorPos);
			float cursorX = font.getWidth(cs) + 2 + xOffset;
			if(cursorX < 0)
				xOffset -= cursorX;
			else if(cursorX > rect.getWidth())
				xOffset -= cursorX - rect.getWidth();
			if(totalWidth + xOffset < rect.getWidth())
				xOffset = rect.getWidth() - totalWidth - 5;
		}
	}

	/**
	 * Change the text to display
	 * 
	 * @param value the new value
	 * @return this
	 */
	public TextField text(String value) {
		text = value;
		cursorPos = text.length();
		return this;
	}
	
	public String getText() {
		return text;
	}

	/**
	 * Change the text to display grayed out when field is empty
	 * 
	 * @param value the new value
	 * @return this
	 */
	public TextField hint(String value) {
		hint = value;
		return this;
	}

	/**
	 * Change the position of this label
	 * 
	 * @param x the offset from left in pixels
	 * @param y the offset from top in pixels
	 * @return this
	 */
	public TextField position(float x, float y) {
		rect.setLocation(x, y);
		return this;
	}

	/**
	 * Change the size of this label
	 * 
	 * @param width in pixels
	 * @param width in pixels
	 * @return this
	 */
	public TextField size(float width, float height) {
		rect.setSize(width, height);
		return this;
	}

	/**
	 * Change the font of the text
	 * 
	 * @param value the new value
	 * @return this
	 */
	public TextField font(Font value) {
		font = value;
		return this;
	}

	/**
	 * Change the text color
	 * 
	 * @param value the new value
	 * @return this
	 */
	public TextField color(Color value) {
		color = value;
		return this;
	}

	/**
	 * Change the hint text color
	 * 
	 * @param value the new value
	 * @return this
	 */
	public TextField hintColor(Color value) {
		hintColor = value;
		return this;
	}

	/**
	 * Change the image
	 * 
	 * @param value the new value
	 * @return this
	 */
	public TextField image(Image value) {
		image = value;
		return this;
	}

	/**
	 * Change the focus image
	 * 
	 * @param value the new value
	 * @return this
	 */
	public TextField focusImage(Image value) {
		focusImage = value;
		return this;
	}

	/**
	 * Change the update listener for when the slider changes
	 * 
	 * @param value the new value
	 * @return this
	 */
	public TextField update(IUpdateListener value) {
		listener = value;
		return this;
	}
	
	/**
	 * Clone this label
	 * 
	 * @return A new TextField with the same values as this
	 */
    @Override
	public TextField clone() {
		TextField b = TextField.create(text, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

		b.font = font;
		b.color =  color;
		
		return b;
	}


	/**
	 * Create a new label
	 * 
	 * @param text the text to display
	 * @param x offset from left in pixels
	 * @param y offset from top in pixels
	 * @param width in pixels
	 * @param height in pixels
	 * @return the new label
	 */
	public static TextField create(String text, float x, float y, float width, float height) {
		TextField label = new TextField();
		label.text(text);
		label.position(x,y);
		label.size(width, height);
		return label;
	}
	
	/**
	 * Shows how to create and init a label
	 */
	@SuppressWarnings("unused")
	private void demo() {
		int x=0,y=0,w=0,h=0;
		TextField b = TextField.create("Hello", x, y, w, h)
			.font(font)
			.color(Color.gray)
			.image(null);
		TextField c = b.clone()
			.text("World")
			.position(x,y)
			.size(w,h);
	}
}
