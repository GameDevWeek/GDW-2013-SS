package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.Input;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public interface AstronautController {

	// moves the character forward
	public void moveRight();

	// moves the character backward
	public void moveLeft();

	// jump?!
	public void jump();

	// action key
	public void action();
	

	// public void throwAlien();

	// public void placeReplace();

}
