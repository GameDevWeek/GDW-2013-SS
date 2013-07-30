package de.fhtrier.gdw.ss2013.input;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
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
    private LinkedList<Gamepad> gamepads = new LinkedList<>();
    private Keyboard keyboard;
    private Mouse mouse;

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

    private InputManager(GameContainer container) {
        this.container = container;

        keyboard = new Keyboard(container);
        devices.add(keyboard);
        mouse = new Mouse(container);
        devices.add(mouse);

        for (int i = 0; i < container.getInput().getControllerCount(); ++i) {
            Gamepad gamepad = new Gamepad(container, i);
            gamepads.add(gamepad);
            devices.add(gamepad);
            Log.debug("gamepad found");
        }
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

    /**
     * TODO - Methode um alle verfügbaren InputDevices abzufragen - Maussupport
     * implementieren - Gamepadsupport implementieren
     */

    /*
     * head -> node next -> node next -> node
     */
}
