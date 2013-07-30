
package de.fhtrier.gdw.ss2013.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.KeyInfo;
import de.fhtrier.gdw.ss2013.input.InputManager.ACTION;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public abstract class InputDevice {

	public static enum DEVICE_TYPE {
		MOUSE("mouse"),
		KEYBOARD("keyboard"),
		GAMEPAD("gamepad");
		
		private String deviceName;

		private DEVICE_TYPE(String deviceName) {
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

	protected HashMap<ACTION, HashSet<Integer>> keymapping;
	
	public InputDevice (GameContainer gc) {
		container = gc;
		keymapping = new HashMap<>();
	}

	protected void doAction (ACTION action) {
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
			case ROTATEABILITY_UP:
				alienController.rotateAbilitiesUp();
				break;
			case ROTATEABILITY_DOWN:
				alienController.rotateAbilitiesDown();
				break;
			case USEABILITY:
				alienController.useAbility();
				break;
			default:
				break;
			}
		}
	}

	protected void doAction(ACTION action,int x, int y){
		if (alienController != null) {
			alienController.targetMouse(x,y);
			//Log.debug("Alien pointing at " + x +" " +y);
		}
	}
	public void update () {
		// für alle Actions
		Set<Entry<ACTION, HashSet<Integer>>> entries = keymapping.entrySet();
		for (Entry<ACTION, HashSet<Integer>> entry : entries) {
			// checke jeden gemappte taste
			for (Integer key : entry.getValue()) {
				if (container.getInput().isKeyDown(key)) {
					// führe Aktion aus und breche check für diese Aktion ab
					doAction(entry.getKey());
					break;
				}
			}
		}
	}

	public void loadKeymapping (DEVICE_TYPE deviceType) {
		List<KeyInfo> mappings = AssetLoader.getInstance().getKeyList(deviceType);
		for (KeyInfo keyInfo : mappings) {
			ACTION action = ACTION.getAction(keyInfo.name);
			//derzeit nur 1 Taste pro Action
			HashSet<Integer> tmpSet = new HashSet<>();
			tmpSet.add(keyInfo.key);
			Log.debug("Loaded Keymapping: " + keyInfo.key +" nach " + keyInfo.key);
			keymapping.put(action, tmpSet);
		}
	}
		

	public void saveKeymapping (String device) {
		List<KeyInfo> keyInfo = new ArrayList<>();
		// für alle Actions
		Set<Entry<ACTION, HashSet<Integer>>> entries = keymapping.entrySet();
		for (Entry<ACTION, HashSet<Integer>> entry : entries) {
			// checke jede gemappte taste
			for (Integer key : entry.getValue()) {
				if (container.getInput().isKeyDown(key)) {
					KeyInfo info = new KeyInfo();
					info.key = key;
					info.name = entry.getKey().name;
				}
			}
		}

	}

	public void setKey (ACTION action, int key) {
		keymapping.get(action).add(key);
	}

	public HashSet<Integer> getKey (ACTION action) {
		return keymapping.get(action);
	}

	public void setAlienController (AlienController ac) {
		alienController = ac;
	}

	public void setAstronautController (AstronautController ac) {
		astronautController = ac;
	}

}
