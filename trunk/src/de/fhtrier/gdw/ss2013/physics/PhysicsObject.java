//Author: Jerry, Thomas M.

package de.fhtrier.gdw.ss2013.physics;

import java.util.ArrayList;
import java.util.Collection;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.Entity;

public abstract class PhysicsObject {

	private Entity owner;
	private BodyDef myBodyDef;
	private FixtureDef myFixtureDef;
	private Fixture myFixture;
	private Body myBody;
	
	protected static final BodyType DEFAULT_BODYTYPE = BodyType.STATIC;
	protected static final float DEFAULT_DENSITY = 1.0f;
	protected static final float DEFAULT_RESTITUTION = 1.0f;
	protected static final float DEFAULT_FRICTION = 1.0f;
	protected static final boolean DEFAULT_ISSENSOR = false;
    

	private Collection<ICollisionListener> collisionListeners;

	protected PhysicsObject(float restitution, float density, float friction,
            boolean isSensor)
	{		
		myFixtureDef = new FixtureDef();
		myFixtureDef.restitution = restitution;
		myFixtureDef.density = density;
		myFixtureDef.friction = friction;
		myFixtureDef.isSensor = isSensor;
		
		collisionListeners = new ArrayList<ICollisionListener>();
	}

	protected void init(Shape myShape,BodyType bodyType, Vec2 Pos) {
	    this.myBodyDef = new BodyDef();
	    this.myBodyDef.type = bodyType;
	    myFixtureDef.shape = myShape;
	    enableSimulation();
	    this.myBody.createFixture(this.myFixtureDef);
	    setPosition(Pos);
	}
	
	protected void setBodyDef(BodyDef myBodyDef) {
		this.myBodyDef = myBodyDef;
	}

	protected void setFixtureDef(FixtureDef fixtureDef) {
		if (this.myBody == null) {
			throw new RuntimeException(
					"Bitte enableSimulation vorher ausführen.");
		}
		this.myFixtureDef = fixtureDef;
		this.myBody.createFixture(this.myFixtureDef);
	}

	public void enableSimulation() {
		if (this.myBodyDef == null) {
			throw new RuntimeException("Bitte myBodyDef initzalisiren!");
		}
		this.myBody = PhysicsManager.getCurrent().enableSimulation(this);
		this.myBody.m_userData = this;
		this.myFixture = this.myBody.createFixture(myFixtureDef);
	}

	public void disableSimulation() {
		PhysicsManager.getCurrent().disableSimulation(this);
	}

	public Body getBody() {
		return myBody;
	}

	public BodyDef getBodyDef() {
		return myBodyDef;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public Entity getOwner() {
		return owner;
	}

	public float getX() {
		return myBody.getPosition().x;
	}

	public float getY() {
		return myBody.getPosition().y;
	}

	public Vec2 getPosition() {
		return myBody.getPosition();
	}

	public void setX(float x) {
		myBody.setTransform(new Vec2(x, getY()), myBody.getAngle());
	}

	public void setY(float y) {
		myBody.setTransform(new Vec2(getX(), y), myBody.getAngle());
	}

	public void setPosition(Vec2 pos) {
		myBody.setTransform(pos, myBody.getAngle());
	}

	public void setPosition(float x, float y) {
		setPosition(new Vec2(x, y));
	}

	public void simpelForceApply(Vec2 force) {
		myBody.applyForceToCenter(force);
	}

	public void applyImpulse(Vec2 force) {
        myBody.applyLinearImpulse(force, this.myBody.getWorldCenter());
    }
	
	public float getAngle() {
		return myBody.getAngle();
	}

	public Vec2 getLiniarVelocity() {
		return myBody.getLinearVelocity();
	}

	public void setLinearVelocity(Vec2 v) {
		myBody.setLinearVelocity(v);
	}

	public boolean isAwake() {
		return myBody.isAwake();
	}

	public boolean isAsleep() {
		return !myBody.isAwake();
	}

	public void setGravityScale(float gravityScale) {
		myBody.setGravityScale(gravityScale);
	}

	public float getGravityScale() {
		return myBody.getGravityScale();
	}

	public void setMassData(MassData massData) {
		myBody.setMassData(massData);
	}

	public void setMassData(float mass) {
		myBody.m_mass = mass;
	}

	public float getMass() {
		return myBody.getMass();
	}

	public float getFriction() {
		return myFixtureDef.friction;
	}

	public float getDensity() {
		return myFixtureDef.density;
	}

	public float getRestitution() {
		return myFixtureDef.restitution;
	}
	
	public boolean addCollisionListener(ICollisionListener listener) {
	    return collisionListeners.add(listener);
	}

	public boolean removeCollisionListener(ICollisionListener listener) {
	    return collisionListeners.remove(listener);
	}

	public void beginContact(PhysicsObject object) {
	    for (ICollisionListener listener : collisionListeners) {
	        listener.beginContact(object);
	    }
	}

	public void endContact(PhysicsObject object) {
	    for (ICollisionListener listener : collisionListeners) {
	        listener.endContact(object);
	    }
	}
	
    public void update(GameContainer container, int delta)
            throws SlickException {
        if(owner != null)
        {
            owner.setPosition(getPosition().x, getPosition().y);
        }
    }
}
