
package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.Log;
import org.lwjgl.input.*;

import de.fhtrier.gdw.ss2013.input.InputManager.ACTION;

public class Mouse extends InputDevice {

	public Mouse (GameContainer gc) {
		super(gc);
		loadKeymapping("mouse");
	}

	@Override
	public void update () {
		super.update();
		if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			doAction(ACTION.SHOOT);
		}
		if (container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			doAction(ACTION.USEABILITY);
		}
		doAction(ACTION.TARGET, container.getInput().getMouseX(), container.getInput().getMouseY());
		
		int dWheel = org.lwjgl.input.Mouse.getDWheel();
		if (dWheel < 0) {
			doAction(ACTION.ROTATEABILITY_UP);
			Log.debug("Mousewheel: " + dWheel);

		} else if (dWheel > 0) {
			doAction(ACTION.ROTATEABILITY_DOWN);
			Log.debug("Mousewheel: " + dWheel);

		}
	}

}
