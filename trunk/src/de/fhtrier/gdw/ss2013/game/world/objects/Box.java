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
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
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
        animation = AssetLoader.getInstance().getAnimation("box");
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) {
    	animation.draw(getPosition().x-(animation.getWidth()/2), getPosition().y-(animation.getHeight()/2));
    }

	@Override
    protected void initialize() {
        super.initialize();
        isPlayerOnMe = 0;
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(PhysixManager.DENSITY).friction(PhysixManager.FRICTION)
                .asBox(initialSize.x, initialSize.y);
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
    }

    @Override
    public void endContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut || other instanceof Alien) {
            isPlayerOnMe--;
        }
    }
}
