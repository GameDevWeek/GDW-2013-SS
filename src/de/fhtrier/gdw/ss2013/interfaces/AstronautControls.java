package de.fhtrier.gdw.ss2013.interfaces;

import org.newdawn.slick.Input;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public interface AstronautControls {

    public int forwardKey = Input.KEY_D;
    public int backwardKey = Input.KEY_A;
    public int jumpKey = Input.KEY_SPACE;
    public int actionKey = Input.KEY_E;

    // moves the character forward
    public void moveForward(int key);

    // moves the character backward
    public void moveBackward(int key);

    // jump?!
    public void jump(int key);

    // action key
    public void action(int key);

    // public void throwAlien();

    // public void placeReplace();

}
