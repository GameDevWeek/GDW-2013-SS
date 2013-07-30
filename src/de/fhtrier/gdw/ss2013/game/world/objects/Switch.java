package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

/**
 * Switch Class
 * 
 * @author Kevin, Georg
 * 
 */
public class Switch extends Entity implements Interactable{

	boolean activated = false;

	public Switch(Vector2f pos) {
		super(pos.copy());
	}

	public Switch() {
		this(new Vector2f());
	}
	
	@Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
    }

	@Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    }
	
	public void turnSwitch() {
	    activated = !activated;
	}
	
	public boolean getSwitch() {
	    return activated;
	}
	
	public void setSwitch(boolean value) {
	    activated = value;
	}
}
