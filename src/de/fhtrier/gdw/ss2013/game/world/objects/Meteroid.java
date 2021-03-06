package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.EntityConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.spawner.MeteoriteSpawner;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

/**
 * Meteroid class
 * 
 * @author Kevin, Georg
 * 
 *         dont fix spelling mistake, is used too often byRD
 */
public class Meteroid extends EntityCollidable {

    final static float DEBUG_ENTITY_HALFEXTEND = 5;
    protected Animation ani;
    protected SoundPlayer soundPlayer;
    protected Sound impactSound;

    public Meteroid() {
        super();
        this.ani = AssetLoader.getInstance().getAnimation("meteorite");
        setParticle(AssetLoader.getInstance().getParticle("meteorid5"));
    }

    @Override
    protected void initialize() {
        super.initialize();
        int width = ani.getWidth();
        int height = ani.getHeight();
        setInitialSize(width, height);
        soundPlayer = SoundLocator.getPlayer();
        impactSound = SoundLocator.loadSound("meteorit");
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(PhysixManager.DENSITY).friction(PhysixManager.FRICTION)
                .category(PhysixConst.ENEMY).mask(PhysixConst.MASK_ENEMY)
                .fixedRotation(false)
                .linearDamping(0.1f)
                .angularDamping(0.8f)
                .asCircle(initialSize.x / 2);
        physicsObject.setGravityScale(0.5f);
    }

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        super.render(container, g);
        
        g.pushTransform();
        g.translate(getPosition().x, getPosition().y);
        g.rotate(0, 0, (float) (physicsObject.getAngle() / Math.PI * 180.0));
        g.translate(-ani.getWidth() / 2.0f, -ani.getHeight() / 2.0f);
        ani.draw();
        g.popTransform();
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (!(other instanceof MeteoriteSpawner)) {
            soundPlayer.playSound(impactSound);
        }
        if (other == null) {
            World.getInstance().getEntityManager().removeEntity(this);
        }
        if (other instanceof Astronaut) {
            ((Astronaut) other).setOxygen(((Astronaut) other).getOxygen()
                    - EntityConstants.METEORITE_DAMAGE);
            World.getInstance().getEntityManager().removeEntity(this);
        }
    }

    @Override
    public void endContact(Contact object) {
    }
}
