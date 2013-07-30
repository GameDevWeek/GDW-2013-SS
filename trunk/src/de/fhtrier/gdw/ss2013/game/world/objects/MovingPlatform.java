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
	private Vector2f velocity;
	private Vector2f[] endpositions;

	public MovingPlatform(Vector2f pos, Vector2f velo, Vector2f start, Vector2f end) {
		super(pos.copy());
		velocity = velo;
		endpositions = new Vector2f[2];
		endpositions[0] = start;
		endpositions[1] = end;
	}

	public MovingPlatform() {
		this(new Vector2f(), new Vector2f(), new Vector2f(), new Vector2f());
	}

	public MovingPlatform(Vector2f pos) {
		this(pos.copy(), new Vector2f(), new Vector2f(), new Vector2f());
	}

	public void onCollision(Entity e) {
		if (e instanceof Player || e instanceof Box) {
			e.getPosition().x += velocity.x;
			e.getPosition().y += velocity.y;
		} else if (e instanceof MovingPlatform) {
		    reachEnd();
		}
	}

	private void reachEnd() {
		velocity.x = -velocity.x;
		velocity.y = -velocity.y;
	}
	
    public void update(GameContainer container, int delta)
                throws SlickException {

        // float dt = delta / 1000.f;
        // TODO clamp dt if dt > 1/60.f ?
        this.position.x += this.velocity.x;
        this.position.y += this.velocity.y;
        if (this.getPosition().equals(this.endpositions[0]) || this.getPosition().equals(this.endpositions[1])) {
            reachEnd();
        }
    }
}
