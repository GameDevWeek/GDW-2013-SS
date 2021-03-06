package de.fhtrier.gdw.ss2013.game.world.objects;

import java.util.ArrayList;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

public class Teleporter extends EntityCollidable implements Interactable {

    private boolean isActive = true;
    private Teleporter target;
    private boolean mirrored = false;

    private boolean firstUpdateDone = false;

    private final ArrayList<Entity> ignorList = new ArrayList<>();
    private final ArrayList<Entity> toSet = new ArrayList<>();
    
    private SoundPlayer soundpLayer;
    private Sound teleportSound;

    public Teleporter() {
        super(AssetLoader.getInstance().getImage("teleporter_inactive"));

    }

    @Override
    public boolean isBottomPositioned() {
        return true;
    }

    @Override
    protected void initialize() {
        super.initialize();
        ignorList.clear();
        toSet.clear();

        if (properties != null) {

            isActive = properties.getBoolean("isActive", true);
            mirrored = properties.getBoolean("mirrored", false);
        }

        img = AssetLoader.getInstance().getImage(
                (isActive ? "teleporter_active" : "teleporter_inactive"));
        soundpLayer = SoundLocator.getPlayer();
        teleportSound = SoundLocator.loadSound("teleporter");
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y).sensor(true).asBox(
                initialSize.x, initialSize.y);
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
        if (!firstUpdateDone) {
            String targetName = properties.getProperty("target");
            Entity targetTeleporter = World.getInstance().getEntityManager()
                    .getEntityByName(targetName);
            if (targetTeleporter instanceof Teleporter) {
                target = (Teleporter) targetTeleporter;
            } else {
                System.err.println(getName() + "can not find " + target);
            }
            firstUpdateDone = true;
        }
        toSet.clear();
        super.update(container, delta);

    }

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        if (mirrored) {
            g.drawImage(img, getPosition().x - (img.getWidth() / 2),
                    getPosition().y - (img.getHeight() / 2), img.getWidth(), 0,
                    0, img.getHeight());
        } else {
            g.drawImage(img, getPosition().x - (img.getWidth() / 2),
                    getPosition().y - (img.getHeight() / 2), 0, 0,
                    img.getWidth(), img.getHeight());
        }
        // super.render(container, g);
    }

    @Override
    public void beginContact(Contact contact) {

        if (isActive && target != null) {
            Entity other = getOtherEntity(contact);
            setParticle(AssetLoader.getInstance()
                    .getParticle("teleporter_test").clone());

            if (other instanceof Alien || other instanceof Astronaut) {

                World.getInstance().getAstronaut().teleportAlienback();

            }
            if (other instanceof Astronaut || other instanceof Box) {
                if (!ignorList.contains(other)) {
                    target.ignorList.add(other);
                    toSet.add(other);
                    soundpLayer.playSoundAt(teleportSound, this);
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
                    || other instanceof Entity) {
                if (ignorList.contains(other)) {
                    ignorList.remove(other);
                }
            }
        }

    }

    @Override
    public void activate() {
        isActive = true;
        img = AssetLoader.getInstance().getImage("teleporter_active");
    }

    @Override
    public void deactivate() {
        isActive = false;
        img = AssetLoader.getInstance().getImage("teleporter_inactive");
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

}
