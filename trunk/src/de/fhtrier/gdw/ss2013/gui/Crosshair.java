/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.Image;

public class Crosshair {

    private Image crosshair;

	public Crosshair(Image crosshair) {
	    
	    this.crosshair = crosshair;

	}

	public void update() {

	}

	public void render(float x, float y) {
	    
	    crosshair.draw(x, y);
	    
	}
}
