
package de.fhtrier.gdw.ss2013.input;


import org.newdawn.slick.GameContainer;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public abstract class InputDevice {

	public static enum DeviceType {
		MOUSE("mouse"),
		KEYBOARD("keyboard"),
		GAMEPAD("gamepad");
		
		private String deviceName;

		private DeviceType(String deviceName) {
			this.deviceName = deviceName;
		}
		
		@Override
		public String toString() {
			return deviceName; 
		}
	};

	protected GameContainer container;
	protected InputManager manager;
	
	public InputDevice (GameContainer gc) {
		container = gc;
		manager = InputManager.getInstance();
	}

	public abstract void update ();



}
