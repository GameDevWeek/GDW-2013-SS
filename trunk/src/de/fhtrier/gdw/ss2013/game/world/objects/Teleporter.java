package de.fhtrier.gdw.ss2013.game.world.objects;

import java.util.ArrayList;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public class Teleporter extends EntityCollidable implements Interactable {

    private boolean isActive = true;
    private Teleporter target;

    private ArrayList<Entity> ignorList;
    private ArrayList<Entity> toSet;

    public Teleporter() {
        super(AssetLoader.getInstance().getImage("door_closed"));
        initialize();
    }

    @Override
    protected void initialize() {
        ignorList = new ArrayList<Entity>();
        toSet = new ArrayList<Entity>();
        super.initialize();
    }

    public void setTargetTeleporter(Teleporter target) {
        this.target = target;
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        for (Entity e : toSet) {
            e.getPhysicsObject().setPosition(target.getPosition());
        }
        toSet.clear();
        // TODO Auto-generated method stub
        super.update(container, delta);
    }

    @Override
    public void beginContact(Contact contact) {

        if (isActive) {
            Fixture a = contact.getFixtureA();
            Fixture b = contact.getFixtureB();
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();

            if (objectA.getOwner() instanceof Player
                    || objectA.getOwner() instanceof Box) {
                if (!ignorList.contains(objectA.getOwner())) {
                    target.ignorList.add(objectA.getOwner());
                    toSet.add(objectA.getOwner());
                    // objectA.setPosition(target.getPosition());
                }
            }
            if (objectB.getOwner() instanceof Player
                    || objectB.getOwner() instanceof Box) {
                if (!ignorList.contains(objectB.getOwner())) {
                    target.ignorList.add(objectB.getOwner());
                    toSet.add(objectB.getOwner());
                }

            }

        }
    }

    @Override
    public void endContact(Contact contact) {
        if (isActive) {
            Fixture a = contact.getFixtureA();
            Fixture b = contact.getFixtureB();
            PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
            PhysixObject objectB = (PhysixObject) b.getBody().getUserData();

            if (objectA.getOwner() instanceof Player
                    || objectA.getOwner() instanceof Box) {
                if (ignorList.contains(objectA.getOwner())) {
                    ignorList.remove(objectA.getOwner());
                }
            }
            if (objectB.getOwner() instanceof Player
                    || objectB.getOwner() instanceof Box) {
                if (ignorList.contains(objectB.getOwner())) {
                    ignorList.remove(objectB.getOwner());
                }

            }
        }

    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub
        isActive = true;
    }

    @Override
    public void deactivate() {
        isActive = false;
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        return isActive;
    }

}
