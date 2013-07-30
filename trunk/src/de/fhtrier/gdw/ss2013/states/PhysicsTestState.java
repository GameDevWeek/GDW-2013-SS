package de.fhtrier.gdw.ss2013.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.input.InputManager;

public class PhysicsTestState extends BasicGameState{

	private InputManager inputManager;
	private World world;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	    InputManager.init(container);
	    inputManager = InputManager.getInstance();
		world = new World(container, game);
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		world.render(container, g);
		
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		MainGame.checkFullscreenToggle();
		world.update(container, delta);
		inputManager.update(delta);
		
	}

	public int getID() {
		return MainGame.PHYSIC_TEST;
	}

}
