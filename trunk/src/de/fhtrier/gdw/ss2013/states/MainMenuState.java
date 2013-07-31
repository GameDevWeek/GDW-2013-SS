
package de.fhtrier.gdw.ss2013.states;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.MenuManager;

/** Menu state */
public class MainMenuState extends BasicGameState {
	private Font font;
	private MenuManager menuManager;

	@Override
	public void init (final GameContainer container, final StateBasedGame game) throws SlickException {
		font = AssetLoader.getInstance().getFont("quartz_40");
		menuManager = new MenuManager(container, null, MenuManager.Type.MAINMENU);
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
// font.drawString(0, 0, "Main Menu");
// font.drawString(container.getWidth() / 2, container.getHeight() / 2,
// "Press Enter");
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
	public void keyReleased (int key, char c) {
		if (key == Input.KEY_ENTER) {
			MainGame.changeState(MainGame.GAMEPLAYSTATE);
		}
	}

}
