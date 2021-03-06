
package de.fhtrier.gdw.ss2013.menu.pages;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AttributeSet.FontAttribute;

import de.fhtrier.gdw.ss2013.MainGame;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.ScoreInfo;
import de.fhtrier.gdw.ss2013.game.score.HighscoreManager;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.menu.Button;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.Label;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.MenuPageAction;
import de.fhtrier.gdw.ss2013.states.GameplayState;


public class MenuPageRoot extends MenuPage {
    
    private Label gameName;
    private Button spielStarten;
    private Button steuerung;
    private Button highscore;
    private Button credits;
    private Button beenden;
    
	public MenuPageRoot (final GameContainer container, final StateBasedGame _game, final MenuManager manager)
		throws SlickException {
		super(container, _game, manager, null, null, "root");
		
		Font font = AssetLoader.getInstance().getFont("jabjai_heavy");
				
		float hText = font.getLineHeight() * 1.5f;
        
		float xCenter = MenuManager.MENU_WIDTH / 2.0f;
		float yCenter = MenuManager.MENU_HEIGHT / 2.0f;
		
	    Image logo = AssetLoader.getInstance().getImage("symbion_logo_menu");
	    //bgImage = AssetLoader.getInstance().getImage("background_menu");
	    
	    addCenteredLabel(logo, xCenter, yCenter * 0.5f);
	    
	    int buttonCount, i;
	    buttonCount = i = 5;
	    
	    MenuPageLevelSelect levelSelect = new MenuPageLevelSelect(container, _game, manager, this, null);
	    addCenteredButton("Spiel starten", xCenter, yCenter + hText * (buttonCount - i--), font, 
	            new MenuPageAction(manager, levelSelect));
	    
	    MenuPageOptions options = new MenuPageOptions(container, _game, manager, this, null);
	    addCenteredButton("Einstellungen", xCenter, yCenter + hText * (buttonCount - i--), font, 
	            new MenuPageAction(manager, options));
	    
	    MenuPageHighscore highscore = new MenuPageHighscore(container, _game, manager, this, null);
	    addCenteredButton("Highscore", xCenter, yCenter + hText * (buttonCount - i--), font,
	            new MenuPageAction(manager, highscore));
	    
	    
	    MenuPageCredits credits = new MenuPageCredits(container, _game, manager, this);
	    addCenteredButton("Credits", xCenter, yCenter + hText * (buttonCount - i--), font, 
	    		new MenuPageAction(manager, credits));

	    
	    addCenteredButton("Beenden", xCenter, yCenter + hText * (buttonCount - i--), font, 
                new IActionListener() { 
                    public void onAction() {
                        System.exit(0);
                    }
                }
        );
		
		System.out.println(HighscoreManager.getHighscoreRank("test", 54));
	}
}
