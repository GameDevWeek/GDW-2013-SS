package de.fhtrier.gdw.ss2013.menu.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.ScoreInfo;
import de.fhtrier.gdw.ss2013.game.score.HighscoreManager;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.IUpdateListener;
import de.fhtrier.gdw.ss2013.menu.Label;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuManager.Type;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.ToggleButton;
import de.fhtrier.gdw.ss2013.menu.Widget;
import de.fhtrier.gdw.ss2013.menu.Widget.Align;

public class MenuPageHighscore extends MenuPage {
    
    private ToggleButton selectLevel;
    private List<Widget> astroWidgets = new ArrayList<>();
    private List<Widget> alienWidgets = new ArrayList<>();
    private List<Widget> scoreWidgets = new ArrayList<>();
    private String currentMapName;
    private Label lblLevel;
    
    public MenuPageHighscore(GameContainer container, StateBasedGame game,
            MenuManager menuManager, MenuPage parent, String bgImage) throws SlickException {
        super(container, game, menuManager, parent, null, "highscore");
        
        this.currentMapName = "";
        
        this.standardFont = AssetLoader.getInstance().getFont("jabjai_heavy");
        float hText = this.standardFont.getLineHeight() * 1.5f;
        
        float xCenter = MenuManager.MENU_WIDTH / 2.0f;
        float yCenter = MenuManager.MENU_HEIGHT / 2.0f;
        
        Label title = addCenteredLabel("Highscore", xCenter, yCenter * 0.20f, this.standardFont);
        
        Label lvl = addLeftAlignedLabel("Level:", 25, yCenter * 0.20f + hText , this.standardFont);
        
        float multiply = 0.6f;
        
        if (menuManager.getType() == Type.MAINMENU) {
            Set<String> levels = AssetLoader.getInstance().getMapInfos();
            String[] lvls = levels.toArray(new String[levels.size()]);
            selectLevel = addLeftAlignedToggleButton(lvls, this.standardFont.getWidth(lvl.text) + 50, yCenter * 0.20f + hText, this.standardFont, Align.LEFT);
            this.currentMapName = lvls[0];
            selectLevel.update(new IUpdateListener() {
                @Override
                public void onUpdate(Object value) {
                    try {
                        currentMapName = selectLevel.texts[selectLevel.getState()];
                        refreshHighscoreList();
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            this.lblLevel = addLeftAlignedLabel(this.currentMapName, this.standardFont.getWidth(lvl.text) + 50, yCenter * 0.20f + hText, this.standardFont);
        }
        
        Label rankU = addLeftAlignedLabel("Rang", 25, yCenter * multiply - hText, this.standardFont);
        //Astronaut
        Label astroU = addLeftAlignedLabel("Astronaut", 125, yCenter * multiply - hText, this.standardFont);
        //Alien
        Label alienU = addLeftAlignedLabel("Alien", 475, yCenter * multiply - hText, this.standardFont);
        //Score
        Label scoreU = addLeftAlignedLabel("Score", 850, yCenter * multiply - hText, this.standardFont);
        
        hText = this.standardFont.getLineHeight() * 1.25f;
        
        for (int i = 0; i < 10; i++) {
            //Platzierung
            Label rank = addLeftAlignedLabel(i+1 + ".", 25, yCenter * multiply + (hText * i+1), this.standardFont);
            //Astronaut
            Label astro = addLeftAlignedLabel("" , 125, yCenter * multiply + (hText * i+1), this.standardFont);
            astroWidgets.add(astro);
            //Alien
            Label alien = addLeftAlignedLabel("", 475, yCenter * multiply + (hText * i+1), this.standardFont);
            alienWidgets.add(alien);
            //Score
            Label score = addLeftAlignedLabel("", 850, yCenter * multiply + (hText * i+1), this.standardFont);
            scoreWidgets.add(score);
        }
        
        refreshHighscoreList();
        
        addCenteredButton("zurueck", xCenter, MenuManager.MENU_HEIGHT - 1.5f * this.standardFont.getLineHeight(), this.standardFont, 
                new IActionListener() { 
                    public void onAction() {
                        close();
                    }
                }
        );
        
    }
    
    public void refreshHighscoreList() throws SlickException {
        List<ScoreInfo> scores = HighscoreManager.getHighscoresFromMap(this.currentMapName);
        
        clearHighscoreList();
        
        if (scores != null) {
            for (int i = 0; i < scores.size(); i++) {
                Label astro = (Label) astroWidgets.get(i);
                astro.text(scores.get(i).astronautName);
                
                Label alien = (Label) alienWidgets.get(i);
                alien.text(scores.get(i).alienName);
                
                Label score = (Label) scoreWidgets.get(i);
                score.text(scores.get(i).score + "");
            }
        }
    }
    
    public void clearHighscoreList() {
        for (int i = 0; i < 10; i++) {
            Label astro = (Label) astroWidgets.get(i);
            astro.text("");
            
            Label alien = (Label) alienWidgets.get(i);
            alien.text("");
            
            Label score = (Label) scoreWidgets.get(i);
            score.text("");
        }
    }
    
    @Override
    public void activate() {
        if (this.menuManager.getType() == Type.INGAME) {
            this.currentMapName = World.getInstance().getLevelName();
            this.lblLevel.text(this.currentMapName);
        }
    }
}
