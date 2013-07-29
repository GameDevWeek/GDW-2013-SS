//Author: Jerry, Thomas M.

package de.fhtrier.gdw.ss2013.physics;

import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.Entity;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

public abstract class PhysicsObject {

    private Entity owner;
    private Body myBody;
    private Collection<ICollisionListener> collisionListeners;
    private BodyDef myBodyDef;
    
    protected PhysicsObject(BodyDef myBodyDef,Entity owner) {
        this.owner = owner;
        this.myBodyDef = myBodyDef;
        enableSimulation();
        
        myBody.m_userData = this;
        collisionListeners = new ArrayList<ICollisionListener>();
    }

    protected void setBody(Body myBody) {
        this.myBody = myBody;
    }

    public void enableSimulation()
    {
        //this.myBody = PhysicsManager.;
    }
    
    public void disableSimulation()
    {
        
    }
    
    public Body getBody() {
        return myBody;
    }
    
    public BodyDef getBodyDef()
    {
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
        setPosition(new Vec2(x,y));
    }
    
    public void simpelForceApply(Vec2 force)
    {
        myBody.applyForceToCenter(force);
    }
    
    public float getAngle()
    {
        return myBody.getAngle();
    }
    
    public Vec2 getLiniarVelocity()
    {
        return myBody.getLinearVelocity();
    }

    public void setLinearVelocity(Vec2 v)
    {
        myBody.setLinearVelocity(v);
    }
    
    public boolean isAwake()
    {
        return myBody.isAwake();
    }
    
    public boolean isAsleep()
    {
        return !myBody.isAwake();
    }
    
    public void setGravityScale(float gravityScale)
    {
        myBody.setGravityScale(gravityScale);
    }
    
    public float getGravityScale()
    {
        return myBody.getGravityScale();
    }
    
    public void setMassData(MassData massData)
    {
        myBody.setMassData(massData);
    }

    public void setMassData(float mass)
    {
        MassData massData = new MassData();
        massData.mass = mass;
        setMassData(massData);
    }
    
    public float getMass()
    {
        return myBody.getMass();
    }
    
    public boolean addCollisionListener(ICollisionListener listener) {
        return collisionListeners.add(listener);
    }
    
    public boolean removeCollisionListener(ICollisionListener listener) {
        return collisionListeners.remove(listener);
    }
    
    public void onCollide(PhysicsObject po)
    {
        for(ICollisionListener listener: collisionListeners)
        {
            listener.onCollide(po);
        }
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
