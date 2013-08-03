package de.fhtrier.gdw.ss2013.game.world.objects;


import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Accelerator extends EntityCollidable implements Interactable {

	private boolean isActive = false;
    private Animation animation;

    public Accelerator() {
    	super();
        
        animation = AssetLoader.getInstance().getAnimation("accelerator");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();
        setInitialSize(animation.getWidth(), animation.getHeight());
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y)
                .sensor(true).asBox(initialSize.x, initialSize.y);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        animation.draw(origin.x - initialSize.x / 2.0f,
                origin.y - initialSize.y / 2.0f);
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        
        if (isActive && (other instanceof Astronaut || other instanceof Alien || other instanceof Box)) {
            Alien alien = World.getInstance().getAlien();
            if(other == alien.getCurrentSelectedBox()) {
                alien.dropCurrentSelected();
            }
            float x = properties.getFloat("velocity_x", 0);
            float y = properties.getFloat("velocity_y", 0);
//            if(y != 0)
//                other.setVelocityY(y);
//            if(x != 0)
//                other.setVelocityX(x);
            
            other.getPhysicsObject().applyImpulse(x, y);
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

	@Override
	public void activate() {
		isActive = true;
	}

	@Override
	public void deactivate() {
		isActive = false;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}
}
