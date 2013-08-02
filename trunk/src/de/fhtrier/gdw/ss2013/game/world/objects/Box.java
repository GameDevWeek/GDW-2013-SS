package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Box class
 * 
 * @author Kevin, Georg
 * 
 */
public class Box extends EntityCollidable {

    private int isPlayerOnMe;

    public Box() {
        super(AssetLoader.getInstance().getImage("box"));
    }
    
    @Override
    protected void initialize() {
        isPlayerOnMe = 0;
    }

    public void onCollision(Entity e) {
        if (e instanceof Astronaut) {
        }
    }

    public boolean isPlayerOnBox() {
        return isPlayerOnMe > 0;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
        PhysixObject objectB = (PhysixObject) b.getBody().getUserData();

        if (objectA.getOwner() instanceof Player) {
            isPlayerOnMe++;
        }
        if (objectB.getOwner() instanceof Player) {
            isPlayerOnMe++;
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
        PhysixObject objectB = (PhysixObject) b.getBody().getUserData();

        if (objectA.getOwner() instanceof Player) {
            isPlayerOnMe--;
        }
        if (objectB.getOwner() instanceof Player) {
            isPlayerOnMe--;
        }

    }
}
