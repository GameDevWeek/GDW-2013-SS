package de.fhtrier.gdw.ss2013.game.world.enemies;

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
//	private Entity huntedPlayer;
	private Vector2f speed;
//	private boolean isInLevel;
//	private PhysixObject collidingLevelObject;

	public GroundEnemy(Animation animation) {
		super(animation);
		speed = new Vector2f(EnemyConstants.ENEMY_SPEED, 0);
		state = GroundEnemyState.patrol;
		setDamage(EnemyConstants.GROUND_DAMAGE);
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
			patrol();
			break;
		case huntPlayer:
			hunt();
			break;
		}
//		System.out.println("Speed: " + speed + " | State: " + state + " | stuck: " + (isInLevel) + " | pos: "+getPosition());
	}

	private void hunt() {
//		Vector2f playerDirection = getPlayerDirection(huntedPlayer);
//		if (playerDirection.y > EnemyConstants.ENEMY_MAX_HEIGHT_DIFFERENCE
//				|| getDistanceToPlayer(huntedPlayer) > EnemyConstants.ENEMY_MAX_RANGE) {
//			state = GroundEnemyState.patrol;
//			return;
//		}
//
//		playerDirection.y = 0;
//		setVelocity(playerDirection.normalise().scale(EnemyConstants.ENEMY_SPEED).copy());
	}

	private void patrol() {
//		World world = World.getInstance();
		
//		if (isInLevel) {
//			float myX = getPosition().x;
//			float levelX = collidingLevelObject.getPosition().x;
//			if (myX > levelX) {
//				speed.x = EnemyConstants.ENEMY_SPEED;
//			}
//			else {
//				speed.x = -EnemyConstants.ENEMY_SPEED;
//			}
//		}

//		if (!world.getAstronaut().isCarryAlien()
//				&& getDistanceToPlayer(world.getAlien()) < EnemyConstants.ENEMY_MAX_RANGE
//				&& !isPlayerTooHigh(world.getAlien())) {
//			state = GroundEnemyState.huntPlayer;
//			huntedPlayer = world.getAlien();
//			return;
//		}
//
//		if (getDistanceToPlayer(world.getAstronaut()) < EnemyConstants.ENEMY_MAX_RANGE
//				&& !isPlayerTooHigh(world.getAlien())) {
//			state = GroundEnemyState.huntPlayer;
//			huntedPlayer = world.getAstronaut();
//			return;
//		}

		setVelocity(speed.copy());
	}

//	private boolean isPlayerTooHigh(Entity e) {
//		Vector2f playerDirection = getPlayerDirection(e);
//		return (playerDirection.y > EnemyConstants.ENEMY_MAX_HEIGHT_DIFFERENCE);
//	}

//	private Vector2f getPlayerDirection(Entity e) {
//		return e.getPosition().sub(getPosition());
//	}

//	private float getDistanceToPlayer(Entity e) {
//		return getPlayerDirection(e).length();
//	}

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
//			isInLevel = true;
			speed.x = -speed.x;
		}

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
		Entity other = getOtherEntity(contact);
		if (other == null) {
//			isInLevel = false;
//			collidingLevelObject = null;
		}
	}
}
