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

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.gui.utils.CenteredText;

public class HUD {

	private Progressbar healthbar;
	private Crosshair crosshair;
	private Annotation notation;
	private AbilitySelection abilityWheel;
	private Image test;
	private Quickselect quickselect;
	private World worldinstance;
	private AssetLoader asset;

	public HUD(GameContainer container, World worldinstance) throws SlickException {
		//init Assetloader
	    asset = AssetLoader.getInstance();
	    
	    //init world
	    this.worldinstance = worldinstance;
	     
	    // Init healthbar
	    healthbar = new Progressbar();

		final Vector2f position = new Vector2f(10, 10);
		final Vector2f size = new Vector2f(240, 40);
		final int cornerradius = 5;

		//final String imagePath = "/res/Dummy_GUIs_Images/";

		final Image frame = asset.getImage("healthBarFrame");
        final Image background = asset.getImage("healthBarBackground");
        final Image bar = asset.getImage("healthBarForeground");
        final Font font = asset.getFont("verdana_24");
        
        healthbar.init(position, size, cornerradius, frame, background, bar, font, worldinstance);

		// Init abilityWheel

		abilityWheel = new AbilitySelection();

		position.set(20, 20);

		final Image ability1 = asset.getImage("ability1");
        final Image ability2 = asset.getImage("ability2");
        final Image ability3 = asset.getImage("ability3");
        
        abilityWheel.init(ability1, ability2, ability3, position);
		
		//abilityWheel.init(ability1, ability2, ability3, position);

		// Init quickselect
		quickselect = new Quickselect();
		final int selected = 1;
		final int countdown_start = 500;
		quickselect.init(ability1, ability2, ability3, selected,
				countdown_start, worldinstance);

		// DEV
		quickselect.setSelected(2);
		
		
		//init Crosshair
		final Image cross = asset.getImage("ability1");
		crosshair = new Crosshair(cross, worldinstance);

	}

	public void update(GameContainer container, StateBasedGame game, int delta) {

		healthbar.update(container, game, delta);
		quickselect.update(container, game, delta);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		healthbar.render(container, game, g);
		abilityWheel.render(container, game, g);
		quickselect.render(container, game, g);
		//crosshair.render(container, game, g);
		
	}
}
