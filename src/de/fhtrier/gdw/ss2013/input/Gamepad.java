package de.fhtrier.gdw.ss2013.input;

import java.util.HashMap;
import java.util.HashSet;

import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.input.InputDevice.ACTION;

public class Gamepad extends InputDevice{

    private HashMap<String, Integer> buttons = new HashMap<>();
    org.lwjgl.input.Controller controller;
    

    public Gamepad(GameContainer gc, int index) {
        super(gc);
        controller = org.lwjgl.input.Controllers.getController(index);
        // über alle buttons laufen Controller.getbuttonCount
        // name davon speichern
        //
        Log.debug("NAME " + controller.getName());
    }

    @Override
    public void update() {

        HashSet<ACTION> actions = new HashSet<>(keymapping.values());
        
        for (int key : keymapping.keySet()) {
            if (controller.isButtonPressed(key)) {
                ACTION action = keymapping.get(key);
                if (actions.contains(action)) {
                    actions.remove(action);
                    doAction(action);
                }
            }
            if (controller.getPovX() < 0.0f) {
                doAction(ACTION.MOVEBACKWARD);
            }

            if (controller.getPovX() > 0.0f) {
                doAction(ACTION.MOVEFORWARD);
            }
            
        }
    }

    @Override
    public void loadKeymapping() {

        keymapping.put(0, ACTION.JUMP); // A (1)

        keymapping.put(1, ACTION.ACTION); // B (2)
        
        keymapping.put(2, ACTION.SHOOT); // RB (6)
        
        keymapping.put(3, ACTION.USEABILITY); //  (7)
        
        keymapping.put(4, ACTION.ROTATEABILITY_UP); //  (8)

    }


}
