/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class AbilitySelection {

	private Image[] ability;
	private Vector2f position; // ist hier jeweils der Abstand den die Position
	private int selected;
	private Color key;
	private World worldinstance;

	public AbilitySelection() {

	}

	public void init(Image ability1, Image ability2, Image ability3,
			Vector2f position, World worldinstance) {

		ability = new Image[3];

		ability[0] = ability1;
		ability[1] = ability2;
		ability[2] = ability3;

		this.position = position.copy();

		key = new Color(255f, 255f, 255f, 0.5f);
		
		this.worldinstance = worldinstance;
		selected = worldinstance.getAlien().getselectedAbility();
	}	
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
	    selected = worldinstance.getAlien().getselectedAbility();
	}	
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {

		if (selected != 1) {
			ability[0].draw(container.getWidth()
							- (position.x + ability[0].getWidth()), position.y,
							key);
		} else {
			ability[0].draw(container.getWidth()
							- (position.x + ability[0].getWidth()), position.y);
		}

		if (selected != 2) {
			ability[1].draw(container.getWidth()
							- (position.x + ability[0].getWidth()), position.y
							+ ability[0].getHeight() + 20, key);
		} else {
			ability[1].draw(container.getWidth()
							- (position.x + ability[0].getWidth()), position.y
							+ ability[0].getHeight() + 20);
		}

		if (selected != 3) {
			ability[2].draw(container.getWidth()
							- (position.x + ability[0].getWidth()),
							position.y + ability[0].getHeight() + 20
									+ ability[1].getHeight() + 20, key);
		} else {
			ability[2].draw(container.getWidth()
							- (position.x + ability[0].getWidth()),
							position.y + ability[0].getHeight() + 20
									+ ability[1].getHeight() + 20);
		}

	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

}
