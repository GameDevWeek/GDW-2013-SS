
package de.fhtrier.gdw.ss2013.input;

import java.util.HashMap;

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
	enum ACTION {
		MOVEFORWARD, MOVEBACKWARD, JUMP, ACTION, SHOOT, TARGET, ROTATEABILITY, USEABILITY
	};

	// dynamsiche tastenzuweisungen
	HashMap<Integer, ACTION> keymapping;

	public InputDevice (GameContainer gc) {
		container = gc;
		keymapping = new HashMap<>();
		loadKeymapping();
	}
	
    protected void doAction(ACTION action) {
        if (astronautController != null) {
            switch (action) {
            case MOVEFORWARD:
                astronautController.moveForward();
                break;
            case MOVEBACKWARD:
                astronautController.moveBackward();
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
            case ROTATEABILITY:
                alienController.rotateAbilities();
                break;
            case USEABILITY:
                alienController.useAbility();
                break;
            default:
                break;
            }
        }
    }

	public abstract void update ();

	public abstract void loadKeymapping ();

	/** TODO */
	public void saveKeymapping () {

	}

	/** TODO
	 * @param action
	 * @param key */
	public void setKey (ACTION action, int key) {

	}

	public int getKey (ACTION action) {
		return 0;
	}

	public void setAlienController (AlienController ac) {
		alienController = ac;
	}

	public void setAstronautController (AstronautController ac) {
		astronautController = ac;
	}

	/** TODO - Keymapping für alle Actions je nach InputDevice aus Config datei lesen und speichern // Actions zu den Keys -
	 * Keymapping dynamisch ändern können - Getter für Keymapping */
}
