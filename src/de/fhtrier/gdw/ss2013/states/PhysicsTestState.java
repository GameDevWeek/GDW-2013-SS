package de.fhtrier.gdw.ss2013.states;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.input.InputManager;
import de.fhtrier.gdw.ss2013.physics.PhysicsManager;
import de.fhtrier.gdw.ss2013.physics.RectanglePhysicsObject;
import de.fhtrier.gdw.ss2013.physics.test.TestWorld;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;

public class PhysicsTestState extends BasicGameState {

	private InputManager inputManager;
	private TestWorld world;
	private RectanglePhysicsObject rec;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		InputManager.init(container);
		inputManager = InputManager.getInstance();
		world = new TestWorld(container, game);

		// Physic Test Objects
		rec = new RectanglePhysicsObject(BodyType.DYNAMIC, new Vec2(50.0f,
				50.0f), new Vec2(400.0f, 400.0f));

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
		world.getPhysicsManager().update(container, delta);

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		world.getPhysicsManager().reset();
	}

	public int getID() {
		return MainGame.PHYSIC_TEST;
	}

	public void keyReleased(int key, char c) {

		// triggers PhysicTestState
		if (key == Input.KEY_EQUALS || key == Input.KEY_P) {
			MainGame.changeState(MainGame.PHYSIC_TEST);
		}
		if (key == Input.KEY_SPACE) {

			RectanglePhysicsObject rpo = new RectanglePhysicsObject(
					BodyType.DYNAMIC, new Vec2(30, 30), new Vec2(500, 300));
			rpo.setMassData(100);
			rpo.setLinearVelocity(new Vec2(
					(float) (100 + Math.random() * 1000 - 500),
					(float) (100 + Math.random() * 500)));
		}
	}

}
