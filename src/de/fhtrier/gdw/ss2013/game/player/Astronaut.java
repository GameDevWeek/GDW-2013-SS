package de.fhtrier.gdw.ss2013.game.player;

import org.jbox2d.common.Vec2;
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
    float maxSpeed = 26;
    float speed = 80;
    float jumpSpeed = 300;
    int jumpDelay = 0;
  
    
	public Astronaut() {

		// Default
	    
	    super("testplayer");
		maxOxygen = 1000f;
		oxygen = maxOxygen;
		
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
        jumpDelay -= delta;
        if (oxygen > 0)
			this.oxygen -= (this.maxOxygen * PlayerConstants.OXYGEN_PERCENTAGE_LOST_PER_SECOND)
					* (delta / 1000f);
    }
        
    @Override
    public void moveRight() {
        setVelocityX(speed);
        if(getZustand()!="animtest")
        setZustand("animtest");
       
    }

    @Override
    public void moveLeft() {
        setVelocityX(-speed);
        if(getZustand()!="animtest")
        setZustand("animtest");
        
    }

    @Override
    public void jump() {
        if(jumpDelay <= 0) {
            jumpDelay = 0;
            setVelocityY(-jumpSpeed);
            physicsObject.applyImpulse(new Vector2f(0, -jumpSpeed));
            setZustand("testplayer");
            jumpDelay = 500;
        }
    }

    @Override
    public void action() {
    	getVelocity().y = 2;
        physicsObject.applyImpulse(this.getVelocity());
        if(getZustand()!="animtest")
        setZustand("animtest");
    }

    public boolean isCarryAlien() {
        return carryAlien;
    }

    public void setCarryAlien(boolean carryAlien) {
        this.carryAlien = carryAlien;
    }
	
	

}
