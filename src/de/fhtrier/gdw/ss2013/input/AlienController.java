package de.fhtrier.gdw.ss2013.input;

import org.newdawn.slick.Input;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public interface AlienController {

	public void shoot();

	public void nextAbility();
	
	public void previousAbility();

	public void useAbility();
	
	public void setCursor(int x , int y);

    public void cursorLeft();

    public void cursorRight();

    public void cursorUp();

    public void cursorDown();

}
