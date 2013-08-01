package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.RecycleableEntity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;

/**
 * Meteroid class
 * 
 * @author Kevin, Georg
 * 
 */
public class Meteroid extends AbstractEnemy implements RecycleableEntity {

	private EntityManager m;
	final static float DEBUG_ENTITY_HALFEXTEND = 5;

	public Meteroid() {
		super(AssetLoader.getInstance().getAnimation("meteorite"));
		this.m = World.getInstance().getEntityManager();
	}

	public void update(GameContainer container, int delta) throws SlickException {
		float dt = delta / 1000.f;
		// TODO clamp dt if dt > 1/60.f ?
		this.getPosition().x += this.getVelocity().x * dt;
		this.getPosition().y += this.getVelocity().y * dt;

		if (getPosition().y > 5000) { // FIXME: rausnehmen, wenn Objekt aus dem
										// Level raus ist
			m.removeEntity(this);
		}
	}

	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setReferences(EntityManager m) {
		this.m = m;
	}

	@Override
	public void beginContact(Contact contact) {
		Entity other = getOtherEntity(contact);
		if (other instanceof Astronaut) {
			((Astronaut) other).setOxygen(((Astronaut) other).getOxygen() - this.getDamage());
		}
		
		World.getInstance().getEntityManager().removeEntity(this);
		
//		if (other == null) {
//			World.getInstance().getEntityManager().removeEntity(this); // doesnt work until dispose() works
//		}

//		Fixture a = contact.m_fixtureA;
//		Fixture b = contact.m_fixtureB;
//
//		if (!(a.getBody().getUserData() != null && a.getBody().getUserData() != null)) {
//			return;
//		}
//
//		PhysixObject oA = (PhysixObject) a.getBody().getUserData();
//		PhysixObject oB = (PhysixObject) b.getBody().getUserData();
//
//		if (oA.getOwner() != null ^ oB.getOwner() != null)
//			return;
//
//		if (oA.getOwner() != null) { // b is level
//			System.out.println("b is level");
//		}
//		else { // a is level
//			System.out.println("a is level");
//		}
	}

	@Override
	public void endContact(Contact object) {
	}
}
