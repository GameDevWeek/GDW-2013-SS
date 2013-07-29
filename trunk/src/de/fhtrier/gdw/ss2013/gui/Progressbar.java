/**
 * Boris, David (UI-Team)
 */

package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Progressbar {
	private Vector2f position;
	private Vector2f size;

	private Image frame;
	private Image background;
	private Image bar;

	private int cornerradius = 5;

	// Werte für konkreten Balken
	private float maxValue = 2000; // gibt den Maximalwert an
	private float currentValue = 1000; // gibt den aktuellen Wert an
	private float currentPercentValue; // currentValue in Prozent in Bezug auf
										// maxValue
	private float filled; // gibt an wie weit der Balken gefüllt ist

	private Image progress;

	public void init(Vector2f position, Vector2f size, int cornerradius,
			Image frame, Image background, Image bar) {
		this.position = position.copy();
		this.size = size.copy();
		this.cornerradius = cornerradius;

		this.frame = frame;
		this.background = background;
		this.bar = bar;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		// currentValue auslesen.
		currentPercentValue = (currentValue / maxValue) * 100;

		filled = size.x * (currentPercentValue / 100);

	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {

		// frame.draw(position.x, position.y);
		background.draw(position.x, position.y);
		bar.draw(position.x, position.y, position.x + filled, position.y
				+ size.y, 0, 0, size.x - filled, size.y);

	}

}
