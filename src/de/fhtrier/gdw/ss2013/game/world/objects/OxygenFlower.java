/**
 *
 * OxygenFlower class
 *
 * @author Justin, Sandra
 *
 * Blume erzeugt Blasen und Blasenvorrat verringert sich
 */
package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.SpawnerConstants;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.world.World;

public class OxygenFlower extends EntityCollidable {

    private int bubbleTime;
    private int maxBubblesOnPlant;
    private int count;

    int bubbleRespawnTimer;

    // needs to be without parameters!

    public OxygenFlower() {
        super(AssetLoader.getInstance().getImage("plant"));
        this.maxBubblesOnPlant = 3;
        bubbleTime = 0;
    }

    @Override
    protected void initialize() {
        super.initialize();

        bubbleRespawnTimer = properties.getInt("bubbleSpawnDelay",
                SpawnerConstants.FLOWER_DEFAULT_SPAWN_DELAY);

        maxBubblesOnPlant = properties.getInt("maxBubblesOnPlant",
                SpawnerConstants.FLOWER_DEFAULT_MAX_BUBBLES);

    }

    @Override
    public boolean isBottomPositioned() {
        return true;
    }

    public void setMaxBubble(int maxBubble) {
        this.maxBubblesOnPlant = maxBubble;
    }

    public int getMaxBubble() {
        return maxBubblesOnPlant;
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y).sensor(true).asBox(
                initialSize.x, initialSize.y);
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        super.update(container, delta);

        bubbleTime += delta;

        if (bubbleTime >= bubbleRespawnTimer
                && this.count < this.getMaxBubble()) {

            float x = this.getPosition().getX()
                    + ((float) Math.random() * 150 - 75);
            float y = this.getPosition().getY() - (float) Math.random() * 30
                    - 50;
            OxygenBubble entity = World.getInstance().getEntityManager()
                    .createEntity(OxygenBubble.class);
            // Bubble-Objekt
            ((OxygenBubble) entity).setFlower(this);
            ((OxygenBubble) entity).setOrigin(x, y);
            this.count++;
            this.bubbleTime -= this.bubbleRespawnTimer; 
        }
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact object) {
    }

    public void decreaseBubbleCount() {
        count--;
    }
}
