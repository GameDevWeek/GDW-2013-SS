package de.fhtrier.gdw.ss2013.menu.pages;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.MenuPageAction;
import de.fhtrier.gdw.ss2013.states.GameplayState;

public class MenuPageGamePause extends MenuPage {

    public MenuPageGamePause(GameContainer container, StateBasedGame game,
            MenuManager menuManager) throws SlickException {
        super(container, game, menuManager, null, null, "pause");
        
        Font font = AssetLoader.getInstance().getFont("verdana_46");
        
        float x = 100;
        float y = 200;
        float h = font.getLineHeight() * 1.5f;
        
        
        int buttonCount, i;
        buttonCount = i = 4;
        
        addLeftAlignedButton("Fortsetzen", x, y + h * (buttonCount - i--), font, 
                new IActionListener() {
                    @Override
                    public void onAction() {
                        GameplayState.hideMenu();
                    }
                }
        );
        
        addLeftAlignedButton("Neustarten", x, y + h * (buttonCount - i--), font, 
                new IActionListener() {
                    @Override
                    public void onAction() {
                        World.getInstance().shallBeReseted(true);
                        GameplayState.hideMenu();
                    }
                }
        );
        
        addLeftAlignedButton("Zurück zum Hauptmenü", x, y + h * (buttonCount - i--), font, 
                new IActionListener() {
                    @Override
                    public void onAction() {
                    }
                }
        );
        
        MenuPageHighscore highscore = new MenuPageHighscore(container, game, menuManager, this, null);
        addLeftAlignedButton("Highscore", x, y + h * (buttonCount - i--), font, 
                new MenuPageAction(menuManager, highscore));
        
        addLeftAlignedButton("Beenden", x, MenuManager.MENU_HEIGHT - h, font, 
                new IActionListener() {
                    @Override
                    public void onAction() {
                        System.exit(0);
                    }
                }
        );
    }

}
