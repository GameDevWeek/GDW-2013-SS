/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class Crosshair {

    private Image crosshair;
    private World worldinstance;

	public Crosshair(Image crosshair, World worldinstance) {
	    
	    this.crosshair = crosshair;
	    this.worldinstance = worldinstance;

	}

	public void update() {

	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
	    
	    //crosshair.draw(worldinstance.getAlien().getPointingAt()); //Noch nicht da!
	    
	}
}
