package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.GameContainer;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public abstract class InputDevice {

	protected GameContainer container;
	protected AlienController alienController = null;
	protected AstronautController astronautController = null;

	// enum für actions
	enum ACTIONS {
		MOVEFORWARD, MOVEBACKWARD, JUMP, ACTION, SHOOT, TARGET, ROTATEABILITY, USEABILITY
	};

	// dynamsiche tastenzuweisungen
	// HashMap<K,V>

	public InputDevice(GameContainer gc) {
		container = gc;
	}

	public abstract void update();

	public void setAlienController(AlienController ac) {
		alienController = ac;
	}

	public void setAstronautController(AstronautController ac) {
		astronautController = ac;
	}

	/**
	 * TODO
	 * - Keymapping für alle Actions je nach InputDevice aus Config datei lesen und speichern
	 * - Keymapping dynamisch ändern können 
	 * - Getter für Keymapping
	 */
}
