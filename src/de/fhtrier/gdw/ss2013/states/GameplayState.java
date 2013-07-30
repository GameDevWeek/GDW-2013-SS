package de.fhtrier.gdw.ss2013.states;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.gui.HUD;

/**
 * Gameplay state
 */
public class GameplayState extends BasicGameState {

	private World world;
	private Font font;
	private HUD hud;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		world = new World(container, game);
		font = AssetLoader.getInstance().getFont("verdana_46");
		hud = new HUD(container, world);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setBackground(Color.black);
		g.setColor(Color.white);

		world.render(container, g);
		

		font.drawString(0, 0, "Gameplay");
		font.drawString(0, container.getHeight() - font.getLineHeight(),
				"Use Arrowkeys to move");

		hud.render(container, game, g);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		MainGame.checkFullscreenToggle();
		world.update(container, delta);

		hud.update(container, game, delta);
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
