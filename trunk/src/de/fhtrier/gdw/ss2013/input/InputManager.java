package de.fhtrier.gdw.ss2013.input;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.util.Log;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public class InputManager {

    private GameContainer container;
    private int delta;
    private LinkedList<InputDevice> devices = new LinkedList<>();

    private static InputManager instance;
    
    public static void init(GameContainer container){
        if(instance == null ){
            instance = new InputManager(container);
        }
        
    }
    
    public static InputManager getInstance()  {
        if(instance == null){
            Log.error("InputManager nicht initialisiert!");
        }
        return instance;
    }
    
    
    private InputManager(GameContainer container) {
        this.container = container;
        devices.add(new Keyboard(container));
    }

    public void update(int delta) {
        for (InputDevice device : devices) {
            device.update();
        }
    }

    private LinkedList<InputDevice> getInputDevices() {
        return devices;
    }

    private InputDevice getInputDeviceByType(Class<InputDevice> clazz) {
//        for (InputDevice device : devices) {
//            if (device instanceof clazz)
//                return device;
//        }
       return null;
    }

    public InputDevice getKeyboard() {
        for (InputDevice device : devices) {
            if (device instanceof Keyboard)
                return device;
        }
        return null;

    }

    public InputDevice getMouse() {

        for (InputDevice device : devices) {
            if (device instanceof Mouse)
                return device;
        }
        return null;
    }

    public InputDevice getGamepads() {

        for (InputDevice device : devices) {
            if (device instanceof Gamepad)
                return device;
        }
        return null;

    }

    /**
     * TODO - Methode um alle verfügbaren InputDevices abzufragen - Maussupport
     * implementieren - Gamepadsupport implementieren
     * 
     */
    
    /*
     * head ->   node
     *           next ->    node
     *                      next ->   node
     * 
     * 
     */
}
