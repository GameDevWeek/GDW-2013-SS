package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.Log;
import org.lwjgl.input.*;

public class Mouse extends InputDevice {

    private int wheel = 0;
    public Mouse(GameContainer gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update() {

        if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            alienController.shoot();
        }

        else if (container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            alienController.useAbility();
        }

        alienController.targetMouse(container.getInput().getMouseX(), container
                .getInput().getMouseY());

        if(wheel !=org.lwjgl.input.Mouse.getDWheel() ){
            alienController.rotateAbilities();
        }
        wheel = org.lwjgl.input.Mouse.getDWheel();
    }

    @Override
    public void loadKeymapping() {
        // TODO Auto-generated method stub
        
    }

}
