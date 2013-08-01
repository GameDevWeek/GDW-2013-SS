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
	private Image adaptedBar;
	private boolean b = false;
	
	private Color colorKey;
	
	// Werte für konkreten Balken
	private float maxValue; // gibt den Maximalwert an
	private float currentValue; // gibt den aktuellen Wert an
	private float currentPercentValue; // currentValue in Prozent in Bezug auf
										// maxValue
	private float filled; // gibt an wie weit der Balken gefüllt ist

	private World worldinstance; 
	
	//private Image progress;
	private Font font;

	public void init(Vector2f position, Vector2f size,
			Image frame, Image background, Image bar, Font font, World worldinstance) {
		this.position = position.copy();
		
		
		this.frame = frame;
		this.background = background;
		this.bar = bar;
		try {
            this.adaptedBar = new Image(bar.getWidth(), bar.getHeight()/2);
        } catch (SlickException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		this.font = font;
		this.worldinstance = worldinstance;
		this.maxValue=worldinstance.getAstronaut().getMaxOxygen();
		
		//colorKey = new Color(45,4,200);
		colorKey = new Color(0.0f, 0.0f,0.0f, 0.0f);
		bar.setCenterOfRotation(0.f, bar.getHeight()/2);
		
		
		//this.size = size.copy();
		this.size = new Vector2f(bar.getWidth(), bar.getHeight());
		
		b = false;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		currentValue = worldinstance.getAstronaut().getOxygen();
		
		currentPercentValue = ((currentValue / maxValue) * 100);
		
		if (currentPercentValue < 0)
		    currentPercentValue = 0;
		
		filled = size.x * (currentPercentValue / 100);
		
		//Bar rotieren und adaptedBar aus Bar erzeugen
		//angeleht an diese Website:
		//http://ray3k.com/site/tutorials/slick-offscreen-rendering/
		
		bar.setRotation(90 * (100-currentPercentValue)/100);
		
		Graphics f = container.getGraphics();
        f.clear();
        f.drawImage(bar,position.x,position.y-bar.getHeight()/2);
        //save the result to the outputImage
        System.out.println("float: (" + position.x + ", " + position.y + ") Int: (" + (int)position.x + ", " + (int)position.y+ ")");
        f.copyArea(adaptedBar, (int)position.x, (int)position.y);		
        f.clear();
        
       		

	}
	
//	Image creatAdaptedBar(Image bar);

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
	    //es gilt für alle:	 g=container.getGraphics();
	    g.setBackground(colorKey);
	    adaptedBar.draw(position.x, position.y);		
		
		CenteredText.draw(position.x + size.x / 2 , position.y + size.y / 2, 
		                  String.format("%.2f %%", currentPercentValue),font);
	    
	}

}
