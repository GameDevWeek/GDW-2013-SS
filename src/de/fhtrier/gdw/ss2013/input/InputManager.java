package de.fhtrier.gdw.ss2013.input;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public class InputManager {

	private GameContainer container;
	private int delta;
	private LinkedList<InputDevice> devices = new LinkedList<>();

	public InputManager(GameContainer container) {
		this.container = container;
		devices.add(new Keyboard(container));

		// input devices von slick

	}

	public void update(int delta) {
		for (InputDevice device : devices) {
			device.update();
		}

	}

	// setter fuer controls
}
