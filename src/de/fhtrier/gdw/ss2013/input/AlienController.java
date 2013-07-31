package de.fhtrier.gdw.ss2013.input;

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

    public void cursorRight(float scale);

    public void cursorLeft(float scale);

    public void cursorUp(float scale);

    public void cursorDown(float scale);

}
