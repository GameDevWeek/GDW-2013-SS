package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Player;

/**
 * Moving Platform class
 * 
 * @author Kevin, Georg
 * 
 */
public class MovingPlatform extends Entity {
	private Vector2f[] endpositions;

	public void init(Vector2f start, Vector2f end) {
		endpositions = new Vector2f[2];
		endpositions[0] = start;
		endpositions[1] = end;
	}

	public void onCollision(Entity e) {
		if (e instanceof Player || e instanceof Box) {
            Vector2f velocity = getVelocity();
			e.getPosition().x += velocity.x;
			e.getPosition().y += velocity.y;
		} else if (e instanceof MovingPlatform) {
		    reachEnd();
		}
	}

	private void reachEnd() {
        Vector2f velocity = getVelocity();
		velocity.negateLocal();
        setVelocity(velocity);
	}
	
    public void update(GameContainer container, int delta)
                throws SlickException {
        if (this.getPosition().equals(this.endpositions[0]) || this.getPosition().equals(this.endpositions[1])) {
            reachEnd();
        }
    }
}
