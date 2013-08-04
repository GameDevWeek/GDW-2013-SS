package de.fhtrier.gdw.ss2013.menu.pages;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.SettingsInfo;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.menu.Button;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.Label;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.MenuPageAction;

public class MenuPageRoot extends MenuPage {

    private Label gameName;
    private Button spielStarten;
    private Button steuerung;
    private Button highscore;
    private Button credits;
    private Button beenden;

    public MenuPageRoot(final GameContainer container,
            final StateBasedGame _game, final MenuManager manager,
            final boolean ingame) throws SlickException {
        super(container, _game, manager, null, null, "root");

        Font font = AssetLoader.getInstance().getFont("jabjai_heavy");

        float hText = font.getLineHeight() * 1.5f;

        float xCenter = MenuManager.MENU_WIDTH / 2.0f;
        float yCenter = MenuManager.MENU_HEIGHT / 2.0f;

        if (!ingame) {

            Image img = AssetLoader.getInstance().getImage("symbion_logo_menu");

            addCenteredLabel(img, xCenter, yCenter * 0.5f);

            int buttonCount, i;
            buttonCount = i = 5;

            MenuPageLevelSelect levelSelect = new MenuPageLevelSelect(
                    container, _game, manager, this, null);
            addCenteredButton("Spiel starten", xCenter, yCenter + hText
                    * (buttonCount - i--), font, new MenuPageAction(manager,
                    levelSelect));

            addCenteredButton("Los geht's!", xCenter, yCenter + hText
                    * (buttonCount - i--), font, new IActionListener() {
                public void onAction() {
                    SettingsInfo settings = AssetLoader.getInstance()
                            .getSettings();
                    AssetLoader.getInstance().writeSettings(settings);
                    World.getInstance().setLevelName(settings.lastLoadedMap);
                    World.getInstance().reset();
                    MainGame.changeState(MainGame.GAMEPLAYSTATE);
                }
            });

            MenuPageOptions options = new MenuPageOptions(container, _game,
                    manager, this, null);
            addCenteredButton("Einstellungen", xCenter, yCenter + hText
                    * (buttonCount - i--), font, new MenuPageAction(manager,
                    options));

            MenuPageHighscore highscore = new MenuPageHighscore(container,
                    _game, manager, this, null);
            addCenteredButton("Highscore", xCenter, yCenter + hText
                    * (buttonCount - i--), font, new MenuPageAction(manager,
                    highscore));

            MenuPageCredits credits = new MenuPageCredits(container, _game,
                    manager, this);
            addCenteredButton("Credits", xCenter, yCenter + hText
                    * (buttonCount - i--), font, new MenuPageAction(manager,
                    credits));

            addCenteredButton("Beenden", xCenter, yCenter + hText
                    * (buttonCount - i--), font, new IActionListener() {
                public void onAction() {
                    System.exit(0);
                }
            });
        }

    }
}
