
package de.fhtrier.gdw.ss2013.input;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.ControlsInfo;
import java.util.LinkedList;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.util.Log;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */
public class InputManager {

	private static InputManager instance;

	public static void init (GameContainer container) {
		if (instance == null) {
			instance = new InputManager(container);
			instance.createControllers();
		}
	}

	public static InputManager getInstance () {
		if (instance == null) {
			Log.error("InputManager nicht initialisiert!");
		}
		return instance;
	}

	private GameContainer container;
	private int delta;
	private LinkedList<InputDevice> activeDevices = new LinkedList<>();
	private LinkedList<Gamepad> gamepads = new LinkedList<>();
	private Keyboard keyboard;
	private Mouse mouse;

	private AlienController alienController = null;
	private AstronautController astronautController = null;

	private InputManager (GameContainer container) {
		this.container = container;
	}

	private void createControllers () {

		keyboard = new Keyboard(container);
		mouse = new Mouse(container);

		ControlsInfo controlsInfo = AssetLoader.getInstance().getControls();
		if (controlsInfo.gamepads != null) {
			for (ControlsInfo.GamepadInfo gamepadInfo : controlsInfo.gamepads) {
				try {
					Gamepad gamepad = new Gamepad(container, gamepadInfo);
					gamepads.add(gamepad);
				} catch (IllegalArgumentException e) {
					Log.warn("Failed to create controller: " + e.getMessage());
				}
			}
		}
		
		//Temporär!
		activeDevices.add(keyboard);
		activeDevices.add(mouse);
		for (Gamepad pad : gamepads) {
			activeDevices.add(pad);
		}
		printControllerInfo();
		
	}

	public void activateDevice (InputDevice device) {
		if (!activeDevices.contains(device)) {
			activeDevices.add(device);
		}
		Log.info("InputDevice wurde bereits aktiviert!");
	}
	
	public void deactivateDevice(InputDevice device){
		activeDevices.remove(device);
	}

	public void update (int delta) {
		for (InputDevice device : activeDevices) {
			device.update();
		}
	}

	protected void doAction (InputAction action) {
		if (astronautController != null) {
			switch (action) {
			case MOVE_RIGHT:
				astronautController.moveRight();
				break;
			case MOVE_LEFT:
				astronautController.moveLeft();
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
		if (alienController != null) {
			switch (action) {
			case SHOOT:
				alienController.shoot();
				break;
			case NEXT_ABILITY:
				alienController.nextAbility();
				break;
			case PREV_ABILITY:
				alienController.previousAbility();
				break;
			case USE_ABILITY:
				alienController.useAbility();
				break;
			case CURSOR_LEFT:
				alienController.cursorLeft();
				break;
			case CURSOR_RIGHT:
				alienController.cursorRight();
				break;
			case CURSOR_UP:
				alienController.cursorUp();
				break;
			case CURSOR_DOWN:
				alienController.cursorDown();
				break;
			default:
				break;
			}
		}
	}

	protected void setCursor (InputAction action, int x, int y) {
		if (alienController != null) {
			alienController.setCursor(x, y);
			// Log.debug("Alien pointing at " + x +" " +y);
		}
	}

	protected void setCursorDelta (InputAction action, float value) {
		switch (action) {
		case CURSOR_DOWN:
			alienController.cursorDown();
			break;
		case CURSOR_UP:
			alienController.cursorUp();
			break;
		case CURSOR_LEFT:
			alienController.cursorLeft();
			break;
		case CURSOR_RIGHT:
			alienController.cursorRight();
			break;
		default:
			break;
		}
	}

	public Keyboard getKeyboard () {
		if (keyboard != null) {
			return keyboard;
		}
		Log.error("Keine Tastatur angeschlossen!");
		return null;

	}

	public Mouse getMouse () {

		if (mouse != null) {
			return mouse;
		}
		Log.error("Keine Maus angeschlossen!");
		return null;
	}

	public Gamepad getGamepad (int id) {
		if (gamepads.size() >= id) {
			return gamepads.get(id);
		}
		Log.error("Kein Gamepad mit ID " + id + " angeschlossen!");
		return null;

	}

	public int getGamepadCount () {
		return gamepads.size();
	}

	public void setAlienController (AlienController ac) {
		alienController = ac;
	}

	public void setAstronautController (AstronautController ac) {
		astronautController = ac;
	}

	public final AlienController getAlienController () {
		return alienController;
	}

	public final AstronautController getAstronautController () {
		return astronautController;
	}

	private void printControllerInfo () {

		int numControllers = org.lwjgl.input.Controllers.getControllerCount();
		for (int i = 0; i < numControllers; i++) {
			org.lwjgl.input.Controller c = org.lwjgl.input.Controllers.getController(i);
			Log.debug(c.getName());
			for (int j = 0; j < c.getButtonCount(); j++) {
				Log.debug("Buttonindex: " + j + " Name: " + c.getButtonName(j));
			}
			for (int j = 0; j < c.getAxisCount(); j++) {
				Log.debug("Axisindex: " + j + " Name: " + c.getAxisName(j));
			}
		}
	}
}
