package de.fhtrier.gdw.ss2013.game.world.objects;


import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Accelerator extends EntityCollidable {

    private Animation animation;
    private boolean invertAnimation;
    private boolean invertAnimationUp;
    private SoundPlayer soundPlayer;
    private Sound accSound;

    public Accelerator() {
    	super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();
        
        float x = properties.getFloat("velocity_x", 0);
        float y = properties.getFloat("velocity_y", 0);
        if (Math.abs(x) > Math.abs(y)) {
            animation = AssetLoader.getInstance().getAnimation("accelerator_right");
            invertAnimation = x < 0;
        } else {
            animation = AssetLoader.getInstance().getAnimation("accelerator_up");
            invertAnimationUp = y > 0;
        }
        setInitialSize(animation.getWidth(), animation.getHeight());
        soundPlayer = SoundLocator.getPlayer();
        accSound = SoundLocator.loadSound("accelerator");
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y)
                .sensor(true).asBox(initialSize.x, initialSize.y);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        
        if (invertAnimationUp) {
            animation.draw(origin.x - animation.getWidth() / 2,
                    origin.y + animation.getHeight() / 2,
                    animation.getWidth(),
                    -animation.getHeight());
        } else if (invertAnimation) {
            animation.draw(origin.x + animation.getWidth() / 2, origin.y
                    - animation.getHeight() / 2, -animation.getWidth(),
                    animation.getHeight());
        } else {
            animation.draw(origin.x - animation.getWidth() / 2, origin.y
                    - animation.getHeight() / 2);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);

        if (other instanceof Astronaut || other instanceof Alien || other instanceof Entity) {
            animation.restart();
            Alien alien = World.getInstance().getAlien();
            if (other == alien.getCurrentSelected()) {
                alien.dropCurrentSelected();
            }
            float x = properties.getFloat("velocity_x", 0);
            float y = properties.getFloat("velocity_y", 0);
//            if(y != 0)
//                other.setVelocityY(y);
//            if(x != 0)
//                other.setVelocityX(x);

            other.getPhysicsObject().applyImpulse(x, y);
            soundPlayer.playSoundAt(accSound, this);
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

}
