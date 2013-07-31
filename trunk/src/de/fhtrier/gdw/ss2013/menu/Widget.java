package de.fhtrier.gdw.ss2013.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Menu widget
 * 
 * @author Lusito
 */
public abstract class Widget {
	protected boolean focus = false;
	protected boolean visible = true;
	/** The bounding rect */
	protected Rectangle rect = new Rectangle(0, 0, 0, 0);

	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean value) {
		visible = value;
	}
	
	public enum Align {
		LEFT,
		RIGHT,
		CENTER
	}
	
	public void keyReleased(int key, char c) {
	}
	public void keyPressed(int key, char c, boolean repeated) {
	}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
	}

	public void mouseReleased(int button, int x, int y) {
	}
	
	public void mousePressed(int button, int x, int y) {
	}

	public boolean contains(int x, int y) {
		return rect.contains(x, y);
	}
	
	public abstract void init(GameContainer container) throws SlickException;
	public abstract void render(Graphics g);
	
	public void setFocus(boolean value) {
		focus = value;
	}
	
	public Rectangle getRect()
	{
		return rect;
	}
}
