//Author: Jerry, Thomas M.

package de.fhtrier.gdw.ss2013.physics;

import java.util.ArrayList;
import java.util.Collection;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.Entity;

public abstract class PhysicsObject {

	private Entity owner;
	private Body myBody;
	private Collection<ICollisionListener> collisionListeners;
	private BodyDef myBodyDef;
	private FixtureDef myFixtureDef;

	protected PhysicsObject(Entity owner, float restitution, float density, float friction,
            boolean isSensor) {
		this.owner = owner;
		
	      myFixtureDef = new FixtureDef();
	        myFixtureDef.restitution = restitution;
	        myFixtureDef.density = density;
	        myFixtureDef.friction = friction;
	        myFixtureDef.isSensor = isSensor;
	        
	        
		collisionListeners = new ArrayList<ICollisionListener>();
	}

	protected void init(Shape myShape,BodyDef myBodyDef)
	{
	    myFixtureDef.shape = myShape;
	    this.myBodyDef = myBodyDef;
	    enableSimulation();
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
		this.myBody = PhysicsManager.getInstance().enableSimulation(this);
		this.myBody.m_userData = this;
	}

	public void disableSimulation() {
		PhysicsManager.getInstance().disableSimulation(this);
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
		MassData massData = new MassData();
		massData.mass = mass;
		setMassData(massData);
	}

	public float getMass() {
		return myBody.getMass();
	}

	public boolean addCollisionListener(ICollisionListener listener) {
		return collisionListeners.add(listener);
	}

	public boolean removeCollisionListener(ICollisionListener listener) {
		return collisionListeners.remove(listener);
	}

	public void onCollide(PhysicsObject po) {
		for (ICollisionListener listener : collisionListeners) {
			System.out.println("Collision!!!!!!!!!!!!!!!!!!!");
			listener.onCollide(po);
		}
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

	/**
	 * 
	 * @param delta
	 *            deltaTime in miliseconds
	 * @throws SlickException
	 */
	public void update(GameContainer c, int delta) throws SlickException {

	}

}
