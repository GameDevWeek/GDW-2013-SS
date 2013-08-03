package de.fhtrier.gdw.ss2013.menu.pages;

import java.util.Random;
import java.util.Set;

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
import de.fhtrier.gdw.ss2013.menu.ToggleButton;
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
        
        
        Label lvl = addLeftAlignedLabel("Level:", 25, yCenter * 0.20f + hText , font);
        
        float multiply = 0.6f;
        
        Set<String> levels = AssetLoader.getInstance().getMapInfos();
        final String[] lvls = levels.toArray(new String[levels.size()]);
        final ToggleButton tb=addLeftAlignedToggleButton(lvls, font.getWidth(lvl.text) + 50, yCenter * 0.20f + hText, font, Align.LEFT);
        
        Label rankU = addLeftAlignedLabel("Rang", 25, yCenter * multiply - hText, font);
        rankU.size(50, font.getLineHeight());
        //Astronaut
        Label astroU = addLeftAlignedLabel("Astronaut", 125, yCenter * multiply - hText, font);
        astroU.size(50, font.getLineHeight());
        //Alien
        Label alienU = addLeftAlignedLabel("Alien", 475, yCenter * multiply - hText, font);
        alienU.size(50, font.getLineHeight());
        //Score
        Label scoreU = addLeftAlignedLabel("Score", 850, yCenter * multiply - hText, font);
        scoreU.size(50, font.getLineHeight());
        
        
        hText = font.getLineHeight() * 1.25f;
        
        for (int i = 0; i < 10; i++) {
            //Platzierung
            Label rank = addLeftAlignedLabel(i+1 + ".", 25, yCenter * multiply + (hText * i+1), font);
            rank.size(50, font.getLineHeight());
            //Astronaut
            Label astro = addLeftAlignedLabel("Astronaut " + i + "" , 125, yCenter * multiply + (hText * i+1), font);
            astro.size(50, font.getLineHeight());
            //Alien
            Label alien = addLeftAlignedLabel("Alien " + i + "", 475, yCenter * multiply + (hText * i+1), font);
            alien.size(50, font.getLineHeight());
            //Score
            Label score = addLeftAlignedLabel(Math.round(Math.random() * 100000) + "", 850, yCenter * multiply + (hText * i+1), font);
            score.size(50, font.getLineHeight());
            
        }
        
        hText = font.getLineHeight() * 1.5f;
        
        addCenteredButton("zurueck", xCenter, MenuManager.MENU_HEIGHT - 1.5f * font.getLineHeight(), font, 
                new IActionListener() { 
                    public void onAction() {
                        close();
                    }
                }
        );
        
    }

}
