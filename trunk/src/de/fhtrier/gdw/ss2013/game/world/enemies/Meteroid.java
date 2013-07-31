package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

/**
 * Meteroid class
 * 
 * @author Kevin, Georg
 * 
 */
public class Meteroid extends AbstractEnemy implements ICollidable {

    private EntityManager m;
	private float health;
	final static float DEBUG_ENTITY_HALFEXTEND = 5;

	public Meteroid() {
	}
    
    public void setHealth(float hp) {
		health = hp;
    }

	@Override
	public void onCollision(Entity e) {
		if (e instanceof Astronaut) {
			((Astronaut) e).setOxygen(((Astronaut) e).getOxygen()
					- this.getDamage());
		}
	}

	public void reduceHealth(float dmg) {
		health -= dmg;
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(Color.magenta);
        Vector2f position = getPosition();
		g.drawRect(position.x - DEBUG_ENTITY_HALFEXTEND, position.y
				- DEBUG_ENTITY_HALFEXTEND, DEBUG_ENTITY_HALFEXTEND * 2,
				DEBUG_ENTITY_HALFEXTEND * 2);
		
		// g.drawString(this.hashCode(), position.x, position.y);
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
		float dt = delta / 1000.f;
		// TODO clamp dt if dt > 1/60.f ?
		this.getPosition().x += this.getVelocity().x * dt;
		this.getPosition().y += this.getVelocity().y * dt;
	    if (health <= 0) {
	         m.removeEntity(this);
	    }
	}

	@Override
	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setReferences(EntityManager m) {
	    this.m = m;
	}
}
