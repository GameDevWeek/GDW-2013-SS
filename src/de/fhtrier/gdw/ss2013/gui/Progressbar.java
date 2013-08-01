/**
 * Boris, David (UI-Team)
 */

package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.gui.utils.CenteredText;

public class Progressbar {
	private Vector2f position;
	private Vector2f size;

	private Image frame;
	private Image background;
	private Image bar;
	
	// Werte für konkreten Balken
	private float maxValue; // gibt den Maximalwert an
	private float currentValue; // gibt den aktuellen Wert an
	private float currentPercentValue; // currentValue in Prozent in Bezug auf
										// maxValue
	private World worldinstance; 
	
	//private Image progress;
	private Font font;

	public void init(Vector2f position, Vector2f size,
			Image frame, Image background, Image bar, Font font, World worldinstance) {
		this.position = position.copy();
		
		
		this.frame = frame;
		this.background = background;
		this.bar = bar;
		this.font = font;
		this.worldinstance = worldinstance;
		this.maxValue=worldinstance.getAstronaut().getMaxOxygen();
		this.size = new Vector2f(bar.getWidth(), bar.getHeight());
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
		
		CenteredText.draw(position.x + size.x / 2 , position.y + size.y / 2, 
		                  String.format("%.2f %%", currentPercentValue),font);
	}
}
