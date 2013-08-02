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
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.physix.PhysixShape;
import de.fhtrier.gdw.ss2013.physix.PhysixShapeConfig;
import de.fhtrier.gdw.ss2013.renderer.DynamicParticleSystem;
import org.jbox2d.dynamics.BodyType;

/**
 * Entity base class
 */
public abstract class Entity {

    protected final Vector2f origin = new Vector2f();
    protected final Vector2f initialSize = new Vector2f();
    protected PhysixShape physicsObject;
    protected Image img;
    protected String name;
    protected SafeProperties properties;
    private static int entityId = 0;
    final static float DEBUG_ENTITY_HALFEXTEND = 5;
    protected DynamicParticleSystem particle;

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
	protected int renderLayer = 0;

    public Entity(String name) {
        this.name = name;
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        if (img != null) {
            g.drawImage(img, getPosition().x - (img.getWidth() / 2), getPosition().y - (img.getHeight() / 2));
        }
        if (particle != null) {
            particle.render();
        }
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
        } else {
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

    public void setVelocity(float x, float y) {
        if (physicsObject == null) {
            throw new NullPointerException("Physics object does not exist.");
        }
        physicsObject.setLinearVelocity(x, y);
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
		if (img != null) {
			setInitialSize(getImage().getWidth(), getImage().getHeight());
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

    public void setOrigin(Vector2f pos) {
        origin.set(pos);
    }

    public void setOrigin(float x, float y) {
        origin.set(x, y);
    }

    public void setInitialSize(Vector2f size) {
        initialSize.set(size);
    }

    public void setInitialSize(float width, float height) {
        initialSize.set(width, height);
    }

    public void setPhysicsObject(PhysixShape physicsObject) {
        physicsObject.setOwner(this);
        this.physicsObject = physicsObject;
    }

    public PhysixShapeConfig createPhysics(BodyType type, float x, float y) {
        return new PhysixShapeConfig(World.getInstance().getPhysicsManager(), this, type, x, y);
    }

//    public abstract void initPhysics();

    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y)
                .density(PhysixManager.DENSITY).friction(PhysixManager.FRICTION)
                .asBox(initialSize.x, initialSize.y);
    }

    public PhysixShape getPhysicsObject() {
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

    public Entity getOtherEntity(Contact contact) {
        Fixture a = contact.getFixtureA();
        if (a.getBody().getUserData() != physicsObject) {
            return ((PhysixShape) a.getBody().getUserData()).getOwner();
        }
        Fixture b = contact.getFixtureB();
        return ((PhysixShape) b.getBody().getUserData()).getOwner();
    }

    public boolean isBottomPositioned() {
        return false;
    }
}
