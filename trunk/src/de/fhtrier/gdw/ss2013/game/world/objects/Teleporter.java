package de.fhtrier.gdw.ss2013.game.world.objects;

import java.util.ArrayList;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import org.jbox2d.dynamics.BodyType;

public class Teleporter extends EntityCollidable implements Interactable {

    private boolean isActive = true;
    private Teleporter target;

    private ArrayList<Entity> ignorList;
    private ArrayList<Entity> toSet;

    public Teleporter() {
        super(AssetLoader.getInstance().getImage("door_closed"));
    }

    @Override
    protected void initialize() {
        super.initialize();
        ignorList = new ArrayList<Entity>();
        toSet = new ArrayList<Entity>();
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y)
                .sensor(true).asBox(initialSize.x, initialSize.y);
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

        if (isActive && target != null) {
            Entity other = getOtherEntity(contact);
            if (other instanceof Astronaut || other instanceof Alien
                    || other instanceof Box) {
                if (!ignorList.contains(other)) {
                    target.ignorList.add(other);
                    toSet.add(other);
                    // objectA.setPosition(target.getPosition());
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        if (isActive && target != null) {
            Entity other = getOtherEntity(contact);
            if (other instanceof Astronaut || other instanceof Alien
                    || other instanceof Box) {
                if (ignorList.contains(other)) {
                    ignorList.remove(other);
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
