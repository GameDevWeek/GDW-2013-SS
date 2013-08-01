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

	private AbilityDisplay[] abilityDisplay;
	private int selected;
	private World worldinstance;

	public AbilitySelection() {
	}

	public void init(AbilityDisplay[] abilityDisplay,
            World worldinstance) 
	{
	    this.abilityDisplay = abilityDisplay;
       
        this.worldinstance = worldinstance;
        selected = worldinstance.getAlien().getselectedAbility();
        abilityDisplay[selected-1].setActivated(true);
    }
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
	    
	    if (selected != worldinstance.getAlien().getselectedAbility())
	    {
	      int selectedOld = selected;
	      selected = worldinstance.getAlien().getselectedAbility();
	      
	      abilityDisplay[selectedOld-1].setActivated(false);
	      abilityDisplay[selected-1].setActivated(true);
	    };
	    
	    for(AbilityDisplay elem : abilityDisplay)
        {
            elem.update(container, game, delta);
        }
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
	    for(AbilityDisplay elem : abilityDisplay)
	    {
	        elem.render(container, game, g);
	    }
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

}
