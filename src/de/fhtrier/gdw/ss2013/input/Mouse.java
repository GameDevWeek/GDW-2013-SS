
package de.fhtrier.gdw.ss2013.input;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.ControlsInfo;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.Log;

import java.util.HashMap;

public class Mouse extends InputDevice {

	private static enum Button {

		LEFT, RIGHT, WHEEL_UP, WHEEL_DOWN
	}

	private HashMap<Button, InputAction> buttonMap = new HashMap<>();

	private int oldX, oldY;

	public Mouse (GameContainer gc) {
		super(gc);
		loadSettings();
	}

	@Override
	public void update () {
		if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			doButton(Button.LEFT);
		}
		if (container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			doButton(Button.RIGHT);
		}
		if (oldX != container.getInput().getMouseX() || oldY != container.getInput().getMouseY()) {
			manager.setCursor(InputAction.TARGET, container.getInput().getMouseX(), container.getInput().getMouseY());
		}
		oldX = container.getInput().getMouseX();
		oldY = container.getInput().getMouseY();

		int dWheel = org.lwjgl.input.Mouse.getDWheel();
		if (dWheel < 0) {
			doButton(Button.WHEEL_UP);
			Log.debug("Mousewheel: " + dWheel);

		} else if (dWheel > 0) {
			doButton(Button.WHEEL_DOWN);
			Log.debug("Mousewheel: " + dWheel);

		}
	}

	private void loadSettings () {

		ControlsInfo controlsInfo = AssetLoader.getInstance().getControls();
		if (controlsInfo.mouse != null && controlsInfo.mouse.alien != null) {
			addButton(InputAction.SHOOT, controlsInfo.mouse.alien.SHOOT);
			addButton(InputAction.USE_ABILITY, controlsInfo.mouse.alien.USE_ABILITY);
			addButton(InputAction.NEXT_ABILITY, controlsInfo.mouse.alien.NEXT_ABILITY);
			addButton(InputAction.PREV_ABILITY, controlsInfo.mouse.alien.PREV_ABILITY);
		}
	}

	private void doButton (Button button) {
		InputAction action = buttonMap.get(button);
		if (action != null) {
			manager.doAction(action);
		}
	}

	private void addButton (InputAction action, String buttonName) {
		buttonMap.put(Button.valueOf(buttonName), action);
	}
}
