package de.fhtrier.gdw.ss2013.game.world.objects;


import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Accelerator extends EntityCollidable {

	private boolean isActive = true;

    public Accelerator() {
    	super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y)
                .sensor(true).asBox(initialSize.x, initialSize.y);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        
        if (isActive && (other instanceof Astronaut || other instanceof Alien || other instanceof Entity)) {
            Alien alien = World.getInstance().getAlien();
            if(other == alien.getCurrentSelected()) {
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

}
