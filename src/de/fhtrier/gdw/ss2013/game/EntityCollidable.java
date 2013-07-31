package de.fhtrier.gdw.ss2013.game;

import de.fhtrier.gdw.ss2013.physix.ICollisionListener;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public abstract class EntityCollidable extends Entity implements ICollisionListener {

    @Override
    public abstract void beginContact(PhysixObject object);

    @Override
    public abstract void endContact(PhysixObject object);

}
