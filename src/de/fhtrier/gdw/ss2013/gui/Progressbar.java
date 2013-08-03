/**
 * Boris, David (UI-Team)
 */

package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class Progressbar {
	private Vector2f position;
	private Image bar;
	// Werte für konkreten Balken
	private float maxValue; // gibt den Maximalwert an
	private float currentValue; // gibt den aktuellen Wert an
	private float currentPercentValue; // currentValue in Prozent in Bezug auf
										// maxValue
	private World worldinstance; 

	public void init(Vector2f position, Image bar, World worldinstance) {
		this.position = position.copy();

		this.bar = bar;
		this.worldinstance = worldinstance;
		this.maxValue=worldinstance.getAstronaut().getMaxOxygen();
		bar.setCenterOfRotation(0.f, bar.getHeight()/2);		
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		currentValue = worldinstance.getAstronaut().getOxygen();
		
		currentPercentValue = ((currentValue / maxValue) * 100);
		
		if (currentPercentValue < 0)
		    currentPercentValue = 0;
		
		bar.setRotation(90 * (100-currentPercentValue)/100);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {	    
	    g.setClip((int)position.x, (int)position.y, bar.getWidth(), bar.getHeight()/2);
	    bar.draw(position.x, position.y - bar.getHeight()/2);
        
	    g.setClip(0, 0, container.getWidth(), container.getHeight());
	}
}
