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

import de.fhtrier.gdw.ss2013.debug.TestModeStatus;
import de.fhtrier.gdw.ss2013.states.LoadGameState;

//comment so commit works

/**
 * The Game object
 */
public class MainGame extends StateBasedGame {

    public static final String WINDOW_TITLE = "GameDevWeek SS2013";
    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;
    public static final int MAINMENUSTATE = 0;
    public static final int GAMEPLAY = 1;
    public static final int TEAMDEATHMATCH = 2;
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

    public static void checkFullscreenToggle() {
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
                if (a.equals("-testmode"))
                    TestModeStatus.setStatus(true);
            }

            instance = new MainGame(args);
            AppGameContainer app = new AppGameContainer(instance);
            app.setDisplayMode(1024, 768, false);
            app.setAlwaysRender(true);
            app.setMaximumLogicUpdateInterval(60);
            app.setVSync(true);
            app.setShowFPS(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
}