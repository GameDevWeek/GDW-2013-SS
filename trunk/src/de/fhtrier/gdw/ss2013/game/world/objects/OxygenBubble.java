/**
 * OxygenBubble class
 * @author Justin, Sandra
 * 
 * erzeugt Sauerstoffblasen und soll Sauerstoffvorrat erhöhen
 * 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.physics.ICollidable;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;


public class OxygenBubble extends Entity implements ICollidable, Interactable {

    private float oxygenLevel;
    private OxygenFlower flower;
    private AssetLoader a = AssetLoader.getInstance();
    private Image img = a.getImage("bubble");
    private EntityManager man;
    private float speed = 2;
    private float Oxygenlevel;

    public OxygenBubble() {
        super(AssetLoader.getInstance().getImage("bubble"));
        this.oxygenLevel = getOxygenLevel();
    }

    @Override
    public void onCollision(Entity e) {
        if (e instanceof Astronaut) {
            if (((Astronaut) e).getOxygen() + oxygenLevel < ((Astronaut) e)
                    .getMaxOxygen()) {
                ((Astronaut) e).setOxygen(((Astronaut) e).getOxygen()
                        + oxygenLevel);
                man.removeEntity(this);
                flower.bubbleLost();
            } else {
                ((Astronaut) e).setOxygen(((Astronaut) e).getMaxOxygen());
            }
        }//Collision von bubbles
        if(e instanceof OxygenBubble) {
            setVelocityX(speed);
            setVelocityY(speed);
//            Vector2f position = getPosition();
//            Vector2f otherPosition = getPosition();
//            if(otherPosition.x <= this.position.x)
//            {
//                this.position.x -= this.velocity.x;
//            }
//            if(e.getPosition().y <= this.position.y)
//            {
//                this.position.y -= this.velocity.y;
//            }
        }
    }

    public float getOxygenLevel() {
        return oxygenLevel;
    }

    @Override
    public void update(GameContainer container, int delta) {
        hover();
    }

    public void hover() {
        setVelocityX(-speed);
        setVelocityY(-speed);
    }

    public void setOxygenLevel(float oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

//    public void render(GameContainer container, Graphics g)
//            throws SlickException {
//        img.draw(this.getPosition().x, this.getPosition().y);
//    }

    @Override
    public Fixture getFixture() {
        // TODO Auto-generated method stub
        return null;
    }
    public void setReferences(OxygenFlower flower) {
        this.flower = flower;
    }
}
