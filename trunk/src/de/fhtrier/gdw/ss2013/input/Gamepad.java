
package de.fhtrier.gdw.ss2013.input;

import de.fhtrier.gdw.ss2013.assetloader.infos.ControlsInfo;
import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.input.Controller;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.util.Log;

public class Gamepad extends InputDevice {

	private HashMap<String, InputAction> actionMap = new HashMap<>();
	private HashMap<String, Integer> buttonNameMap = new HashMap<>();
	org.lwjgl.input.Controller controller;

	public Gamepad (GameContainer gc, ControlsInfo.GamepadInfo gamepadInfo) {
		super(gc);

		// müsste in config datei ausgelagert werden pro Controller(eigentlich pro treiber...)
		buttonNameMap.put("A", 0);
		buttonNameMap.put("B", 1);
		buttonNameMap.put("X", 2);
		buttonNameMap.put("Y", 3);
// buttonNameMap.put("Left Trigger", 0);
// buttonNameMap.put("Right Trigger", 0);

		// controller suchen der zur aktuellen Config passt
		String name = gamepadInfo.name;
		int numControllers = org.lwjgl.input.Controllers.getControllerCount();
		for (int i = 0; i < numControllers; i++) {
			org.lwjgl.input.Controller c = org.lwjgl.input.Controllers.getController(i);
			if (c.getName().equalsIgnoreCase(name)) {
				controller = c;
				break;
			}
		}

		if (controller == null) {
			throw new IllegalArgumentException("Controller config not found: " + name);
		}

		if (gamepadInfo.astronaut != null) {

// setKey(InputAction.MOVE_LEFT, gamepadInfo.astronaut.MOVE_LEFT);
// setKey(InputAction.MOVE_RIGHT, gamepadInfo.astronaut.MOVE_RIGHT);
			actionMap.put(gamepadInfo.astronaut.JUMP, InputAction.JUMP);
			actionMap.put(gamepadInfo.astronaut.ACTION, InputAction.ACTION);
		}

		if (gamepadInfo.alien != null) {
// public String CURSOR_LEFTRIGHT;
// public String CURSOR_UPDOWN;
			actionMap.put(gamepadInfo.alien.SHOOT, InputAction.SHOOT);
			actionMap.put(gamepadInfo.alien.USE_ABILITY, InputAction.USE_ABILITY);
			actionMap.put(gamepadInfo.alien.NEXT_ABILITY, InputAction.NEXT_ABILITY);
			actionMap.put(gamepadInfo.alien.PREV_ABILITY, InputAction.PREV_ABILITY);
		}
	}

	@Override
	public void update () {

		for (Entry<String, InputAction> entry : actionMap.entrySet()) {
			if (buttonNameMap.keySet().contains(entry.getKey())) {
				if (controller.isButtonPressed(buttonNameMap.get(entry.getKey()))) {
					manager.doAction(entry.getValue());
				}
			}
		}

		if (controller.getPovX() > 0) {
			doButton("+POV_X");
		} else if (controller.getPovX() < 0) {
			doButton("-POV_X");
		}

		if (controller.getPovY() > 0) {
			doButton("+POV_Y");
		} else if (controller.getPovY() < 0) {
			doButton("-POV_Y");
		}
		Log.debug("new frame");
		int numAxes = controller.getAxisCount();
		for (int i = 0; i < numAxes; i++) {
			float value = controller.getAxisValue(i);
			Log.debug("Achse: " + controller.getAxisName(i) + " Wert " + controller.getAxisValue(i));
			if (value > 0.4f) {
				doButtonFromAnalog("+" + controller.getAxisName(i), value);
			} else if (value < -0.4f) {
				doButtonFromAnalog("-" + controller.getAxisName(i), value);
			}
		}
	}

	public void doButton (String button) {
		switch (button) {
		case "-POV_Y":
			manager.doAction(InputAction.JUMP);
			break;
		case "-X-Achse":
			manager.doAction(InputAction.MOVE_LEFT);
			break;
		case "+POV_X":
			manager.doAction(InputAction.MOVE_RIGHT);
			break;
		}
	}

	public void doButtonFromAnalog (String button, float value) {
		switch (button) {
		case "-Y-Achse":
			manager.doAction(InputAction.JUMP);
			break;
		case "-X-Achse":
			manager.doAction(InputAction.MOVE_LEFT);
			break;
		case "+X-Achse":
			manager.doAction(InputAction.MOVE_RIGHT);
			break;
		case "+X-Rotation":
			manager.setCursorDelta(InputAction.CURSOR_RIGHT, value);
			break;
		case "-X-Rotation":
			manager.setCursorDelta(InputAction.CURSOR_LEFT, value);
			break;
		case "+Y-Rotation":
			manager.setCursorDelta(InputAction.CURSOR_DOWN, value);
			break;
		case "-Y-Rotation":
			manager.setCursorDelta(InputAction.CURSOR_UP, value);
			break;
		}
	}

	public void doAxis (String axis, float value) {
		InputAction action = actionMap.get(axis);
		if (action != null) {
			manager.doAction(action);
		}
	}
}
