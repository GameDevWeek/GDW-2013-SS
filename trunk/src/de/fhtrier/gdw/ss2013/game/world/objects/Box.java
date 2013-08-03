package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.zones.DeadZone;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.physix.PhysixShapeConfig;
import org.jbox2d.dynamics.BodyType;

/**
 * Box class
 *
 * @author Kevin, Georg
 *
 */
public class Box extends EntityCollidable {

    private int isPlayerOnMe;
    private Animation animation;

    public Box() {
        super();
    }

    @Override
    public boolean isBottomPositioned() {
        return true;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.pushTransform();
        g.translate(getPosition().x, getPosition().y);
        g.rotate(0, 0, (float) (physicsObject.getAngle() / Math.PI * 180.0));
        g.translate(-animation.getWidth() / 2.0f, -animation.getHeight() / 2.0f);
        animation.draw();
        g.popTransform();
    }

    @Override
    protected void initialize() {
        super.initialize();
        animation = AssetLoader.getInstance().getAnimation(properties.getProperty("animation"));
        isPlayerOnMe = 0;
        setInitialSize(animation.getWidth(), animation.getHeight());
    }

    @Override
    public void initPhysics() {
        PhysixShapeConfig config = createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(PhysixManager.DENSITY).friction(PhysixManager.FRICTION)
                .fixedRotation(false)
                .linearDamping(0.1f)
                .angularDamping(0.8f);

        if (properties.getBoolean("round", false)) {
            config.asCircle(initialSize.x / 2);
        } else {
            config.asBox(initialSize.x, initialSize.y);
        }
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
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut || other instanceof Alien) {
            isPlayerOnMe++;
        }
        if (other instanceof DeadZone) {
            DeadZone zone = (DeadZone) other;
            if (zone.isRemoveBox()) {
                World.getInstance().getEntityManager().removeEntity(this);
                World.getInstance().getAlien().setCurrentSelectedBox(null);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut || other instanceof Alien) {
            isPlayerOnMe--;
        }
    }
}
