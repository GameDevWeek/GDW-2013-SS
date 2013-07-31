/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;

/**
 * Player class
 */
public abstract class Player extends Entity {

	protected Animation animation;
	private String zustand = "testplayer";

	public Player(String zustand) {
		animation = AssetLoader.getInstance().getAnimation(zustand);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
        Vector2f position = getPosition();
		animation.draw(position.x - animation.getWidth() / 2,
				position.y - animation.getHeight() / 2);
	}

	public String getZustand() {
		return zustand;
	}

	public void setZustand(String zustand) {
		
		if(!(this.animation.equals(AssetLoader.getInstance().getAnimation(zustand))))
		{
		    this.zustand = zustand;
		    this.animation=AssetLoader.getInstance().getAnimation(zustand);
	        
		}
	}
	
	public Animation getAnimation(){
	    return animation;
	}
	
	public void die() {
	    
	}

}
