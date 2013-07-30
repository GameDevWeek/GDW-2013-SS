package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.Input;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public interface AlienController {

	public void shoot();

	public void rotateAbilitiesUp();
	
	public void rotateAbilitiesDown();

	public void useAbility();
	
	public void targetMouse(int x , int y);

}
