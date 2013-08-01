/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class AbilityDisplayManager {

	private StaticAbilityDisplay[] abilityDisplay;
	private int selected;
	private World worldinstance;

	public AbilityDisplayManager() {
	}

	public void init(StaticAbilityDisplay[] abilityDisplay,
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
	    
	    for(StaticAbilityDisplay elem : abilityDisplay)
        {
            elem.update(container, game, delta);
        }
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
	    for(StaticAbilityDisplay elem : abilityDisplay)
	    {
	        elem.render(container, game, g);
	    }
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

}
