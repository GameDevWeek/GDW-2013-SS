package de.fhtrier.gdw.ss2013.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;



/**
 * Text label for a menu page
 * 
 * @author Lusito
 * @todo file paths need to be adapted when resource loader is ready.
 */
public class Label extends Widget {
	/** The font to write the message with */
	private Font font;
	/** Text to display */
	public String text = "";
	public Color color;
	public Image image;
	public Align align = Align.LEFT;
	public float rotation;
	public float angle;
	private long lastRenderTime;
	private PathMover follow;
	private PathMover charEater;
	private long lastEat;
	private boolean useTextSize;

	private Label() {
	}

	public void init(GameContainer container) throws SlickException {
		if(font == null)
			font = AssetLoader.getInstance().getFont("verdana_46");
		if(color == null)
			color = Color.white;
	}

	public void render(Graphics g) {
		long currentTime = System.currentTimeMillis();
		if(lastRenderTime == 0)
			lastRenderTime = currentTime;
		long delta = currentTime - lastRenderTime;
		lastRenderTime = currentTime;
		
		float finalAngle = 0;
		// rotate around the center of the image
		if(rotation != 0) {
			angle +=  rotation * delta / 1000.0f;
			finalAngle = angle;
		}
		
		if(follow != null) {
			follow.update((int)delta);
			Vector2f pos = follow.getPosition();
			rect.setLocation(Math.round(pos.x - rect.getWidth()/2), Math.round(pos.y - rect.getHeight()/2));
			finalAngle += follow.getAngle();
		}
		
		if(charEater != null && !text.isEmpty()) {
			if(currentTime - lastEat > 200 ) {
				Vector2f cp = charEater.getPosition();
				if(rect.intersects(new Circle(cp.x, cp.y, 50))) {
					lastEat = currentTime;
					switch(align) {
					case LEFT:
						text = text.substring(0, text.length()-1);
						break;
					case RIGHT: 
						text = text.substring(1);
						break;
					case CENTER: 
						break;
					}
					rect.setWidth(font.getWidth(text));
				}
			}
		}
		
		g.pushTransform();
		// move to the position where it should be drawn
		g.translate(rect.getX(), rect.getY());
		// rotate around the center of the image
		if(finalAngle != 0)
			g.rotate(rect.getWidth()/2, rect.getHeight()/2, finalAngle);

		if(!text.isEmpty()) {
			g.setFont(font);
			float w = font.getWidth(text);
			float h = font.getLineHeight();

			if(useTextSize) {
				rect.setWidth(w);
				rect.setHeight(h);
			}
			
			if(color != null) {
				g.setColor(color);
				switch(align) {
				case LEFT:
					g.drawString(text, 0, Math.round(rect.getHeight()/2 - h/2));
					break;
				case RIGHT: 
					g.drawString(text, Math.round(rect.getWidth() - w), Math.round(rect.getHeight()/2 - h/2));
					break;
				case CENTER: 
					g.drawString(text, Math.round(rect.getWidth()/2 - w/2), Math.round(rect.getHeight()/2 - h/2));
					break;
				}
			}
		}
		if(image != null) {
			image.draw(0, 0, rect.getWidth(), rect.getHeight());
		}
		
		g.popTransform();
	}

	/**
	 * Change the text to display
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label text(String value) {
		text = value;
		return this;
	}

	/**
	 * Change the position of this label
	 * 
	 * @param x the offset from left in pixels
	 * @param y the offset from top in pixels
	 * @return this
	 */
	public Label position(float x, float y) {
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
	public Label size(float width, float height) {
		rect.setSize(width, height);
		return this;
	}
	
	/**
	 * Change the size depending on text size (happens on update)
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label useTextSize(boolean value) {
		useTextSize = value;
		return this;
	}

	/**
	 * Change the font of the text
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label font(Font value) {
		font = value;
		return this;
	}

	/**
	 * Change the align of the text
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label align(Align value) {
		align = value;
		return this;
	}

	/**
	 * Change the text color
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label color(Color value) {
		color = value;
		return this;
	}

	/**
	 * Change the image
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label image(Image value) {
		image = value;
		return this;
	}

	/**
	 * Change the rotation
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label rotation(float value) {
		rotation = value;
		return this;
	}

	/**
	 * Change the angle
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label angle(float value) {
		angle = value;
		return this;
	}

	/**
	 * Follow a path mover
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label follow(PathMover value) {
		follow = value;
		return this;
	}

	/**
	 * Follow a path mover
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label charEater(PathMover value) {
		charEater = value;
		return this;
	}

	/**
	 * Set visibility
	 * 
	 * @param value the new value
	 * @return this
	 */
	public Label visible(boolean value) {
		setVisible(value);
		return this;
	}
	

	/**
	 * Clone this label
	 * 
	 * @return A new Label with the same values as this
	 */
	public Label clone() {
		Label b = Label.create(text, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

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
	public static Label create(String text, float x, float y, float width, float height) {
		Label label = new Label();
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
		Label b = Label.create("Hello", x, y, w, h)
			.font(font)
			.color(Color.gray)
			.image(null);
		Label c = b.clone()
			.text("World")
			.position(x,y)
			.size(w,h);
	}
}
