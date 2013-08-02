package de.fhtrier.gdw.ss2013.menu.pages;

import java.util.Random;

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
import de.fhtrier.gdw.ss2013.menu.Widget.Align;

public class MenuPageHighscore extends MenuPage {

    public MenuPageHighscore(GameContainer container, StateBasedGame game,
            MenuManager menuManager, MenuPage parent, String bgImage) throws SlickException {
        super(container, game, menuManager, parent, null, "highscore");
        
        Font font = AssetLoader.getInstance().getFont("jabjai_heavy");
        float hText = font.getLineHeight() * 1.5f;
        
        float xCenter = MenuManager.MENU_WIDTH / 2.0f;
        float yCenter = MenuManager.MENU_HEIGHT / 2.0f;
        
        Label title = addCenteredLabel("Highscore", xCenter, yCenter * 0.20f, font);
        
        font = font = AssetLoader.getInstance().getFont("jabjai_light");
        hText = font.getLineHeight() * 1.5f;
        
        
        float multiply = 0.35f;
        
        for (int i = 0; i < 10; i++) {
            //Platzierung
            Label rank = addLeftAlignedLabel(i+1 + ".", 25, yCenter * multiply + (hText * i), font);
            rank.size(50, font.getLineHeight());
            //Astronaut
            Label astro = addLeftAlignedLabel("Astronaut " + i + "" , 75, yCenter * multiply + (hText * i), font);
            astro.size(50, font.getLineHeight());
            //Alien
            Label alien = addLeftAlignedLabel("Alien " + i + "", 400, yCenter * multiply + (hText * i), font);
            alien.size(50, font.getLineHeight());
            //Score
            Label score = addLeftAlignedLabel(Math.round(Math.random() * 100000) + "", 800, yCenter * multiply + (hText * i), font);
            score.size(50, font.getLineHeight());
            
        }
        
        font = AssetLoader.getInstance().getFont("verdana_46");
        hText = font.getLineHeight() * 1.5f;
        
        addCenteredButton("zurück", xCenter, MenuManager.MENU_HEIGHT - 1.5f * font.getLineHeight(), font, 
                new IActionListener() { 
                    public void onAction() {
                        close();
                    }
                }
        );
        
    }

}
