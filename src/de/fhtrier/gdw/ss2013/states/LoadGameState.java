package de.fhtrier.gdw.ss2013.states;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.debug.DebugModeStatus;
import de.fhtrier.gdw.ss2013.util.AssetLoader;

/**
 * Loading state
 */
public class LoadGameState extends BasicGameState {

    // private Image loadscreen;
    private MainMenuState mainMenuState;
    private GameplayState gameplayState;
    private DeferredResource nextResource;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        // loadscreen = new Image("/res/LoadScreen/titlescreen.png");
        LoadingList.setDeferredLoading(true);

        // Todo: initialize assets
        new AssetLoader();
//        Image img = new Image("res/animaions/team0.png");
        
        mainMenuState = new MainMenuState();
        mainMenuState.init(container, game);
        game.addState(mainMenuState);

        gameplayState = new GameplayState();
        gameplayState.init(container, game);
        game.addState(gameplayState);

        boolean fullscreen = false;

        if (fullscreen != container.isFullscreen()) {
            MainGame.toggleFullscreen();
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        container.setShowFPS(false);

        // int total = LoadingList.get().getTotalResources();
        // int loaded = LoadingList.get().getTotalResources()
        // - LoadingList.get().getRemainingResources();
        // if (loaded == 0) {
        // loaded = 1;
        // }
        // total = total / loaded;
        //
        // g.setColor(Color.red);
        // g.fillRect(container.getWidth() / 2 - 245,
        // container.getHeight() / 2 - 70, (int) (5.5f * (100 / total)),
        // 100);
        // loadscreen.draw((container.getWidth() - loadscreen.getWidth()) / 2,
        // (container.getHeight() - loadscreen.getHeight()) / 2);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        if (nextResource != null) {
            try {
                nextResource.load();
            } catch (IOException e) {
                throw new SlickException("Failed to load: "
                        + nextResource.getDescription(), e);
            }
            nextResource = null;
        }

        if (LoadingList.get().getRemainingResources() > 0) {
            nextResource = LoadingList.get().getNext();
        } else {
            container.setShowFPS(true);

            if (DebugModeStatus.getStatus()) {
                MainGame.changeState(MainGame.GAMEPLAY);
            } else {
                MainGame.changeState(MainGame.MAINMENUSTATE);
            }
        }
    }

    @Override
    public int getID() {
        return 2;
    }
}
