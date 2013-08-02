/**
 *
 * OxygenFlower class
 *
 * @author Justin, Sandra
 *
 * Blume erzeugt Blasen und Blasenvorrat verringert sich
 */
package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import org.jbox2d.dynamics.BodyType;

public class OxygenFlower extends EntityCollidable implements Interactable {

    private float bubbleTime;
    private int maxBubble;
    private int count;
    private World w = World.getInstance();
    private EntityManager m;

    // needs to be without parameters!

    public OxygenFlower() {
        super(AssetLoader.getInstance().getImage("plant"));
        this.maxBubble = 6; // FIXME: use a better value
        bubbleTime = 0;
        m = w.getEntityManager();
    }

    public void createBubbles() {
        if (count != getMaxBubble()) {
            
            if(count <= 5)
                {
                    float x = this.getPosition().getX() + ((float) Math.random() * 100-50);
                    float y = this.getPosition().getY() - (float) Math.random() * 100-50;
                    OxygenBubble entity = m.createEntity(OxygenBubble.class);
                    // Bubble-Objekt
                    ((OxygenBubble) entity).setFlower(this);
                    ((OxygenBubble) entity).setOrigin(x,y);

                }
            // bubbleCount
            count++;
            bubbleTime = 0;
        }
    }

    public void setMaxBubble(int maxBubble) {
        this.maxBubble = maxBubble;
    }

    public int getMaxBubble() {
        return maxBubble;
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y)
                .sensor(true).asBox(initialSize.x, initialSize.y);
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {

        bubbleTime += delta;
        if (bubbleTime >= 800) {

            this.createBubbles();
            
        }
    }

    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub
    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut) {
            if (bubbleTime >= 2000) {
                this.createBubbles();
            }
        }
    }

    @Override
    public void endContact(Contact object) {
    }
    
    public void decreaseBubbleCount() {
    	count--;
    }
}
