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
	private Tooltip notation;
	private AbilitySelection abilityWheel;
	private Quickselect quickselect;
	private AssetLoader asset;
    private FpsCalculator fpsCalc = new FpsCalculator(100);
    private Font fpsFont;

	public HUD(GameContainer container, World worldinstance) throws SlickException {
		//init Assetloader
	    asset = AssetLoader.getInstance();
	     
	    // Init healthbar
	    healthbar = new Progressbar();

		final Vector2f position = new Vector2f(10, 10);
		final Vector2f size = new Vector2f(240, 40); /////////
		
		//final String imagePath = "/res/Dummy_GUIs_Images/";

		final Image frame = asset.getImage("healthBarFrame");
        final Image background = asset.getImage("healthBarBackground");
        final Image bar = asset.getImage("healthBarForeground");
        final Font font = asset.getFont("verdana_24");
        
        healthbar.init(position, size, frame, background, bar, font, worldinstance);

		// Init abilityWheel

		abilityWheel = new AbilitySelection();

		position.set(20, 20);

		final Image ability1 = asset.getImage("ability1");
        final Image ability2 = asset.getImage("ability2");
        final Image ability3 = asset.getImage("ability3");
        
        abilityWheel.init(ability1, ability2, ability3, position, worldinstance);
		
		//abilityWheel.init(ability1, ability2, ability3, position);

		// Init quickselect
		quickselect = new Quickselect();
		final int selected = 1;
		final int countdown_start = 500;
		quickselect.init(ability1, ability2, ability3, selected,
				countdown_start, worldinstance);

		
		//init Crosshair
		final Image crosshairImage = asset.getImage("crosshair1");
		crosshair = new Crosshair();
		crosshair.init(worldinstance, crosshairImage);
		
		//init tooltip
		notation = new Tooltip();
		notation.init(worldinstance, font);

        fpsFont = asset.getFont("quartz_40");
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {

		healthbar.update(container, game, delta);
		abilityWheel.update(container, game, delta);
		quickselect.update(container, game, delta);
		fpsCalc.update();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		healthbar.render(container, game, g);
		abilityWheel.render(container, game, g);
		quickselect.render(container, game, g);
		notation.render();
		crosshair.render(container, game, g);
		
        String fps = String.format("%d fps", (int)fpsCalc.calculate());
        CenteredText.draw(container.getWidth() / 2, 30, fps, fpsFont);
	}
}
