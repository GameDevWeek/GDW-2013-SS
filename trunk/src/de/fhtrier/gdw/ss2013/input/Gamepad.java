
package de.fhtrier.gdw.ss2013.input;

import de.fhtrier.gdw.ss2013.assetloader.infos.ControlsInfo;
import java.util.HashMap;
import org.newdawn.slick.GameContainer;

public class Gamepad extends InputDevice {

	private HashMap<String, InputAction> actionMap = new HashMap<>();
    private final boolean[] buttonDown;
	private org.lwjgl.input.Controller controller;

	public Gamepad (GameContainer gc, ControlsInfo.GamepadInfo gamepadInfo) {
		super(gc);

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
			actionMap.put(gamepadInfo.astronaut.MOVE_LEFT, InputAction.MOVE_LEFT);
			actionMap.put(gamepadInfo.astronaut.MOVE_RIGHT, InputAction.MOVE_RIGHT);
			actionMap.put(gamepadInfo.astronaut.JUMP, InputAction.JUMP);
			actionMap.put(gamepadInfo.astronaut.ACTION, InputAction.ACTION);
		}

		if (gamepadInfo.alien != null) {
			actionMap.put(gamepadInfo.alien.CURSOR_UP, InputAction.CURSOR_UP);
			actionMap.put(gamepadInfo.alien.CURSOR_DOWN, InputAction.CURSOR_DOWN);
			actionMap.put(gamepadInfo.alien.CURSOR_LEFT, InputAction.CURSOR_LEFT);
			actionMap.put(gamepadInfo.alien.CURSOR_RIGHT, InputAction.CURSOR_RIGHT);
			actionMap.put(gamepadInfo.alien.SHOOT, InputAction.SHOOT);
			actionMap.put(gamepadInfo.alien.USE_ABILITY, InputAction.USE_ABILITY);
			actionMap.put(gamepadInfo.alien.NEXT_ABILITY, InputAction.NEXT_ABILITY);
			actionMap.put(gamepadInfo.alien.PREV_ABILITY, InputAction.PREV_ABILITY);
		}
        
        buttonDown = new boolean[controller.getButtonCount()];
	}

	@Override
	public void update () {

        int buttons = controller.getButtonCount();
        for(int i=0; i<buttons; i++) {
            boolean down = controller.isButtonPressed(i);
            if (down != buttonDown[i]) {
                buttonDown[i] = down;
                if(down) {
                    doButton(controller.getButtonName(i));
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
        
		int numAxes = controller.getAxisCount();
		for (int i = 0; i < numAxes; i++) {
			float value = controller.getAxisValue(i);
			if (value > 0.1f) {
				doAxis("+" + controller.getAxisName(i), value);
			} else if (value < -0.1f) {
				doAxis("-" + controller.getAxisName(i), value);
			}
		}
	}

	public void doButton (String button) {
        InputAction action = actionMap.get(button);
        if(action != null) {
            manager.doAction(action);
        }
	}

	public void doAxis (String axis, float value) {
        value = Math.abs(value);
		InputAction action = actionMap.get(axis);
		if (action != null) {
            switch(action) {
                case CURSOR_LEFT:
                case CURSOR_RIGHT:
                case CURSOR_UP:
                case CURSOR_DOWN:
                    manager.setCursorDelta(action, value);
                    break;
                default:
                    // Axes should trigger buttons only on high values
                    if(value > 0.4f)
                        manager.doAction(action);
                    break;
            }
		}
	}
}
