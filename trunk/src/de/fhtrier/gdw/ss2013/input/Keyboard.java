
package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.GameContainer;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public class Keyboard extends InputDevice {

	public Keyboard (GameContainer gc) {
		super(gc);
		loadKeymapping(DEVICE_TYPE.KEYBOARD);
	}

	@Override
	public void update () {
		super.update();
	}



}
