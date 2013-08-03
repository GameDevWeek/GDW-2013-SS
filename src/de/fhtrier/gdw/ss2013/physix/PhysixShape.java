package de.fhtrier.gdw.ss2013.physix;

import java.util.HashSet;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;

public abstract class PhysixShape {

    protected Entity owner;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected Fixture fixture;
    protected Body body;
    protected HashSet<ICollisionListener> collisionListeners = new HashSet<>();
    protected World associatedWorld;
    protected Vec2 dimension;

    PhysixShape(PhysixShapeConfig config) {
        fixtureDef = new FixtureDef();
        fixtureDef.density = config.density;
        fixtureDef.friction = config.friction;
        fixtureDef.isSensor = config.sensor;
        fixtureDef.filter.categoryBits = config.categoryBits;
        fixtureDef.filter.maskBits = config.maskBits;

        dimension = new Vec2();
    }

    protected void init(PhysixShapeConfig config, Shape shape, float x, float y) {
        bodyDef = new BodyDef();
        bodyDef.linearDamping = 0.0f;
        bodyDef.fixedRotation = true;
        bodyDef.type = config.bodyType;
        bodyDef.position.set(x, y);
        fixtureDef.shape = shape;

        associatedWorld = config.manager.getWorld();

        body = associatedWorld.createBody(bodyDef);
        body.setActive(config.active);
        body.setUserData(this);
        fixture = body.createFixture(fixtureDef);
    }

    Body getBody() {
        return body;
    }

    Fixture getFixture() {
        return fixture;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public Entity getOwner() {
        return owner;
    }

    public float getX() {
        return PhysixUtil.toWorld(body.getPosition().x);
    }

    public float getY() {
        return PhysixUtil.toWorld(body.getPosition().y);
    }

    public Vector2f getPosition() {
        return PhysixUtil.toWorld(body.getPosition());
    }

    public void setX(float x) {
        body.setTransform(new Vec2(PhysixUtil.toBox2D(x), body.getPosition().y), body.getAngle());
    }

    public void setY(float y) {
        body.setTransform(new Vec2(body.getPosition().x, PhysixUtil.toBox2D(y)), body.getAngle());
    }

    public void setPosition(Vector2f pos) {
        body.setTransform(PhysixUtil.toBox2D(pos), body.getAngle());
    }

    public void setPosition(float x, float y) {
        body.setTransform(new Vec2(PhysixUtil.toBox2D(x), PhysixUtil.toBox2D(y)), body.getAngle());
    }

    public void simpleForceApply(Vec2 force) {
        body.applyForceToCenter(force);
    }

    public void applyImpulse(Vector2f speed) {
        body.applyLinearImpulse(PhysixUtil.toBox2D(speed), body.getWorldCenter());
    }

    public void applyImpulse(float x, float y) {
        body.applyLinearImpulse(new Vec2(PhysixUtil.toBox2D(x), PhysixUtil.toBox2D(y)), body.getWorldCenter());
    }

    public float getAngle() {
        return body.getAngle();
    }

    public Vector2f getLinearVelocity() {
        return PhysixUtil.toWorld(body.getLinearVelocity());
    }

    public void setLinearVelocity(Vector2f v) {
        body.setLinearVelocity(PhysixUtil.toBox2D(v));
    }

    public void setLinearVelocity(float x, float y) {
        body.getLinearVelocity().x = PhysixUtil.toBox2D(x);
        body.getLinearVelocity().y = PhysixUtil.toBox2D(y);
        body.setAwake(true);
    }

    public void setLinearVelocityX(float x) {
        body.getLinearVelocity().x = PhysixUtil.toBox2D(x);
        body.setAwake(true);
    }

    public void setLinearVelocityY(float y) {
        body.getLinearVelocity().y = PhysixUtil.toBox2D(y);
        body.setAwake(true);
    }

    public boolean isAwake() {
        return body.isAwake();
    }

    public boolean isAsleep() {
        return !body.isAwake();
    }

    public void setGravityScale(float gravityScale) {
        body.setGravityScale(gravityScale);
    }

    public float getGravityScale() {
        return body.getGravityScale();
    }

    public void setMassData(MassData massData) {
        body.setMassData(massData);
    }

    public void setMassData(float mass) {
        body.m_mass = mass;
    }

    public float getMass() {
        return body.getMass();
    }

    public float getFriction() {
        return fixtureDef.friction;
    }

    public float getDensity() {
        return fixtureDef.density;
    }

    public float getRestitution() {
        return fixtureDef.restitution;
    }

    public boolean addCollisionListener(ICollisionListener listener) {
        return collisionListeners.add(listener);
    }

    public boolean removeCollisionListener(ICollisionListener listener) {
        return collisionListeners.remove(listener);
    }

    public void beginContact(Contact contact) {
//        PhysixObject other = objectA == this ? objectB : objectA;
//            
        for (ICollisionListener listener : collisionListeners) {
            listener.beginContact(contact);
        }
    }

    public void endContact(Contact contact) {
//        PhysixObject other = objectA == this ? objectB : objectA;
//        
        for (ICollisionListener listener : collisionListeners) {
            listener.endContact(contact);
        }
    }

    public void setActive(boolean value) {
        body.setActive(value);
    }

    public Vector2f getDimension() {
        return PhysixUtil.toWorld(this.dimension);
    }

    public void removeFromWorld() {
        associatedWorld.destroyBody(body);
    }

    public BodyType getBodyType() {
        return body.getType();
    }

	public boolean isSensor() {
		return fixtureDef.isSensor;
	}
}
