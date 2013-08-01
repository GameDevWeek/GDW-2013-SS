package de.fhtrier.gdw.ss2013.game.world.enemies;

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
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;

/**
 * Meteroid class
 * 
 * @author Kevin, Georg
 * 
 */
public class Meteroid extends EntityCollidable {

	final static float DEBUG_ENTITY_HALFEXTEND = 5;
	private float spawn_x;
	private float spawn_y;
	private Animation ani;

	public Meteroid() {
		super();
		this.ani = AssetLoader.getInstance().getAnimation("meteorite");
	}

	@Override
	protected void initialize() {
		super.initialize();
		if (physicsObject != null)
			System.out.println("BAM");
		PhysixManager physicsManager = World.getInstance().getPhysicsManager();
		
		// FIXME: Dirty hack to get width and height of an Meteorite
		int width = AssetLoader.getInstance().getAnimation("meteorite").getWidth();
		int height = AssetLoader.getInstance().getAnimation("meteorite").getHeight();

		PhysixBox box = new PhysixBox(physicsManager, spawn_x, spawn_y, width, height, BodyType.DYNAMIC, 1, 0.5f, false);
		this.setPhysicsObject(box);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
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

	public void setSpawnPosition(float x, float y) {
		this.spawn_x = x;
		this.spawn_y = y;
	}
}
