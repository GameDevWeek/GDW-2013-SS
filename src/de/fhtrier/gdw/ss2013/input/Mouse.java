package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.Log;
import org.lwjgl.input.*;

import de.fhtrier.gdw.ss2013.input.InputDevice.ACTION;

public class Mouse extends InputDevice {

    private int wheel = 0;
    public Mouse(GameContainer gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update() {

        if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            doAction(ACTION.SHOOT);
        }

        else if (container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            doAction(ACTION.USEABILITY);
        }

        alienController.targetMouse(container.getInput().getMouseX(), container
                .getInput().getMouseY());
        
       
        wheel = org.lwjgl.input.Mouse.getDWheel();
        
        if( wheel < 0 ){ 
            doAction(ACTION.ROTATEABILITY_UP);
        }else if (wheel > 0){
            doAction(ACTION.ROTATEABILITY_DOWN);
        }
        
        
        
    }

    @Override
    public void loadKeymapping() {
        // TODO Auto-generated method stub
        
    }

}
