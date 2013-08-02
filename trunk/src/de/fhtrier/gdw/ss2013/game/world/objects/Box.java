package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

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
    private Animation animation;

    public Box() {
        super();
        setAnimation(AssetLoader.getInstance().getAnimation("box"));
    }
    
    private void setAnimation(Animation animation) {
		this.animation = animation;
	}
    
    @Override
    public void render(GameContainer gc, Graphics g) {
    	animation.draw(getPosition().x-(animation.getWidth()/2), getPosition().y-(animation.getHeight()/2));
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
        
        //FIXME: correct on me check
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
