
package de.fhtrier.gdw.ss2013.input;

import java.util.HashMap;
import java.util.HashSet;

import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.input.InputManager.ACTION;

public class Gamepad extends InputDevice {

	private HashMap<String, Integer> buttons = new HashMap<>();
	org.lwjgl.input.Controller controller;

	public Gamepad (GameContainer gc, int index) {
		super(gc);
		loadKeymapping("gamepad");
		controller = org.lwjgl.input.Controllers.getController(index);
		// über alle buttons laufen Controller.getbuttonCount
		// name davon speichern
	}

	@Override
	public void update () {
		super.update();
		if (controller.getPovX() < 0.0f) {
			doAction(ACTION.MOVEBACKWARD);
		}

		if (controller.getPovX() > 0.0f) {
			doAction(ACTION.MOVEFORWARD);
		}

	}

}
