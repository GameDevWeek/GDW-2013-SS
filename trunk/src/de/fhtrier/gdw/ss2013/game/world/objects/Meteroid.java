package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.EntityConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.spawner.MeteoriteSpawner;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;

/**
 * Meteroid class
 * 
 * @author Kevin, Georg
 * 
 * dont fix spelling mistake, is used too often byRD
 */
public class Meteroid extends EntityCollidable {

	final static float DEBUG_ENTITY_HALFEXTEND = 5;
	private Animation ani;

	public Meteroid() {
		super();
		this.ani = AssetLoader.getInstance().getAnimation("meteorite");
		setParticle(AssetLoader.getInstance().getParticle("meteorid5"));
	}

	@Override
	protected void initialize() {
		super.initialize();
		int width = ani.getWidth();
		int height = ani.getHeight();
        setInitialSize(width, height);
	}

    @Override
    public void initPhysics() {
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(PhysixManager.DENSITY).friction(PhysixManager.FRICTION)
                .asBox(initialSize.x, initialSize.y);
		physicsObject.setGravityScale(0.5f);
    }

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		super.render(container, g);
		ani.draw(this.getPosition().x - (ani.getWidth() / 2), this.getPosition().y - (ani.getHeight() / 2));
	}

	@Override
	public void beginContact(Contact contact) {
		Entity other = getOtherEntity(contact);
		if (other instanceof Astronaut) {
			((Astronaut) other).setOxygen(((Astronaut) other).getOxygen() - EntityConstants.METEORITE_DAMAGE);
		}

		if (!(other instanceof MeteoriteSpawner)) {
			World.getInstance().getEntityManager().removeEntity(this);
		}

		// Fixture a = contact.m_fixtureA;
		// Fixture b = contact.m_fixtureB;
		//
		// if (!(a.getBody().getUserData() != null && a.getBody().getUserData()
		// != null)) {
		// return;
		// }
		//
		// PhysixObject oA = (PhysixObject) a.getBody().getUserData();
		// PhysixObject oB = (PhysixObject) b.getBody().getUserData();
		//
		// if (oA.getOwner() != null ^ oB.getOwner() != null)
		// return;
		//
		// if (oA.getOwner() != null) { // b is level
		// System.out.println("b is level");
		// }
		// else { // a is level
		// System.out.println("a is level");
		// }
	}

	@Override
	public void endContact(Contact object) {
	}
}
