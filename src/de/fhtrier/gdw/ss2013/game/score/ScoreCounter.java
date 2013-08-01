package de.fhtrier.gdw.ss2013.game.score;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ScoreCounter {

	int score;
	
	public void render(Graphics g) {
		g.setColor(Color.red);
        g.drawRect(300, 7, 100, 22);
		g.drawString("Score: " + score, 302, 10);
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void reset() {
		score = 0;
	}
}
