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
    
    private TextField tfAstroName;
    private TextField tfAlienName;
    private ToggleButton tbLevelSelection;
    private String standardNames = "Unnamed";
    private String[] levels;
    
    public MenuPageLevelSelect(GameContainer container, StateBasedGame game,
            MenuManager menuManager, MenuPage parent, String bgImage) throws SlickException {
        super(container, game, menuManager, parent, bgImage, "levelselect");
        
        standardFont = AssetLoader.getInstance().getFont("jabjai_heavy");
        
        float spaceX = 300;
        float xCenter = MenuManager.MENU_WIDTH / 2.0f;
        float yCenter = MenuManager.MENU_HEIGHT / 2.0f;
        
        float hText = standardFont.getLineHeight() * 1.5f;
        
        SettingsInfo settings = AssetLoader.getInstance().getSettings();
        
        Label lblAstronaut = addLeftAlignedLabel("Astronaut: ", 25, yCenter * 0.25f, standardFont);
        
        Font textFieldFont = AssetLoader.getInstance().getFont("verdana_46");
        
        tfAstroName = TextField.create("", spaceX, yCenter * 0.25f, 500, standardFont.getLineHeight());
        
        tfAstroName.hint("Tastaturspieler");
        tfAstroName.font(textFieldFont);
        tfAstroName.image(AssetLoader.getInstance().getImage("text_bg"));
        tfAstroName.focusImage(AssetLoader.getInstance().getImage("text_focus"));
        tfAstroName.color(this.standardColor);
        tfAstroName.hintColor(Color.gray);
        
        if (settings.lastAstronautName != null)
            tfAstroName.text(settings.lastAstronautName);
        
        addWidget(tfAstroName);
        
        Label lblAlien = addLeftAlignedLabel("Alien: ", 25, yCenter * 0.25f + hText, standardFont);

        
        tfAlienName = TextField.create("", spaceX, yCenter * 0.25f + hText, 500, standardFont.getLineHeight());
        
        tfAlienName.hint("Mausspieler");
        tfAlienName.font(textFieldFont);
        tfAlienName.image(AssetLoader.getInstance().getImage("text_bg"));
        tfAlienName.focusImage(AssetLoader.getInstance().getImage("text_focus"));
        tfAlienName.color(this.standardColor);
        tfAlienName.hintColor(Color.gray);
        
        if (settings.lastAlienName != null)
            tfAlienName.text(settings.lastAlienName);
        
        addWidget(tfAlienName);
        
        Label lvlAuswahl = addLeftAlignedLabel("Levelauswahl:", 25, yCenter * 0.25f + (hText * 3) , standardFont);
        
        Set<String> lvls = AssetLoader.getInstance().getMapInfos();
        levels = lvls.toArray(new String[lvls.size()]);
        
        tbLevelSelection = addLeftAlignedToggleButton(levels, spaceX, yCenter * 0.25f + (hText * 3), standardFont, Align.LEFT);
        
        int lvlIndex = 0;
        
        if (settings.lastLoadedMap != null) {
            for(int i = 0; i < levels.length; i++) {
                String s = levels[i];
                if (s.equals(settings.lastLoadedMap)) {
                    lvlIndex = i;
                    break;
                }
            }
        }
        
        tbLevelSelection.state(lvlIndex);
        
        addCenteredButton("Los geht's!", xCenter, yCenter * 1.5f, standardFont, new IActionListener() {
            public void onAction () {
                String selectedLevel = levels[tbLevelSelection.getState()];
                String astroName = tfAstroName.getText().trim();
                String alienName = tfAlienName.getText().trim();
                
                
                SettingsInfo settings = AssetLoader.getInstance().getSettings();
                settings.lastLoadedMap = levels[tbLevelSelection.getState()];
                
                
                if (astroName.trim().isEmpty()) {
                    astroName = standardNames;
                } else {
                    settings.lastAstronautName = astroName;
                }
                
                if (alienName.trim().isEmpty()) {
                    alienName = standardNames;
                } else {
                    settings.lastAlienName = alienName;
                }
                
                AssetLoader.getInstance().writeSettings(settings);
                
                World.getInstance().getAstronaut().setName(astroName);
                World.getInstance().getAlien().setName(alienName);
                
                World.getInstance().setLevelName(selectedLevel);
                World.getInstance().reset();
                MainGame.changeState(MainGame.GAMEPLAYSTATE);
            }
        });
        
        addCenteredButton("zurueck", xCenter, MenuManager.MENU_HEIGHT - 1.5f * standardFont.getLineHeight(), standardFont, 
                new IActionListener() { 
                    public void onAction() {
                        close();
                    }
                }
        );
    }
    
    @Override
    public void activate() {
        
    }
    
}
