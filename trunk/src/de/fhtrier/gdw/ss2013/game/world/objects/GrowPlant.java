package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixCircle;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public class GrowPlant extends EntityCollidable {
   
    public GrowPlant() {
        
       // setParticle(AssetLoader.getInstance().getParticle("pollen1"));
    }
    
    public void grow() {
        PhysixObject childPhysics = new PhysixCircle(World.getInstance()
                .getPhysicsManager(), getPosition().x, getPosition().y,
                (img.getWidth() / 2 + img.getHeight() / 2),
                BodyType.STATIC, 0, 0, true);
        super.setPhysicsObject(childPhysics);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
        PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
        Entity e = objectA.getOwner();
        if (!(e instanceof Astronaut)) {
            e = objectB.getOwner();
            this.grow();
        }
    }

    @Override
    public void endContact(Contact object) {
    }

 
    
    
}
