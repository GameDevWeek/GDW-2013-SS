
package de.fhtrier.gdw.ss2013.input;

import java.util.LinkedList;

import org.lwjgl.input.Controllers;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
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
	private LinkedList<InputDevice> devices = new LinkedList<>();
	private LinkedList<Gamepad> gamepads = new LinkedList<>();
	private Keyboard keyboard;
	private Mouse mouse;

	enum ACTION {
		MOVEFORWARD("MOVEFORWARD"), MOVEBACKWARD("MOVEBACKWARD"), JUMP("JUMP"), ACTION("ACTION"), SHOOT("SHOOT"), TARGET("TARGET"), ROTATEABILITY_UP(
			"ROTATEABILITY_UP"), ROTATEABILITY_DOWN("ROTATEABILITY_DOWN"), USEABILITY("USEABILITY");

		public String name;

		private ACTION (String name) {
			this.name = name;
		}

		public static ACTION getAction (String name) {
			ACTION action = null;
			switch (name) {
			case "MOVEFORWARD":
				action = ACTION.MOVEFORWARD;
				break;
			case "MOVEBACKWARD":
				action = ACTION.MOVEBACKWARD;
				break;
			case "JUMP":
				action = ACTION.JUMP;
				break;
			case "ACTION":
				action = ACTION.ACTION;
				break;
			case "SHOOT":
				action = ACTION.SHOOT;
				break;
			case "USEABILITY":
				action = ACTION.USEABILITY;
				break;
			case "TARGET":
				action = ACTION.TARGET;
				break;
			default:
				Log.error("Es existiert keine Action mit der Bezeichnung " + name);
				break;
			}
			return action;
		}
	};

	private InputManager (GameContainer container) {
		this.container = container;

		keyboard = new Keyboard(container);
		devices.add(keyboard);
		mouse = new Mouse(container);
		devices.add(mouse);

		for (int i = 0; i < org.lwjgl.input.Controllers.getControllerCount(); ++i) {
			if (Controllers.getController(i).getName().equals("Controller (XBOX 360 For Windows)")) {
				Gamepad gamepad = new Gamepad(container, i);
				gamepads.add(gamepad);
				devices.add(gamepad);
			}
		}
	}

	public void update (int delta) {
		for (InputDevice device : devices) {
			device.update();
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

	/** TODO - Methode um alle verfügbaren InputDevices abzufragen - Maussupport implementieren - Gamepadsupport implementieren */

	/*
	 * head -> node next -> node next -> node
	 */
}
