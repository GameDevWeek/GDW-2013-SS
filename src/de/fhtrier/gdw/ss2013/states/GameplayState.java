package de.fhtrier.gdw.ss2013.states;

import de.fhtrier.gdw.ss2013.MainGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Gameplay state
 */
public class GameplayState extends BasicGameState {

    GameContainer container;
    StateBasedGame game;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        this.container = container;
        this.game = game;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.setBackground(Color.black);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        MainGame.checkFullscreenToggle();
    }

    @Override
    public int getID() {
        return MainGame.GAMEPLAY;
    }

    @Override
    public void keyReleased(int key, char c) {
    }

    @Override
    public void keyPressed(int key, char c) {
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
    }

    @Override
    public void mousePressed(int button, int x, int y) {
    }

    @Override
    public void mouseWheelMoved(int newValue) {
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException {
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game)
            throws SlickException {
    }
}
