/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.GameDataInfo.WorldInfo;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;
import de.fhtrier.gdw.ss2013.renderer.DynamicParticleSystem;

/**
 * Entity base class
 */
public abstract class Entity {

	protected PhysixObject physicsObject;
	protected Image img;
	protected String name;
	protected SafeProperties properties;
	protected int renderLayer = 0;

	private static int entityId = 0;

	final static float DEBUG_ENTITY_HALFEXTEND = 5;

	protected DynamicParticleSystem particle;
	protected AssetLoader asset;
	
	private float spawnX, spawnY;

	/* every Entity-class needs a constructor without any parameters! */
	public Entity() {
		setName();
	}

	public Entity(Image img) {
		this();
		this.img = img;
	}

	public Entity(Image img, String name) {
		this.img = img;
		this.name = name;
	}

	public Entity(String name) {
		this.name = name;
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		if (img != null) {
			g.drawImage(img, getPosition().x - (img.getWidth() / 2), getPosition().y - (img.getHeight() / 2));
		}
		if (particle != null)
			particle.render();
	}

	public void update(GameContainer container, int delta) throws SlickException {
		if (particle != null) {
			particle.update(delta);
			particle.setPosition(this.getPosition().x, this.getPosition().y);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null && !name.equals("")) {
			this.name = name;
		}
		else {
			this.name = String.valueOf(entityId++);
		}

	}

	public void setProperties(SafeProperties properties) {
		this.properties = properties;
	}

	public SafeProperties getProperties() {
		return this.properties;
	}

	public void setName() {
		setName(null);
	}

	public Vector2f getPosition() {
		return physicsObject.getPosition();
	}

	public Vector2f getVelocity() {
		if (physicsObject == null) {
			throw new NullPointerException("Physics object does not exist.");
		}
		return physicsObject.getLinearVelocity();
	}

	public void setVelocity(Vector2f velocity) {
		if (physicsObject == null) {
			throw new NullPointerException("Physics object does not exist.");
		}
		physicsObject.setLinearVelocity(velocity);
	}

	public void setVelocityX(float x) {
		if (physicsObject == null) {
			throw new NullPointerException("Physics object does not exist.");
		}
		physicsObject.setLinearVelocityX(x);
	}

	public void setVelocityY(float y) {
		if (physicsObject == null) {
			throw new NullPointerException("Physics object does not exist.");
		}
		physicsObject.setLinearVelocityY(y);
	}

	/**
	 * provide default values:<br>
	 * - called by entitymanager<br>
	 * - called when recycled<br>
	 * - DO NOT CALL SELF
	 */
	protected void initialize() {
	    
	    StackTraceElement[] err = Thread.currentThread().getStackTrace();
	    for(int i=1;i<err.length;++i) {
	        if(!err[i].getMethodName().equals("initialize")) {
//	            System.out.println(err[i].getMethodName());
				if(err[i].getMethodName().equals("<init>")) {
	                throw new UnsupportedOperationException("initialize() called in constructor! Invalid behaviour! ");
	            }
	            break;
	        }
	    }
		if (img != null && physicsObject != null) {
				// hier existiert ein PhysixObject, das aber eine falsche Breite und Höhe hat!
				// UND ich kanns hier besser erstellen
			float x = physicsObject.getPosition().x;
			float y = physicsObject.getPosition().y;
			float width = getImage().getWidth();
			float height = getImage().getHeight();
			WorldInfo worldInfo = AssetLoader.getInstance().getGameData().world;
		    
			PhysixObject box = new PhysixBox(World.getInstance().getPhysicsManager(), x, y, width, height,
					physicsObject.getBodyType(), worldInfo.density, worldInfo.friction,
					physicsObject.isSensor());
			physicsObject.removeFromWorld(); // dirty way to remove old physix object
			setPhysicsObject(box);
		}
	            
	}

	/**
	 * Override to provide destroy code for world objects (physic mostly)
	 */
	protected void dispose() {
		if (physicsObject != null) {
			this.physicsObject.removeFromWorld();
			this.physicsObject = null;
		}
	}

	public void setPhysicsObject(PhysixObject physicsObject) {
		physicsObject.setOwner(this);
		this.physicsObject = physicsObject;
	}

	public PhysixObject getPhysicsObject() {
		return this.physicsObject;
	}

	public DynamicParticleSystem getParticle() {
		return particle;
	}

	public void setParticle(DynamicParticleSystem particle) {
		this.particle = particle;
	}

	public void setImage(Image img) {
		this.img = img;
	}

	public Image getImage() {
		return img;
	}

	public float getSpawnX() {
		return spawnX;
	}

	public void setSpawnX(float startX) {
		this.spawnX = startX;
	}

	public float getSpawnY() {
		return spawnY;
	}

	public void setSpawnY(float startY) {
		this.spawnY = startY;
	}
	
	public void setSpawnXY(float x, float y) {
		this.spawnX = x;
		this.spawnY = y;
	}
    
    public Entity getOtherEntity(Contact contact) {
        Fixture a = contact.getFixtureA();
        if (a.getBody().getUserData() != physicsObject) {
            return ((PhysixObject) a.getBody().getUserData()).getOwner();
        }
        Fixture b = contact.getFixtureB();
        return ((PhysixObject) b.getBody().getUserData()).getOwner();
    }


}
