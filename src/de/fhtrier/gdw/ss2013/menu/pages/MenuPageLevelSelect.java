package de.fhtrier.gdw.ss2013.menu.pages;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.Label;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.ToggleButton;
import de.fhtrier.gdw.ss2013.menu.Widget.Align;
import de.fhtrier.gdw.ss2013.states.GameplayState;

public class MenuPageLevelSelect extends MenuPage {

    public MenuPageLevelSelect(GameContainer container, StateBasedGame game,
            MenuManager menuManager, MenuPage parent, String bgImage) throws SlickException {
        super(container, game, menuManager, parent, bgImage, "levelselect");
        // TODO Auto-generated constructor stub
        
        Font font = AssetLoader.getInstance().getFont("verdana_46");
        
        float xCenter = MenuManager.MENU_WIDTH / 2.0f;
        float yCenter = MenuManager.MENU_HEIGHT / 2.0f;
        
        float hText = font.getLineHeight() * 1.5f;
        
        Label lvlAuswahl = addLeftAlignedLabel("Levelauswahl:", 10, yCenter * 0.25f , font);
        
        
        String[] levels = {"run_or_die", "not_yet_implemented"};
        
        float space = font.getWidth(lvlAuswahl.text);
        addCenteredToggleButton(levels, 10 + space + 10, yCenter * 0.25f, font, Align.CENTER);
        
        addCenteredButton("GO!", xCenter, yCenter, font, 
                new IActionListener() { 
                    public void onAction() {
                        MainGame.changeState(MainGame.GAMEPLAYSTATE);
                    }
                }
        );
        
        float textHeight = font.getLineHeight();
        addCenteredButton("zurück", xCenter, MenuManager.MENU_HEIGHT - 1.5f * textHeight, font, 
                new IActionListener() { 
                    public void onAction() {
                        close();
                    }
                }
        );
    }

}
