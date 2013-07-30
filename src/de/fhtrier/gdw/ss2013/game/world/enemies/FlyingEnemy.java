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
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

/**
 * Flying Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public class FlyingEnemy extends AbstractEnemy implements ICollidable {

	private float health, flytime, bolttime;
	private float flyintelligence, boltintelligence;
	private EntityManager m;
	private Player p;
	final static float DEBUG_ENTITY_HALFEXTEND = 5;

	public FlyingEnemy(Vector2f pos, Vector2f velo, float dmg, float hp) {
		super(pos.copy(), velo.copy(), dmg);
		health = hp;
		flyintelligence = boltintelligence = (float)Math.random();
	    if(flyintelligence > 0.5f) {
	        this.getVelocity().y = 0.0f;
	    } else {
	        this.getVelocity().x = 0.0f;
	    }
	}

	public FlyingEnemy() {
		this(new Vector2f(), new Vector2f(), 0, 0);
	}

	public FlyingEnemy(Vector2f pos) {
		this(pos.copy(), new Vector2f(2.5f, 2.5f), 0, 0);
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

	public void shoot(Player player, EntityManager m) {
		EnemyBullet b = (EnemyBullet) m.createEntityAt(EnemyBullet.class,
				this.position.copy());
		b.getVelocity().x = 5 * calcPlayerDirection(player).x;
		b.getVelocity().y = 5 * calcPlayerDirection(player).y;
		b.setDamage(this.getDamage());
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(Color.blue);
		g.drawRect(position.x - DEBUG_ENTITY_HALFEXTEND, position.y
				- DEBUG_ENTITY_HALFEXTEND, DEBUG_ENTITY_HALFEXTEND * 2,
				DEBUG_ENTITY_HALFEXTEND * 2);
		// g.drawString(this.hashCode(), position.x, position.y);
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
		// float dt = delta / 1000.f;
		flytime += delta;
		bolttime += delta;
		// TODO clamp dt if dt > 1/60.f ?
		this.getPosition().x += this.getVelocity().x;
		this.getPosition().y += this.getVelocity().y;
		if (flytime >= 3000) {
			this.getVelocity().x = -this.getVelocity().x;
			this.getVelocity().y = -this.getVelocity().y;
			flytime = flytime % 3000;
		}
	    if (bolttime >= 2000) {
	        this.shoot(p, m);
	        bolttime = bolttime % 2000;
	    }
	}

	private Vector2f calcPlayerDirection(Player player) {

		Vector2f direction = new Vector2f();

		direction.x = player.getPosition().x - this.position.x;
		direction.y = player.getPosition().y - this.position.y;

		direction.normalise();

		return direction;
	}

	@Override
	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setReferences(EntityManager m, Player p) {
	    this.m = m;
	    this.p = p;
	}
}
