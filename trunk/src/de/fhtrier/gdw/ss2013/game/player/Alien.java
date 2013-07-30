package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.input.AlienController;

public class Alien extends Player implements AlienController{

	private float mana;
	private float maxMana;
	private Animation  bewegungs_ani;
	private AssetLoader asset=AssetLoader.getInstance();;
	public Alien(Vector2f position) {
		super(position);
	
		// Default
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
        // TODO Auto-generated method stub
       bewegungs_ani.draw(position.x, position.y+50); // zum testen
    }

    @Override
    public void shoot() {
        // TODO Auto-generated method stub
        Log.debug("shooting");
    }

    @Override
    public void rotateAbilities() {
        // TODO Auto-generated method stub
        Log.debug("rotate ability");
    }

    @Override
    public void useAbility() {
        // TODO Auto-generated method stub
        Log.debug("using ability");
    }

    @Override
    public void targetMouse() {
        // TODO Auto-generated method stub
        Log.debug("target direction");
    }

    
    

	
}
