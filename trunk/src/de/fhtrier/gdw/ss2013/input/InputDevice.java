
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
	protected AlienController alienController = null;
	protected AstronautController astronautController = null;
	
	public InputDevice (GameContainer gc) {
		container = gc;
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

	protected void doAction(InputAction action,int x, int y){
		if (alienController != null) {
			alienController.setCursor(x,y);
			//Log.debug("Alien pointing at " + x +" " +y);
		}
	}
    
	public abstract void update ();

	public void setAlienController (AlienController ac) {
		alienController = ac;
	}

	public void setAstronautController (AstronautController ac) {
		astronautController = ac;
	}

}
