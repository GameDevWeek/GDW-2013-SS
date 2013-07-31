package de.fhtrier.gdw.ss2013.game;

import de.fhtrier.gdw.ss2013.physix.ICollisionListener;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public abstract class EntityCollidable extends Entity implements ICollisionListener {

    public abstract void beginContact(PhysixObject object);

    public abstract void endContact(PhysixObject object);
    
    @Override
    public void setPhysicsObject(PhysixObject physicsObject) {
        physicsObject.setOwner(this);
        this.physicsObject = physicsObject;
        this.physicsObject.addCollisionListener(this);
    }

}
