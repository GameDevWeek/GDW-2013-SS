/**
 * 
 * OxygenFlower class
 * @author Justin, Sandra
 * 
 * Blume erzeugt Blasen und Blasenvorrat verringert sich
 */
package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.objects.OxygenBubble;
import de.fhtrier.gdw.ss2013.physics.ICollidable;
import de.fhtrier.gdw.ss2013.physics.PhysicsObject;
import de.fhtrier.gdw.ss2013.physix.PhysixCircle;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public class OxygenFlower extends Entity implements Interactable, ICollidable{

    private float bubbleTime;
    private int maxBubble;
    private int count;
    private World w = World.getInstance();
    private EntityManager m;
    // needs to be without parameters!
    public OxygenFlower() {
        super(AssetLoader.getInstance().getImage("plant"));
        this.maxBubble = 6; //FIXME: use a better value
        bubbleTime = 0;
        m = w.getEntityManager();
    }

    //public void shootBubbles(EntityManager manager) {
    
    public void shootBubbles()
    {
        if (count != getMaxBubble()) {
            float x = this.getPosition().getX() + (float)Math.random()*100;
            float y = this.getPosition().getY() - (float)Math.random()*100;
            Vector2f bubblePos = new Vector2f(x, y);
            Entity entity = m.createEntity(OxygenBubble.class);
            //Bubble-Objekt
            PhysixObject childPhysics = new PhysixCircle(w.getPhysicsManager(),x,y,(img.getWidth()/2+img.getHeight()/2)/6,BodyType.KINEMATIC,0,0,true);
            entity.setPhysicsObject(childPhysics);
            //bubbleCount
            count++;
            bubbleTime = 0;
        }
    }

//        while (count < maxBubble) {
//            Vector2f bubblePos = new Vector2f(x, y);
//            x += (Math.random() * 100);
//            y += (Math.random() * 100);
//            Entity entity = manager.createEntity(OxygenBubble.class);
//            childPhysics = bubblePos.copy()
//            entity.setPhysicsObject(childPhysics);
//            count++;
//        }

    @Override
    public void onCollision(Entity e)
    {
        if(e instanceof Astronaut)
        {
            if (bubbleTime >= 2000)
            {
                this.shootBubbles();
            }
        }
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

    public void update(GameContainer container, int delta)
            throws SlickException {
        
        bubbleTime += delta;
        if (bubbleTime >= 1000)
            this.shootBubbles();
    }
    @Override
    public Fixture getFixture() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
        
    }
}
