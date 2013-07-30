package de.fhtrier.gdw.ss2013.input;

import java.util.HashSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public class Keyboard extends InputDevice {

	public Keyboard(GameContainer gc) {
		super(gc);
		loadKeymapping();
	}

	@Override
	public void update() {

		HashSet<ACTIONS> actions = new HashSet<>(keymapping.values());
		for (int key : keymapping.keySet()) {
			if (container.getInput().isKeyDown(key)) {
				ACTIONS action = keymapping.get(key);
				if (actions.contains(action)) {
					actions.remove(action);
					doAction(action);
				}
			}
		}

		// else if (container.getInput().isKeyDown(Input.KEY_A)) {
		// astronautController.moveBackward();
		// }
		//
		// else if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
		// astronautController.jump();
		// }
		//
		// else if (container.getInput().isKeyPressed(Input.KEY_E)) {
		// astronautController.action();
		// }

	}

	private void doAction(ACTIONS action) {
		switch (action) {
		case MOVEFORWARD:
			astronautController.moveForward();
			break;
		case MOVEBACKWARD:
			astronautController.moveBackward();
			break;
		case JUMP:
			astronautController.jump();
			break;
		case ACTION:
			astronautController.action();
			break;
		default:
			break;
		}
	}

	@Override
	public void loadKeymapping() {
		keymapping.put(Input.KEY_D, ACTIONS.MOVEFORWARD);
		keymapping.put(Input.KEY_RIGHT, ACTIONS.MOVEFORWARD);

		keymapping.put(Input.KEY_A, ACTIONS.MOVEBACKWARD);
		keymapping.put(Input.KEY_LEFT, ACTIONS.MOVEBACKWARD);

		keymapping.put(Input.KEY_W, ACTIONS.JUMP);
		keymapping.put(Input.KEY_UP, ACTIONS.JUMP);

		keymapping.put(Input.KEY_S, ACTIONS.ACTION);
		keymapping.put(Input.KEY_DOWN, ACTIONS.ACTION);

	}

}
