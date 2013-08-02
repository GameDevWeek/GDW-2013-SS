package de.fhtrier.gdw.ss2013.game;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Image;

import de.fhtrier.gdw.ss2013.physix.ICollisionListener;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public abstract class EntityCollidable extends Entity implements ICollisionListener {

    public EntityCollidable() {
        super();
    }

    public EntityCollidable(Image img) {
        super(img);
    }

    public EntityCollidable(Image img, String name) {
        super(img, name);
    }

    public EntityCollidable(String name) {
        super(name);
    }

    @Override
    public void setPhysicsObject(PhysixObject physicsObject) {
        super.setPhysicsObject(physicsObject);
        this.physicsObject.addCollisionListener(this);
    }
}
