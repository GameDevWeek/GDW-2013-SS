package de.fhtrier.gdw.ss2013;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

import de.fhtrier.gdw.ss2013.settings.DebugModeStatus;
import de.fhtrier.gdw.ss2013.states.LoadGameState;

/**
 * The Game object
 */
public class MainGame extends StateBasedGame {

    public static final String WINDOW_TITLE = "Symbion";
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final int MAINMENUSTATE = 0;
    public static final int GAMEPLAYSTATE = 1;
    public static final int LOADGAMESTATE = 2;
    public static int currentState = -1;
    public static MainGame instance;
    private LoadGameState loadGameState;
    private AppGameContainer container;
    private Input input;

    public MainGame(String[] args) {
        super(WINDOW_TITLE);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        this.container = (AppGameContainer) container;
        this.input = container.getInput();
        loadGameState = new LoadGameState();
        addState(loadGameState);
    }

    public void checkFullscreenToggle() {
        if (instance.input.isKeyDown(Input.KEY_ENTER)
                && (instance.input.isKeyDown(Input.KEY_LALT) || instance.input
                .isKeyDown(Input.KEY_RALT))) {
            toggleFullscreen();
        }
    }

    public static void toggleFullscreen() {
        try {
            if (instance.container.isFullscreen()) {
                instance.container.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT,
                        false);
            } else {
                instance.container.setDisplayMode(
                        instance.container.getScreenWidth(),
                        instance.container.getScreenHeight(), true);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void changeState(int id) {
        if (id == GAMEPLAYSTATE) {
            instance.container.setMouseGrabbed(true);
        }
        changeState(id, 500, 500);
    }

    public static void changeState(int id, int fadeOutTime, int fadeInTime) {
        if (id != currentState) {
            Transition transitionOut = new FadeOutTransition(Color.black,
                    fadeOutTime);
            Transition transitionIn = new FadeInTransition(Color.black,
                    fadeInTime);
            instance.enterState(id, transitionOut, transitionIn);

            currentState = id;
        }
    }

    public static void main(String[] args) {
        try {
            System.setProperty("org.lwjgl.librarypath",
                    new File(
                    new File(System.getProperty("user.dir"), "native"),
                    LWJGLUtil.getPlatformName()).getAbsolutePath());
            System.setProperty("net.java.games.input.librarypath",
                    System.getProperty("org.lwjgl.librarypath"));

            for (String a : args) {
                if (a.equals("-testmode")) {
                    DebugModeStatus.setStatus(false);
                }
                if (a.equals("-physictest")) {
                    DebugModeStatus.setPhysicTest(true);
                }
            }

            instance = new MainGame(args);
            AppGameContainer app = new AppGameContainer(instance);
            app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
            app.setAlwaysRender(false);
            app.setMaximumLogicUpdateInterval(60);
            app.setShowFPS(false);
            app.setMouseGrabbed(true);
            app.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
}