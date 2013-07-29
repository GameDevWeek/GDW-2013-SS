package de.fhtrier.gdw.ss2013.states;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.game.World;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Gameplay state
 */
public class GameplayState extends BasicGameState {

    private World world;
   // private Font font;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        world = new World(container, game);
        font = new AngelCodeFont("res/fonts/verdana_46.fnt", "res/fonts/verdana_46_0.tga");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.setBackground(Color.black);
        g.setColor(Color.white);

        world.render(container, g);
        
        font.drawString(0, 0, "Gameplay");
        font.drawString(0, container.getHeight() - font.getLineHeight(), "Use Arrowkeys to move");
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        MainGame.checkFullscreenToggle();
        world.update(container, delta);
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
