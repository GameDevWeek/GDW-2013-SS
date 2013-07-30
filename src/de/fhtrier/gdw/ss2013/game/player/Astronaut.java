package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.input.AstronautController;
import de.fhtrier.gdw.ss2013.input.InputManager;

public class Astronaut extends Player implements AstronautController {

	private float oxygen;
	private float maxOxygen;
	Animation bewegungs_ani;
    
    
    private AssetLoader asset = AssetLoader.getInstance();
    
	public Astronaut(Vector2f position) {
		super(position);

		// Default
		maxOxygen = 1000f;
		oxygen = maxOxygen;
		bewegungs_ani = asset.getAnimation(getZustand());
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
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        // TODO Auto-generated method stub
        bewegungs_ani.draw(position.x-bewegungs_ani.getWidth()/2, position.y-bewegungs_ani.getHeight()/2);
        
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
	
	

}
