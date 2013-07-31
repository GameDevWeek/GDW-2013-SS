
package de.fhtrier.gdw.ss2013.menu.pages;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.states.GameplayState;


public class MenuPageRoot extends MenuPage {

	public MenuPageRoot (final GameContainer container, final StateBasedGame _game, final MenuManager manager, final boolean ingame)
		throws SlickException {
		super(container, _game, manager, null, null, "root");
		
		Font font = AssetLoader.getInstance().getFont("verdana_46");
		
		float x = 100;
		float y = 480;
		float h = font.getLineHeight() * 1.2f;
		int i=2;
		addLeftAlignedButton("Symbion", x, y - h * (i--), font, 
			new IActionListener() {
				public void onAction() {
					GameplayState.hideMenu();
				}
			});
	}

}
