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

public class MenuPageGamePause extends MenuPage {

    public MenuPageGamePause(GameContainer container, StateBasedGame game,
            MenuManager menuManager) throws SlickException {
        super(container, game, menuManager, null, null, "pause");
        
        Font font = AssetLoader.getInstance().getFont("verdana_46");
        
        float x = 100;
        float y = 480;
        float h = font.getLineHeight() * 1.2f;
        int i=1;
        
        addLeftAlignedButton("Fortsetzen", x, y -h * (i--), font, 
                new IActionListener() {
                    public void onAction() {
                        GameplayState.hideMenu();
                    }
                }
        );
    }

}
