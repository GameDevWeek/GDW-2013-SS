/**
 * 
 * OxygenFlower class
 * @author Justin, Sandra
 * 
 * Blume erzeugt Blasen und Blasenvorrat verringert sich
 */
package de.fhtrier.gdw.ss2013.game.world.objects;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

public class OxygenFlower extends Entity implements Interactable {

    private int maxBubble;
    private int count;

    // needs to be without parameters!
    public OxygenFlower() {
        super(AssetLoader.getInstance().getImage("plant"));
        this.maxBubble = 5; //FIXME: use a better value
    }

    public void shootBubbles(EntityManager manager) {
        float x = this.getPosition().getX() - 20;
        float y = this.getPosition().getY() + 11;

//        while (count < maxBubble) {
//            Vector2f bubblePos = new Vector2f(x, y);
//            x += (Math.random() * 100);
//            y += (Math.random() * 100);
//            Entity entity = manager.createEntity(OxygenBubble.class);
//            childPhysics = bubblePos.copy()
//            entity.setPhysicsObject(childPhysics);
//            count++;
//        }
    }

    public void setMaxBubble(int maxBubble) {
        this.maxBubble = maxBubble;
    }

    public int getMaxBubble() {
        return maxBubble;
    }

    public void bubbleLost() {
        count--;
    }
}
