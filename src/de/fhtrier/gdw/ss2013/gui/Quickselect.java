package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class Quickselect {

	private Image[] ability;
	private int selected = 1;
	private int countdown_start = 500;
	private int countdown_timer = 0;
	private World worldinstance;

	public void init(Image ability1, Image ability2, Image ability3,
			int selected, int countdown_start, World worldinstance) {

		ability = new Image[3];

		ability[0] = ability1;
		ability[1] = ability2;
		ability[2] = ability3;

		this.selected = selected;
		this.countdown_start = countdown_start;
		
		this.worldinstance = worldinstance;

	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {

		if (countdown_timer > 0) {
			ability[selected - 1].draw(worldinstance.getAstronaut().getPosition().x + 43, worldinstance.getAstronaut().getPosition().y - 25);
		}

	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (countdown_timer > 0) {
			countdown_timer -= delta;
		}
		
		setSelected(worldinstance.getAlien().getselectedAbility());
	}

	public void setSelected(int selected) {
		if (this.selected != selected) {
			this.selected = selected;
			countdown_timer = countdown_start; // counter zurücksetzen

		}

	}

}
