package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.Input;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public interface AlienController {

	public void shoot();

	public void rotateAbilities();

	public void useAbility();
	
	public void targetMouse(int x , int y);

}
