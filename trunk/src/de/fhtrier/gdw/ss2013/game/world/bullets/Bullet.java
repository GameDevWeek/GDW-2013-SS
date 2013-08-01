package de.fhtrier.gdw.ss2013.game.world.bullets;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;

public abstract class Bullet extends EntityCollidable {

	private float spawny;
	private float spawnx;
	private Vector2f shootDirection;

	private Image img;
	protected int livetime;
	private EntityManager m;
	private float damage;

	public Bullet() {
		super();
		img = AssetLoader.getInstance().getImage("boltEnemy");
		m = World.getInstance().getEntityManager();
	}

	@Override
	protected void initialize() {
		super.initialize();
		livetime = 60 * 10;
		setDamage(PlayerConstants.BULLET_DAMAGE);

		int width = img.getWidth();
		int height = img.getHeight();

		PhysixBox box = new PhysixBox(World.getInstance().getPhysicsManager(), spawnx, spawny, width, height,
				BodyType.KINEMATIC, 1, 0.5f, true);
		setPhysicsObject(box);
		setVelocity(shootDirection);
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		img.draw(this.getPosition().x - (img.getWidth() / 2), this.getPosition().y - (img.getHeight() / 2));

		// g.drawString(this.hashCode(), position.x, position.y);
	}

	public void update(GameContainer container, int delta) throws SlickException {
		float df = delta / 1000f;
		
		float x = physicsObject.getX();
		float y = physicsObject.getY();
		System.out.println((getVelocity().x * df) + " | " + (getVelocity().y * df));
		physicsObject.setPosition(x + (getVelocity().x * df), y + (getVelocity().y * df));

		if (livetime <= 0) {
			m.removeEntity(this);
		}
		livetime--;
	}

	public void setReferences(EntityManager m) {
		this.m = m;
	}

	@Override
	public abstract void beginContact(Contact contact);

	@Override
	public void endContact(Contact object) {
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public void setSpawnXY(float x, float y) {
		spawnx = x;
		spawny = y;
	}

	public void setShootDirection(Vector2f shootDirection) {
		this.shootDirection = shootDirection.copy();
	}

	/**
	 * use this for level collision ect.
	 */
	public void checkForUnwantedContacts(Contact contact) {
		Entity other = getOtherEntity(contact);
        if(other == null) {
            World.getInstance().getEntityManager().removeEntity(this);
        }    
	}
}
