/**
 * OxygenBubble class
 * @author Justin, Sandra
 * 
 * erzeugt Sauerstoffblasen und soll Sauerstoffvorrat erhöhen
 * 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;

//import org.newdawn.slick.Image;

public class OxygenBubble extends Entity implements Interactable {

    private float oxygenLevel = 10;
    private OxygenFlower flower;
    // private AssetLoader a = AssetLoader.getInstance();
    // private Image img = a.getImage("bubble");
    private EntityManager man;
    private float speed = 20;
    private int timer = 10;

    public OxygenBubble() {
        super(AssetLoader.getInstance().getImage("bubble"));
        this.oxygenLevel = getOxygenLevel();
    }

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
        }// Collision von bubbles
        if (e instanceof OxygenBubble) {
            setVelocityX(-speed);
            setVelocityY(speed);
        }
    }

    public float getOxygenLevel() {
        return oxygenLevel;
    }

    @Override
    public void update(GameContainer container, int delta) {
        timer += delta;
        if (timer >= 400) {
            setVelocityY(0);
            setVelocityX(0);
        } else {
            setVelocityX(-speed);
            setVelocityY(-speed);
        }
    }

    public void setOxygenLevel(float oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    // public void render(GameContainer container, Graphics g)
    // throws SlickException {
    // img.draw(this.getPosition().x, this.getPosition().y);
    // }

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
