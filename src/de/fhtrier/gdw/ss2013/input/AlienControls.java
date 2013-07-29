package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.Input;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public interface AlienControls {

	public int shootButton = Input.MOUSE_LEFT_BUTTON;
	public int rotateWheel = 0; // /???????????????????????????????
	public int abilityButton = Input.MOUSE_RIGHT_BUTTON;

	public void shoot(int button);

	public void rotateAbilities(int value);

	public void useAbility(int button);

}
