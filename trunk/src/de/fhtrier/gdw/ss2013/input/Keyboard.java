package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import de.fhtrier.gdw.ss2013.input.InputDevice.ACTIONS;

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

		if (container.getInput().isKeyDown(keymapping.get(ACTIONS.MOVEFORWARD))) {
			astronautController.moveForward();
		}
		if (container.getInput().isKeyDown(keymapping.get(ACTIONS.MOVEBACKWARD))) {
			astronautController.moveBackward();
		}
		if (container.getInput().isKeyDown(keymapping.get(ACTIONS.JUMP))) {
			astronautController.jump();
		}
		if (container.getInput().isKeyDown(keymapping.get(ACTIONS.ACTION))) {
			astronautController.action();
		} 
//		else if (container.getInput().isKeyDown(Input.KEY_A)) {
//			astronautController.moveBackward();
//		}
//
//		else if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
//			astronautController.jump();
//		}
//
//		else if (container.getInput().isKeyPressed(Input.KEY_E)) {
//			astronautController.action();
//		}

	}

	@Override
	public void loadKeymapping() {
		// TODO Auto-generated method stub
		keymapping.put(ACTIONS.MOVEFORWARD, Input.KEY_D);
		keymapping.put(ACTIONS.MOVEFORWARD, Input.KEY_RIGHT);

		keymapping.put(ACTIONS.MOVEBACKWARD, Input.KEY_A);
		keymapping.put(ACTIONS.MOVEBACKWARD, Input.KEY_LEFT);

		keymapping.put(ACTIONS.JUMP, Input.KEY_W);
		keymapping.put(ACTIONS.JUMP, Input.KEY_UP);

		keymapping.put(ACTIONS.ACTION, Input.KEY_S);
		keymapping.put(ACTIONS.ACTION, Input.KEY_DOWN);

	}

}
