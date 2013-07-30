/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class Crosshair {

    private Image crosshair;

	public Crosshair(Image crosshair) {
	    
	    this.crosshair = crosshair;

	}

	public void update() {

	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
	    
	    crosshair.draw();
	    
	}
}
