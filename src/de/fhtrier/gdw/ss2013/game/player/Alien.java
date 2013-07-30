package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.input.AlienController;

public class Alien extends Player implements AlienController{

    private int selectedAbility;
	private float mana;
	private float maxMana;
	private Animation  bewegungs_ani;
	
	public Alien(Vector2f position) {
		super(position);
	
		// Default
		selectedAbility = 1;
		maxMana = 0.0f;
		mana = maxMana;
		bewegungs_ani=asset.getAnimation(getZustand());
	}

	public float getMana() {
		return mana;
	}

	public void setMana(float mana) {
		this.mana = mana;
	}

	public float getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(float maxMana) {
		this.maxMana = maxMana;
	}

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
       bewegungs_ani.draw(position.x, position.y+50); // zum testen
    }

    @Override
    public void shoot() {
        Log.debug("shooting");
        setZustand("animtest");
        
    }

    @Override
    public void rotateAbilitiesUp() {
        selectedAbility = (selectedAbility % 3) + 1;
        
        Log.debug("rotate ability");
        setZustand("animtest");
    }
    
    @Override
    public void rotateAbilitiesDown(){
        //yet to implement
    }
    
    public int getselectedAbility(){
        return selectedAbility;
    }

    @Override
    public void useAbility() {
        Log.debug("using ability");
        setZustand("animtest");
    }

   @Override
    public void targetMouse(int x, int y) {
        // TODO Auto-generated method stub
     //   Log.debug("target direction");
      //  setZustand("animtest");
    }


    
    

	
}
