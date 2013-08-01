package de.fhtrier.gdw.ss2013.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;

public class GameWonState extends BasicGameState {

	private Image winImage;
	private float x, y;

	private final int WAIT_TIME = 4000;
	private final int LOAD_TIME = 5000;
	private int time = 0;
	private int initTime = 0;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		winImage = AssetLoader.getInstance().getImage("winning");
		x = gc.getWidth() / 2 - winImage.getWidth() / 2;
		y = gc.getHeight();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		winImage.draw(x, y);
		g.drawString("You have won!", 50, 50);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		initTime += delta;

		if (initTime > WAIT_TIME) {
			time += delta;
			if (y > gc.getHeight() - winImage.getHeight()) {
				float f = (time * 1f / LOAD_TIME);
				y = gc.getHeight() - (f * winImage.getHeight());
			}
		}
	}

	@Override
	public int getID() {
		return MainGame.WINSTATE;
	}

}
