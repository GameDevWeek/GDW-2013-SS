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


public class OxygenBubble extends Entity implements ICollidable {

    private float oxygenLevel;
    private OxygenFlower flower;
    private AssetLoader a = AssetLoader.getInstance();
    private Image img = a.getImage("bubble");
    private Vector2f velocity = new Vector2f(10, 10);
    private EntityManager man;

    // Standard-Konstruktor
    public OxygenBubble(Vector2f position) {
        super(position.copy());
        a.getImage("bubble");
        // Default
        oxygenLevel = 0;
    }

    public OxygenBubble(Vector2f position, float oxygenLevel) {
        super(position.copy());
        this.oxygenLevel = oxygenLevel;
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
            if(e.getPosition().x <= this.position.x)
            {
                this.position.x -= this.velocity.x;
            }
            if(e.getPosition().y <= this.position.y)
            {
                this.position.y -= this.velocity.y;
            }
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
    
        if(this.position.x >= this.velocity.x || this.position.y > this.velocity.y)
        {
            this.position.x -= this.velocity.x;
            this.position.y -= this.velocity.y;
        }
        else if(this.position.x < this.velocity.x || this.position.y < this.velocity.y){
            this.position.x += this.velocity.x;
            this.position.y += this.velocity.y;
        }
    }

    public void setOxygenLevel(float oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        img.draw(this.getPosition().x, this.getPosition().y);
    }

    @Override
    public Fixture getFixture() {
        // TODO Auto-generated method stub
        return null;
    }
    public void setReferences(OxygenFlower flower) {
        this.flower = flower;
    }
}
