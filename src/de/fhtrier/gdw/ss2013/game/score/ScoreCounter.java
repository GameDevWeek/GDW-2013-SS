package de.fhtrier.gdw.ss2013.game.score;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class ScoreCounter {

	int score;
	Vector2f position = new Vector2f(300, 7);
	
	public void render(Graphics g) {
		g.setColor(Color.red);
        g.drawRect(position.x, position.y, 100, 22);
		g.drawString("Score: " + score, position.x + 2, position.y + 3);
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void reset() {
		score = 0;
	}
	
	public int getScore(){
	    return score;
	}
	
	public void setScore(int score){
	    this.score = score;
	}
}
