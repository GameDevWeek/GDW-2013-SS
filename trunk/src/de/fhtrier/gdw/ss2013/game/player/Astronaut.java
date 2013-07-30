package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.input.AstronautController;
import de.fhtrier.gdw.ss2013.input.InputManager;

public class Astronaut extends Player implements AstronautController {

	private float oxygen;
	private float maxOxygen;
	private boolean carryAlien=true;
	Animation bewegungs_ani;
    
    
	public Astronaut(Vector2f position) {
		super(position);

		// Default
		maxOxygen = 1000f;
		oxygen = maxOxygen;
		bewegungs_ani = assetloader.getAnimation(getZustand());
		this.getVelocity().x = 10;
		this.getVelocity().y = 10;

	}

	public float getOxygen() {
		return oxygen;
	}

	public void setOxygen(float oxygen) {
		this.oxygen = oxygen;
	}

	public float getMaxOxygen() {
		return maxOxygen;
	}

	public void setMaxOxygen(float maxOxygen) {
		this.maxOxygen = maxOxygen;
	}

  
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        if (oxygen > 0) 
            this.oxygen -= (this.maxOxygen * PlayerConstants.OXYGEN_PERCENTAGE_LOST_PER_SECOND) * (delta / 1000f);
    }
        
    @Override
    public void moveForward() {
        this.getPosition().x += this.getVelocity().x;
        setZustand("animtest");
    }

    @Override
    public void moveBackward() {
        position.x -= this.getVelocity().x;
        setZustand("animtest");
    }

    @Override
    public void jump() {
    	position.y -= this.getVelocity().x;
        setZustand("animtest");
    }

    @Override
    public void action() {
    	position.y += this.getVelocity().x;
        setZustand("animtest");
    }

    public boolean isCarryAlien() {
        return carryAlien;
    }

    public void setCarryAlien(boolean carryAlien) {
        this.carryAlien = carryAlien;
    }
	
	

}
