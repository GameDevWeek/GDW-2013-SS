package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixCircle;
import de.fhtrier.gdw.ss2013.physix.PhysixShape;

public class GrowPlant extends EntityCollidable {
   
    public GrowPlant() {
        
       // setParticle(AssetLoader.getInstance().getParticle("pollen1"));
    }
    
    public void grow() {
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixShape objectA = (PhysixShape) a.getBody().getUserData();
        PhysixShape objectB = (PhysixShape) b.getBody().getUserData();
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
