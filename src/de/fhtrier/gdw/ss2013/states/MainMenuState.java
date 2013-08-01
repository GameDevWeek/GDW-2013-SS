
package de.fhtrier.gdw.ss2013.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import org.newdawn.slick.Music;

/** Menu state */
public class MainMenuState extends BasicGameState {
	private MenuManager menuManager;
    private Music music;
    private GameContainer container;

	@Override
	public void init (final GameContainer container, final StateBasedGame game) throws SlickException {
        this.container = container;
		menuManager = new MenuManager(container, game, MenuManager.Type.MAINMENU);
        
        music = AssetLoader.getInstance().getMusic("menu");
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		menuManager.render(container, game, g);
	}

	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) throws SlickException {
		((MainGame)game).checkFullscreenToggle();
		menuManager.update(container, game, delta);
	}

	@Override
	public int getID () {
		return MainGame.MAINMENUSTATE;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		menuManager.activate();
		if (music != null)
			music.loop(1f, 1f);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		if (music != null)
			music.stop();
		
		menuManager.activate();
	}

    @Override
	public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_F1:
                container.setMouseGrabbed(!container.isMouseGrabbed());
                break;
            case Input.KEY_F4:
                if(music.playing())
                    music.stop();
                else
                    music.play();
                break;
            case Input.KEY_ENTER:
			MainGame.changeState(MainGame.GAMEPLAYSTATE);
                break;
            default:
                menuManager.keyReleased(key, c);
                break;
        }
	}

    @Override
	public void keyPressed(int key, char c) {
		menuManager.keyPressed(key, c);
	}

    @Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		menuManager.mouseMoved(oldx, oldy, newx, newy);
	}

    @Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		menuManager.mouseDragged(oldx, oldy, newx, newy);
	}

    @Override
	public void mouseReleased(int button, int x, int y) {
		menuManager.mouseReleased(button, x, y);
	}

    @Override
	public void mousePressed(int button, int x, int y) {
		menuManager.mousePressed(button, x, y);
	}
	
    @Override
	public void mouseWheelMoved(int newValue) {
		menuManager.mouseWheelMoved(newValue);
	}

}
