/**
 * OxygenBubble class
 * @author Justin, Sandra
 * 
 * erzeugt Sauerstoffblasen und soll Sauerstoffvorrat erhöhen
 * 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixCircle;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

//import org.newdawn.slick.Image;

public class OxygenBubble extends EntityCollidable implements Interactable {

    private float oxygenLevel = 40;
    private OxygenFlower flower;
    // private AssetLoader a = AssetLoader.getInstance();
    // private Image img = a.getImage("bubble");
    private EntityManager man;
    private float speed = 5;
    private int timer = 10;
    private boolean isUsed = false;
    private Vector2f startPosition;
    private Vector2f initalDirection;

    public OxygenBubble() {
        super(AssetLoader.getInstance().getImage("bubble"));
        this.oxygenLevel = getOxygenLevel();
        man = World.getInstance().getEntityManager();
    }
    
    @Override
    protected void initialize() {
        PhysixObject childPhysics = new PhysixCircle(World.getInstance()
                .getPhysicsManager(), startPosition.x, startPosition.y,
                (img.getWidth() / 2 + img.getHeight() / 2) / 4,
                BodyType.KINEMATIC, 0, 0, true);

        super.setPhysicsObject(childPhysics);
        super.initialize();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
        PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
        Entity e = objectA.getOwner();
        if (!(e instanceof Astronaut)) {
            e = objectB.getOwner();
        }

        if (e instanceof Astronaut) {
            if (((Astronaut) e).getOxygen() + oxygenLevel < ((Astronaut) e)
                    .getMaxOxygen()) {
                ((Astronaut) e).setOxygen(((Astronaut) e).getOxygen()
                        + oxygenLevel);
                man.removeEntity(this);
                isUsed = true;
            } else {
                ((Astronaut) e).setOxygen(((Astronaut) e).getMaxOxygen());

            }
        }// Collision von bubbles
        if (e instanceof OxygenBubble) {
            setVelocity(initalDirection);
        }

    }

    @Override
    public void endContact(Contact object) {
        // TODO Auto-generated method stub

    }

    public void setInitalDirection(Vector2f initialDirection) {
        this.initalDirection = initialDirection;
    }

    public float getOxygenLevel() {
        return oxygenLevel;
    }

    private int deltamod = 1;
    
    @Override
    public void update(GameContainer container, int delta) {
        
        
        if (!isUsed) {
            
            timer += delta*deltamod;
            if(timer >= 2000)
            {
                deltamod = -1;
            }
            if(timer <= 1001)
            {
                deltamod = 1;
            }
            
            if (timer < 1000) {
                setVelocity(initalDirection);
            } 
            else {
                setVelocity(new Vector2f(speed*deltamod,-speed*deltamod));
            }
        }
    }

    public Vector2f getStartPosition() {
        return startPosition;
    }

    public void setPosition(Vector2f position) {
        this.startPosition = position;
    }

    public OxygenFlower getFlower() {
        return flower;
    }

    public void setFlower(OxygenFlower flower) {
        this.flower = flower;
    }

    public void setOxygenLevel(float oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public void setReferences(OxygenFlower flower) {
        this.flower = flower;
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
}
