package de.fhtrier.gdw.ss2013.menu.pages;

import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.SettingsInfo;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.Label;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.TextField;
import de.fhtrier.gdw.ss2013.menu.ToggleButton;
import de.fhtrier.gdw.ss2013.menu.Widget.Align;
import de.fhtrier.gdw.ss2013.states.GameplayState;

public class MenuPageLevelSelect extends MenuPage {

    public MenuPageLevelSelect(GameContainer container, StateBasedGame game,
            MenuManager menuManager, MenuPage parent, String bgImage) throws SlickException {
        super(container, game, menuManager, parent, bgImage, "levelselect");
        
        Font font = AssetLoader.getInstance().getFont("jabjai_heavy");
        
        float spaceX = 300;
        float xCenter = MenuManager.MENU_WIDTH / 2.0f;
        float yCenter = MenuManager.MENU_HEIGHT / 2.0f;
        
        float hText = font.getLineHeight() * 1.5f;
        
        Label lblAstronaut = addLeftAlignedLabel("Astronaut: ", 25, yCenter * 0.25f, font);
        
        TextField tfAstroName = TextField.create("", spaceX, yCenter * 0.25f, 500, font.getLineHeight());
        
        tfAstroName.hint("Tastaturspieler");
        tfAstroName.font(font);
        tfAstroName.image(AssetLoader.getInstance().getImage("text_bg"));
        tfAstroName.focusImage(AssetLoader.getInstance().getImage("text_focus"));
        tfAstroName.color(Color.white);
        
        addWidget(tfAstroName);
        
        Label lblAlien = addLeftAlignedLabel("Alien: ", 25, yCenter * 0.25f + hText, font);

        
        TextField tfAlienName = TextField.create("", spaceX, yCenter * 0.25f + hText, 500, font.getLineHeight());
        
        tfAlienName.hint("Mausspieler");
        tfAlienName.font(font);
        tfAlienName.image(AssetLoader.getInstance().getImage("text_bg"));
        tfAlienName.focusImage(AssetLoader.getInstance().getImage("text_focus"));
        tfAlienName.color(Color.white);
        
        addWidget(tfAlienName);
        
        Label lvlAuswahl = addLeftAlignedLabel("Levelauswahl:", 25, yCenter * 0.25f + (hText * 3) , font);
        
        
        Set<String> levels = AssetLoader.getInstance().getMapInfos();
        
        final String[] lvls = levels.toArray(new String[levels.size()]);
        final ToggleButton tb = addLeftAlignedToggleButton(lvls, spaceX, yCenter * 0.25f + (hText * 3), font, Align.LEFT);
        
        SettingsInfo settings = AssetLoader.getInstance().getSettings();
        int lvlIndex = 0;
        
        if (settings.lastLoadedMap != null) {
            for(int i = 0; i < lvls.length; i++) {
                String s = lvls[i];
                if (s.equals(settings.lastLoadedMap)) {
                    lvlIndex = i;
                    break;
                }
            }
        }
        
        tb.state(lvlIndex);
        
        addCenteredButton("Los geht's!", xCenter, yCenter * 1.5f, font, new IActionListener() {
            public void onAction () {
                World.getInstance().setLevelName(lvls[tb.getState()]);
                SettingsInfo settings = AssetLoader.getInstance().getSettings();
                settings.lastLoadedMap = lvls[tb.getState()];
                AssetLoader.getInstance().writeSettings(settings);
                World.getInstance().shallBeReseted(true);
                MainGame.changeState(MainGame.GAMEPLAYSTATE);
            }
        });
        
        addCenteredButton("zurueck", xCenter, MenuManager.MENU_HEIGHT - 1.5f * font.getLineHeight(), font, 
                new IActionListener() { 
                    public void onAction() {
                        close();
                    }
                }
        );
    }

}
