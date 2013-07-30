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
    
    
	public Astronaut(Vector2f position) {
		super(position);

		// Default
		maxOxygen = 1000f;
		oxygen = maxOxygen;
		bewegungs_ani = AssetLoader.getInstance().getAnimation(getZustand());
		this.getVelocity().x = 200000;
		this.getVelocity().y = 200000;

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
			this.oxygen -= (this.maxOxygen * PlayerConstants.OXYGEN_PERCENTAGE_LOST_PER_SECOND)
					* (delta / 1000f);
    }
        
    @Override
    public void moveForward() {
        physicsObject.applyImpulse(new Vec2(this.getVelocity().x,0));
        setZustand("animtest");
    }

    @Override
    public void moveBackward() {
        
        physicsObject.applyImpulse(new Vec2(-this.getVelocity().x,0));
        
        setZustand("animtest");
    }

    @Override
    public void jump() {
        physicsObject.applyImpulse(new Vec2(0,-this.getVelocity().x));
        setZustand("animtest");
    }

    @Override
    public void action() {
        physicsObject.applyImpulse(new Vec2(0,this.getVelocity().x));
        setZustand("animtest");
    }

    public boolean isCarryAlien() {
        return carryAlien;
    }

    public void setCarryAlien(boolean carryAlien) {
        this.carryAlien = carryAlien;
    }
	
	

}
