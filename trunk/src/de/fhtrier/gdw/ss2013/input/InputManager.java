package de.fhtrier.gdw.ss2013.input;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.ControlsInfo;
import java.util.LinkedList;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.util.Log;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */
public class InputManager {

    private static InputManager instance;

    public static void init(GameContainer container) {
        if (instance == null) {
            instance = new InputManager(container);
        }
    }

    public static InputManager getInstance() {
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

    private InputManager(GameContainer container) {
        this.container = container;

        keyboard = new Keyboard(container);
        devices.add(keyboard);
        mouse = new Mouse(container);
        devices.add(mouse);
//        for (int i = 0; i < org.lwjgl.input.Controllers.getControllerCount(); i++) {
//      	  System.out.println(Controllers.getController(i).getName());
//      	  Controller c= Controllers.getController(i);
//      	  System.out.println();
//      	
//		}
        ControlsInfo controlsInfo = AssetLoader.getInstance().getControls();
        if (controlsInfo.gamepads != null) {
            for (ControlsInfo.GamepadInfo gamepadInfo : controlsInfo.gamepads) {
                try {
                    Gamepad gamepad = new Gamepad(container, gamepadInfo);
                    gamepads.add(gamepad);
                    devices.add(gamepad);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.toString());
                }
            }
        }
        printControllerInfo();
    }

    public void update(int delta) {
        for (InputDevice device : devices) {
            device.update();
        }
    }

    public Keyboard getKeyboard() {
        if (keyboard != null) {
            return keyboard;
        }
        Log.error("Keine Tastatur angeschlossen!");
        return null;

    }

    public Mouse getMouse() {

        if (mouse != null) {
            return mouse;
        }
        Log.error("Keine Maus angeschlossen!");
        return null;
    }

    public Gamepad getGamepad(int id) {
        if (gamepads.size() >= id) {
            return gamepads.get(id);
        }
        Log.error("Kein Gamepad mit ID " + id + " angeschlossen!");
        return null;

    }

    public int getGamepadCount() {
        return gamepads.size();
    }

    private void printControllerInfo(){
   		
   		int numControllers = org.lwjgl.input.Controllers.getControllerCount();
   		for (int i = 0; i < numControllers; i++) {
   			org.lwjgl.input.Controller c = org.lwjgl.input.Controllers.getController(i);
   			Log.debug(c.getName());
   			for (int j = 0; j < c.getButtonCount(); j++) {
   				Log.debug("Buttonindex: "+ j +" Name: " + c.getButtonName(j)  );
   			}
   			for (int j = 0; j < c.getAxisCount(); j++) {
   				Log.debug("Axisindex: "+ j +" Name: " + c.getAxisName(j)  );
   			}
   		}
   	}
}
