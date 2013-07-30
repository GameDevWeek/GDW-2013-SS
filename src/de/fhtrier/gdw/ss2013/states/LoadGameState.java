package de.fhtrier.gdw.ss2013.states;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.settings.DebugModeStatus;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;

/**
 * Loading state
 */
public class LoadGameState extends BasicGameState {

    // private Image loadscreen;
    private MainMenuState mainMenuState;
    private GameplayState gameplayState;
    private PhysicsTestState physicTestState;
    private DeferredResource nextResource;
    private AssetLoader assetLoader;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// loadscreen = new Image("/res/images/testbild.png");
		LoadingList.setDeferredLoading(true);

        // Todo: initialize assets

        assetLoader = AssetLoader.getInstance();
        SoundLocator.provideAssetLoader(assetLoader);

        // Image img = new Image("res/animaions/team0.png");

        physicTestState = new PhysicsTestState();
        physicTestState.init(container, game);
        game.addState(physicTestState);

        boolean fullscreen = false;

        if (fullscreen != container.isFullscreen()) {
            MainGame.toggleFullscreen();
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        container.setShowFPS(false);

		 int total = LoadingList.get().getTotalResources();
		 int loaded = total
		 - LoadingList.get().getRemainingResources();
		 if (loaded == 0) {
		 loaded = 1;
		 }
		 //total = total / loaded;
		
		 g.setColor(Color.red);
		 
		 g.fillRect(container.getWidth()/8, container.getHeight()/8*6,
		         container.getWidth() / 8 * 6  /total * loaded, 50);
		 
		 
		 
		 
		 //g.fillRect(container.getWidth() / 2 - 245,
		 //container.getHeight() / 2 - 70, (int) (5.5f * (100 / total)),
		 //100);
		 //loadscreen.draw((container.getWidth() - loadscreen.getWidth()) / 2,
		 //(container.getHeight() - loadscreen.getHeight()) / 2);
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
			SoundStore.get().setDeferredLoading(false);

	        mainMenuState = new MainMenuState();
	        mainMenuState.init(container, game);
	        game.addState(mainMenuState);

	        gameplayState = new GameplayState();
	        gameplayState.init(container, game);
	        game.addState(gameplayState);
	        
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
