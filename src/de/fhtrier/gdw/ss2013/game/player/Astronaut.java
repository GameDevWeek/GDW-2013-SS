package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;

public class Astronaut extends Player {

	private float oxygen;
	private float maxOxygen;
	Animation bewegungs_ani;
    private Animation animation;
    
    private AssetLoader asset = AssetLoader.getInstance();
    
	public Astronaut(Vector2f position) {
		super(position);

		// Default
		maxOxygen = 0.0f;
		oxygen = maxOxygen;
		bewegungs_ani = asset.getAnimation(getZustand());

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

	
  // @Override
   // public void moveForward(int key) {
        // TODO Auto-generated method stub
     //   super.moveForward(key);
   // }

    //@Override
   // public void moveBackward(int key) {
        // TODO Auto-generated method stub
     //   super.moveBackward(key);
   // }

   // @Override
   // public void jump(int key) {
        // TODO Auto-generated method stub
     //   super.jump(key);
   // }

   

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        // TODO Auto-generated method stub
        bewegungs_ani.draw(position.x, position.y);
        
    }
	
	

}
