/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.commons.utils.FpsCalculator;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.gui.AbilityDisplay.AbilityDisplay;
import de.fhtrier.gdw.ss2013.gui.AbilityDisplay.AbilityDisplayManager;
import de.fhtrier.gdw.ss2013.gui.AbilityDisplay.DynamicAbilityDisplay;
import de.fhtrier.gdw.ss2013.gui.AbilityDisplay.StaticAbilityDisplay;
import de.fhtrier.gdw.ss2013.gui.counter.ScoreCounter;
import de.fhtrier.gdw.ss2013.gui.utils.CenteredText;

public class HUD {

	private Progressbar healthbar;
	private Crosshair crosshair;
	private TooltipManager tooltipmanager;
	private AbilityDisplayManager abilityDisplayManager;
	private AssetLoader asset;
    private FpsCalculator fpsCalc = new FpsCalculator(100);
    private Font fpsFont;
    private AbilityDisplay[] abilityDisplay;
    //private ScoreCounter scoreCounter;

	public HUD(GameContainer container, World worldinstance) throws SlickException {
	        
		//init Assetloader
	    asset = AssetLoader.getInstance();
	     
	    // Init healthbar
	    healthbar = new Progressbar();


		final Vector2f position = new Vector2f(20, 20);	
        
        final Image bar = asset.getImage("healthbar");
        final Font font = asset.getFont("verdana_24");
        
        healthbar.init(position, bar, worldinstance);

		// Init abilityDisplayManager

		abilityDisplayManager = new AbilityDisplayManager();

		abilityDisplay = new AbilityDisplay[4];
		
		//init staticAbilityDisplay
		final Image staticAbilityDisplay1_Image = asset.getImage("ability1_static");
        final Image staticAbilityDisplay2_Image = asset.getImage("ability2_static");

		final Vector2f abilityDisplay1_Position = new Vector2f(staticAbilityDisplay1_Image.getWidth(), 0.0f);
		final Vector2f abilityDisplay2_Position = new Vector2f(staticAbilityDisplay2_Image.getWidth(), staticAbilityDisplay2_Image.getHeight());
		
		final float staticAbilityDisplayFadingspeed = 0.5f;
		
		abilityDisplay[0]=new StaticAbilityDisplay(staticAbilityDisplay1_Image, abilityDisplay1_Position,staticAbilityDisplayFadingspeed);
        abilityDisplay[1]=new StaticAbilityDisplay(staticAbilityDisplay2_Image, abilityDisplay2_Position,staticAbilityDisplayFadingspeed);
        
		//init dynamicAbilityDisplay
		final Image dynamicAbilityDisplay1_Image = asset.getImage("ability1_dynamic");
        final Image dynamicAbilityDisplay2_Image = asset.getImage("ability2_dynamic");
				
		final float dynamicAbilityDisplayFadingspeed = 4.0f;
		
		abilityDisplay[2]=new DynamicAbilityDisplay(dynamicAbilityDisplay1_Image, dynamicAbilityDisplayFadingspeed, worldinstance);
        abilityDisplay[3]=new DynamicAbilityDisplay(dynamicAbilityDisplay2_Image, dynamicAbilityDisplayFadingspeed, worldinstance);
        
        abilityDisplayManager.init(abilityDisplay, worldinstance);
        
    	//init Crosshair
		final Image crosshairImage = asset.getImage("crosshair");
		crosshair = new Crosshair();
		crosshair.init(worldinstance, crosshairImage);
		
		//init tooltip
		
		tooltipmanager = TooltipManager.getInstance();
		//tooltipmanager = new Tooltip();
		tooltipmanager.init(worldinstance, font);
	
		
		//tooltipmanager.addTooltip(new Vector2f (500,500), crosshairImage, new Vector2f (500,500), 500);
		
        fpsFont = asset.getFont("quartz_40");
        
        //Init ScoreCounter
        /*
        Image scoreCounter_digits = AssetLoader.getInstance().getImage("digits");
        Vector2f scoreCounter_position = new Vector2f(140.0f,5.0f);
        int scoreCounter_startValue = 0000;
        int scoreCounter_numberOfDigits = 4;
        float scoreCounter_countingSpeed = 2.0f;
        scoreCounter = new ScoreCounter( scoreCounter_digits
                                        ,scoreCounter_position
                                        ,scoreCounter_startValue
                                        ,scoreCounter_numberOfDigits
                                        ,scoreCounter_countingSpeed);
	*/
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {

		healthbar.update(container, game, delta);
		abilityDisplayManager.update(container, game, delta);
		fpsCalc.update();
		
		//scoreCounter.update(container, game, delta);
		//scoreCounter.setDesiredValue(World.getScoreCounter().getScore());
		
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		healthbar.render(container, game, g);
		abilityDisplayManager.render(container, game, g);
		tooltipmanager.render();
		crosshair.render(container, game, g);
		//scoreCounter.render(container, game, g);
		
		
        String fps = String.format("%d fps", (int)fpsCalc.calculate());
        CenteredText.draw(container.getWidth() / 2, 30, fps, fpsFont);
	}

}
