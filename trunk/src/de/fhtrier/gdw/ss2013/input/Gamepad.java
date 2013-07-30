package de.fhtrier.gdw.ss2013.input;

import java.util.HashMap;
import java.util.HashSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import de.fhtrier.gdw.ss2013.input.InputDevice.ACTION;

public class Gamepad extends InputDevice {

    private HashMap<String, Integer> buttons = new HashMap<>();
    org.lwjgl.input.Controller controller;
    
    public Gamepad(GameContainer gc, int index) {
        super(gc);
        controller = org.lwjgl.input.Controllers.getController(index);
        // über alle buttons laufen Controller.getbuttonCount
        // name davon speichern
        // 
    }

    @Override
    public void update() {

        HashSet<ACTION> actions = new HashSet<>(keymapping.values());
        for (int key : keymapping.keySet()) {
            if ( controller.isButtonPressed(key)  ) {
                ACTION action = keymapping.get(key);
                if (actions.contains(action)) {
                    actions.remove(action);
                    doAction(action);
                }
            }
            
            
        }
    }

    private void doAction(ACTION action) {
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
    
   // SHOOT, TARGET, ROTATEABILITY, USEABILITY

    @Override
    public void loadKeymapping() {
        /*
        keymapping.put(buttons.get("button_X"), ACTION.MOVEFORWARD);

        keymapping.put(buttons.get("button_X"), ACTION.MOVEBACKWARD);

        keymapping.put(buttons.get("button_X"), ACTION.JUMP);

        keymapping.put(buttons.get("button_Y"), ACTION.ACTION);
        */
    }

}
