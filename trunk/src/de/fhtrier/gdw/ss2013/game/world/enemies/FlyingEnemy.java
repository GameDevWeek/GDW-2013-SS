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
	private float factor;
	private EntityManager m;
	private Player p;
	final static float DEBUG_ENTITY_HALFEXTEND = 5;
	private String rechts="animtest", links="animtest", current="animtest", oben="animtest", unten="animtest";

	public FlyingEnemy(Vector2f pos, Vector2f velo, float dmg, float hp) {
		super(pos.copy(), velo.copy(), dmg);
		health = hp;
		flyintelligence = boltintelligence = (float)Math.random();
	    if(flyintelligence > 0.5f) {
	        this.getVelocity().y = 0.0f;
	    } else {
	        this.getVelocity().x = 0.0f;
	    }
	    if (boltintelligence >= 0.67) {
	        factor = 100;
	    } else if (boltintelligence <= 0.33) {
	        factor = 0;
	    } else {
	        factor = 50;
	    }
	    setLeft_animation(links);
	    setRight_animation(rechts);
	    setCurrent(current);
	    setOben(oben);
	    setUnten(unten);
	}

	public FlyingEnemy() {
		this(new Vector2f(), new Vector2f(), 0, 0);
		setLeft_animation(links);
	    setRight_animation(rechts);
	    setCurrent(current);
	    setOben(oben);
        setUnten(unten);
	}

	public FlyingEnemy(Vector2f pos) {
		this(pos.copy(), new Vector2f(2.5f, 2.5f), 0, 0);
		setLeft_animation(links);
	       setRight_animation(rechts);
	        setCurrent(current);
	        setOben(oben);
            setUnten(unten);
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
		//g.setColor(Color.blue);
		//g.drawRect(position.x - DEBUG_ENTITY_HALFEXTEND, position.y
			//	- DEBUG_ENTITY_HALFEXTEND, DEBUG_ENTITY_HALFEXTEND * 2,
			//	DEBUG_ENTITY_HALFEXTEND * 2);
	    super.render(container, g);
	    
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
	    if (bolttime >= 1000 && calcPlayerDistance(p) < 500) {
	        this.shoot(p, m);
	        bolttime = bolttime % 1000;
	    }
	}

	private Vector2f calcPlayerDirection(Player player) {

		Vector2f direction = new Vector2f();
		direction = calcPlayerPosition(player);
		direction.normalise();
		return direction;
	}
	private float calcPlayerDistance(Player player) {
	    Vector2f direction = new Vector2f();
	    direction = calcPlayerPosition(player);
	    return (float) Math.sqrt((direction.x*direction.x)+(direction.y*direction.y));
	}
	private Vector2f calcPlayerPosition(Player player) {
        Vector2f direction = new Vector2f();
        direction.x = player.getPosition().x - this.position.x+((float)Math.random()*factor);
        direction.y = player.getPosition().y - this.position.y;
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
