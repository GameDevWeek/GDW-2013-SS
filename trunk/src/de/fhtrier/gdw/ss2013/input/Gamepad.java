package de.fhtrier.gdw.ss2013.input;

import de.fhtrier.gdw.ss2013.assetloader.infos.ControlsInfo;
import java.util.HashMap;
import org.newdawn.slick.GameContainer;

public class Gamepad extends InputDevice {

    private HashMap<String, InputAction> actionMap = new HashMap<>();
    org.lwjgl.input.Controller controller;

    public Gamepad(GameContainer gc, ControlsInfo.GamepadInfo gamepadInfo) {
        super(gc);

        String name = gamepadInfo.name;
        int numControllers = org.lwjgl.input.Controllers.getControllerCount();
        for (int i = 0; i < numControllers; i++) {
            org.lwjgl.input.Controller c = org.lwjgl.input.Controllers.getController(i);
            if (c.getName().equalsIgnoreCase(name)) {
                controller = c;
                break;
            }
        }

        if (controller == null) {
            throw new IllegalArgumentException("Controller config not found: " + name);
        }

        if (gamepadInfo.astronaut != null) {

//                setKey(InputAction.MOVE_LEFT, gamepadInfo.astronaut.MOVE_LEFT);
//                setKey(InputAction.MOVE_RIGHT, gamepadInfo.astronaut.MOVE_RIGHT);
            actionMap.put(gamepadInfo.astronaut.JUMP, InputAction.JUMP);
            actionMap.put(gamepadInfo.astronaut.ACTION, InputAction.ACTION);
        }

        if (gamepadInfo.alien != null) {
//            public String CURSOR_LEFTRIGHT;
//            public String CURSOR_UPDOWN;
            actionMap.put(gamepadInfo.alien.SHOOT, InputAction.SHOOT);
            actionMap.put(gamepadInfo.alien.USE_ABILITY, InputAction.USE_ABILITY);
            actionMap.put(gamepadInfo.alien.NEXT_ABILITY, InputAction.NEXT_ABILITY);
            actionMap.put(gamepadInfo.alien.PREV_ABILITY, InputAction.PREV_ABILITY);
        }
    }

    @Override
    public void update() {
        if (controller.getPovX() > 0) {
            doButton("+POV_X");
        } else if (controller.getPovX() < 0) {
            doButton("-POV_X");
        }

        if (controller.getPovY() > 0) {
            doButton("+POV_Y");
        } else if (controller.getPovY() < 0) {
            doButton("-POV_Y");
        }

        int numAxes = controller.getAxisCount();
        for (int i = 0; i < numAxes; i++) {
            float value = controller.getAxisValue(i);
            if (value > 0.2f) {
                doButton("+" + controller.getAxisName(i));
            } else if (value < 0.2f) {
                doButton("+" + controller.getAxisName(i));
            }
        }
    }

    public void doButton(String button) {
        
    }

    public void doAxis(String axis, float value) {
        InputAction action = actionMap.get(axis);
        if (action != null) {
            doAction(action);
        }
    }
}
