package de.fhtrier.gdw.ss2013.game.world.enemies;

import java.util.ArrayList;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.constants.EnemyConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import org.jbox2d.dynamics.Fixture;


/**
 * Ground Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class GroundEnemy extends AbstractEnemy {
	private enum GroundEnemyState {
		patrol, huntPlayer
	}

	private GroundEnemyState state;
	private Vector2f speed;
	private ArrayList<Contact> contacts;
	private Entity huntedPlayer;
	private boolean isInLevel;
	private int chillTime;

	public GroundEnemy(Animation animation) {
		super(animation);
		speed = new Vector2f(EnemyConstants.ENEMY_SPEED, 0);
		state = GroundEnemyState.patrol;
		setDamage(EnemyConstants.GROUND_DAMAGE);
		
		contacts = new ArrayList<>();
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);

		switch (state) {
		case patrol:
			patrol(delta);
			break;
		case huntPlayer:
			hunt(delta);
			break;
		}
	}

	private void hunt(int delta) {
		Vector2f playerDirection = getPlayerDirection(huntedPlayer);
		if (playerDirection.y > EnemyConstants.ENEMY_MAX_HEIGHT_DIFFERENCE
				|| getDistanceToPlayer(huntedPlayer) > EnemyConstants.ENEMY_MAX_RANGE) {
			state = GroundEnemyState.patrol;
			return;
		}

		playerDirection.y = 0;
		setVelocity(playerDirection.normalise().scale(EnemyConstants.ENEMY_SPEED).copy());
	}

	private void patrol(int delta) {
		World world = World.getInstance();

		if (!world.getAstronaut().isCarryAlien()
				&& getDistanceToPlayer(world.getAlien()) < EnemyConstants.ENEMY_MAX_RANGE
				&& !isPlayerTooHigh(world.getAlien())) {
			state = GroundEnemyState.huntPlayer;
			huntedPlayer = world.getAlien();
			return;
		}

		if (getDistanceToPlayer(world.getAstronaut()) < EnemyConstants.ENEMY_MAX_RANGE
				&& !isPlayerTooHigh(world.getAlien())) {
			state = GroundEnemyState.huntPlayer;
			huntedPlayer = world.getAstronaut();
			return;
		}
		
		if (chillTime <= 0) {
			if (Math.random() < 0.15f * (delta / 1000f)) {
				chillTime = (int) ((Math.random() * 2000f) + 1000); 
			}	
			setVelocity(speed.copy());
		}
		else {
			chillTime -= delta;
			if (chillTime < 0) chillTime = 0;
		}
//		for (Contact c : contacts) {
//			
//		}

	}

	private boolean isPlayerTooHigh(Entity e) {
		Vector2f playerDirection = getPlayerDirection(e);
		return (playerDirection.y > EnemyConstants.ENEMY_MAX_HEIGHT_DIFFERENCE);
	}

	private Vector2f getPlayerDirection(Entity e) {
		return e.getPosition().sub(getPosition());
	}

	private float getDistanceToPlayer(Entity e) {
		return getPlayerDirection(e).length();
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
        if(a.m_isSensor || b.m_isSensor)
            return;
        
		Entity other = getOtherEntity(contact);

		if (other != null
				&& (other.getPhysicsObject().getBodyType() == BodyType.DYNAMIC && !(other instanceof Alien) && !(other instanceof Astronaut))) {
			if (other.getPosition().x < getPosition().x) {
				speed.x = Math.abs(speed.x);
			}
			else {
				speed.x = -Math.abs(speed.x);
			}
            if (other instanceof Astronaut) {
                ((Astronaut) other).setOxygen(((Astronaut) other).getOxygen() - this.getDamage());
            }
		}
        else {
			isInLevel = true;
			speed.x = -speed.x;
			
			getPhysicsObject().setPosition(getPosition().x-speed.x, getPosition().y);
		}
		
		contacts.add(contact);

//		Fixture a = contact.getFixtureA();
//		Fixture b = contact.getFixtureB();
//
//		Body objectA = a.getBody();
//		Body objectB = b.getBody();
//
//		PhysixObject ap = (PhysixObject) objectA.getUserData();
//		PhysixObject bp = (PhysixObject) objectB.getUserData();
//
//		if (ap == null || bp == null) // filter level<>level,
//										// non-level<>non-level collision
//			return;
//
//		if (ap.getOwner() == null) // ap is level
//		{
//			collidingLevelObject = ap;
//		}
//		else if (bp.getOwner() == null) { // bp is level
//			collidingLevelObject = bp;
//		}
	}

	@Override
	public void endContact(Contact contact) {
		if (getOtherEntity(contact) == null) {
			isInLevel = false;
		}
		contacts.remove(contact);
	}
}
