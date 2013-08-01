/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.gui;

import de.fhtrier.gdw.commons.utils.FpsCalculator;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.World;
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

	public HUD(GameContainer container, World worldinstance) throws SlickException {
	        
		//init Assetloader
	    asset = AssetLoader.getInstance();
	     
	    // Init healthbar
	    healthbar = new Progressbar();


		final Vector2f position = new Vector2f(20, 20);
		final Vector2f size = new Vector2f(0,0);
		
		final Image frame = asset.getImage("healthBarFrame");
        final Image background = asset.getImage("healthbar_rund_background");
        final Image bar = asset.getImage("healthbar");
        final Font font = asset.getFont("verdana_24");
        
        healthbar.init(position, size, frame, background, bar, font, worldinstance);

		// Init abilityDisplayManager

		abilityDisplayManager = new AbilityDisplayManager();

		abilityDisplay = new AbilityDisplay[6];
		
		//init staticAbilityDisplay
		final Image staticAbilityDisplay1_Image = asset.getImage("ability1");
        final Image staticAbilityDisplay2_Image = asset.getImage("ability2");
        final Image staticAbilityDisplay3_Image = asset.getImage("ability3");

		final Vector2f abilityDisplay1_Position = new Vector2f(100,20);
		final Vector2f abilityDisplay2_Position = new Vector2f(70,70);
		final Vector2f abilityDisplay3_Position = new Vector2f(80,120);
		
		final float staticAbilityDisplayFadingspeed = 0.5f;
		
		abilityDisplay[0]=new StaticAbilityDisplay(staticAbilityDisplay1_Image, abilityDisplay1_Position,staticAbilityDisplayFadingspeed);
        abilityDisplay[1]=new StaticAbilityDisplay(staticAbilityDisplay2_Image, abilityDisplay2_Position,staticAbilityDisplayFadingspeed);
        abilityDisplay[2]=new StaticAbilityDisplay(staticAbilityDisplay3_Image, abilityDisplay3_Position,staticAbilityDisplayFadingspeed);
        
		
		//init dynamicAbilityDisplay
		final Image dynamicAbilityDisplay1_Image = asset.getImage("ability1");
        final Image dynamicAbilityDisplay2_Image = asset.getImage("ability2");
        final Image dynamicAbilityDisplay3_Image = asset.getImage("ability3");
				
		final float dynamicAbilityDisplayFadingspeed = 4.0f;
		
		abilityDisplay[3]=new DynamicAbilityDisplay(dynamicAbilityDisplay1_Image, dynamicAbilityDisplayFadingspeed, worldinstance);
        abilityDisplay[4]=new DynamicAbilityDisplay(dynamicAbilityDisplay2_Image, dynamicAbilityDisplayFadingspeed, worldinstance);
        abilityDisplay[5]=new DynamicAbilityDisplay(dynamicAbilityDisplay3_Image, dynamicAbilityDisplayFadingspeed, worldinstance);
        
        abilityDisplayManager.init(abilityDisplay, worldinstance);
        
    	//init Crosshair
		final Image crosshairImage = asset.getImage("crosshair2");
		crosshair = new Crosshair();
		crosshair.init(worldinstance, crosshairImage);
		
		//init tooltip
		
		tooltipmanager = TooltipManager.getInstance();
		//tooltipmanager = new Tooltip();
		tooltipmanager.init(worldinstance, font);
	
		
		//tooltipmanager.addTooltip(new Vector2f (500,500), crosshairImage, new Vector2f (500,500), 500);
		
        fpsFont = asset.getFont("quartz_40");
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {

		healthbar.update(container, game, delta);
		abilityDisplayManager.update(container, game, delta);
		fpsCalc.update();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		healthbar.render(container, game, g);
		abilityDisplayManager.render(container, game, g);
		tooltipmanager.render();
		crosshair.render(container, game, g);
		
        String fps = String.format("%d fps", (int)fpsCalc.calculate());
        CenteredText.draw(container.getWidth() / 2, 30, fps, fpsFont);
	}

}
