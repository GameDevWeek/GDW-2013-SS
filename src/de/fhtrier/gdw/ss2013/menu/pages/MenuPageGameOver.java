
package de.fhtrier.gdw.ss2013.menu.pages;


import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.Label;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.TextField;

/**
 * 
 * @author BreakingTheHobbit
 *
 */
public class MenuPageGameOver extends MenuPage {
    
    TextField go;
    Label label;
    Font font;
    long startTime = -1;
    long count = 0;
    long points = 4567;
    double animationFactor = 800;

    
	public MenuPageGameOver (final GameContainer container, final StateBasedGame _game, final MenuManager manager, final boolean ingame)
		throws SlickException {
		super(container, _game, manager, null, null, "root");
		
		font = AssetLoader.getInstance().getFont("jabjai_heavy");
        
        go = addCenteredTextField("Game Over", MenuManager.MENU_WIDTH/2, MenuManager.MENU_HEIGHT/2, font)
                .color(Color.red);
        
        addCenteredButton("Exit", MenuManager.MENU_WIDTH-font.getWidth("Exit")-50, MenuManager.MENU_HEIGHT-font.getHeight("Exit")-50, font,
			new IActionListener() {
                @Override
				public void onAction() {
					System.exit(0); // todo
				}
			});
	}
	
//	@Override
//	public void render(Graphics g) {
//	    super.render(g);
//	}
}
