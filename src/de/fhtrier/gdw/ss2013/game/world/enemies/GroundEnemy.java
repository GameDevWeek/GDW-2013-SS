package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.EnemyConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Ground Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class GroundEnemy extends AbstractEnemy {
	private enum GroundEnemyState {
		patrol
	}

	private GroundEnemyState state;
	private boolean walksRight;

	public GroundEnemy(Animation animation) {
		super(animation);
		state = GroundEnemyState.patrol;
		walksRight = true;
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
			//patrol();
			break;		
		}
	}

	private void patrol() {
		if (walksRight)
			setVelocityX(EnemyConstants.ENEMY_SPEED);
		else
			setVelocityX(-EnemyConstants.ENEMY_SPEED);
	}

	private Vector2f calcPlayerDirection(Astronaut player) {
		Vector2f direction = calcPlayerPosition(player);
		direction.normalise();
		return direction;
	}

	private float calcPlayerDistance(Astronaut player) {
		Vector2f direction = calcPlayerPosition(player);
		return (float) Math.sqrt((direction.x * direction.x) + (direction.y * direction.y));
	}

	private Vector2f calcPlayerPosition(Astronaut player) {
		Vector2f direction = new Vector2f();
		direction.x = player.getPosition().x - this.getPosition().x;
		direction.y = player.getPosition().y - this.getPosition().y;
		return direction;
	}

	@Override
	public void beginContact(Contact contact) {
		Entity other = getOtherEntity(contact);
		if (other == null || other.getPhysicsObject().getBodyType() == BodyType.DYNAMIC) {
			walksRight = !walksRight;
			return;
		}
		if (other instanceof Astronaut) {
			((Astronaut) other).setOxygen(((Astronaut) other).getOxygen() - this.getDamage());
		}
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();

		Body objectA = a.getBody();
		Body objectB = b.getBody();

		PhysixObject ap = (PhysixObject) objectA.getUserData();
		PhysixObject bp = (PhysixObject) objectB.getUserData();

		if (!(ap == null ^ bp == null)) // filter level<>level,
										// non-level<>non-level collision
			return;

		if (ap == null) // ap is level
		{
			bp.setLinearVelocity(bp.getLinearVelocity().scale(-1));
		}
		else { // bp is level
			ap.setLinearVelocity(bp.getLinearVelocity().scale(-1));
		}

	}

	@Override
	public void endContact(Contact object) {
	}
}
