/**
 * 
 * OxygenFlower class
 * @author Justin, Sandra
 * 
 * Blume erzeugt Blasen und Blasenvorrat verringert sich
 */
package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.Image;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;


public class OxygenFlower extends Entity implements Interactable {

    
    private AssetLoader a = AssetLoader.getInstance();
    private Image img;
    
    private int maxBubble;
    private int count;

    public OxygenFlower(Vector2f position) {
        super(position.copy());
        img = a.getImage("plant");
        // Default
        maxBubble = 5;

    }

    public OxygenFlower(Vector2f position, int maxBubble) {

        super(position);
        this.maxBubble = maxBubble;
    }

    public void shootBubbles(EntityManager manager) {
        float x = this.getPosition().getX() - 20;
        float y = this.getPosition().getY() + 11;

        while (count < maxBubble) {

            Vector2f bubblePos = new Vector2f(x, y);
            x += (Math.random()*100);
            y += (Math.random()*100);
            manager.createEntityAt(OxygenBubble.class, bubblePos.copy());
            count++;
        }

    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {

        g.setColor(Color.pink);
        img.draw(this.getPosition().x-(img.getWidth()/2), this.getPosition().y-(img.getHeight()/2));
        super.render(container, g);

        // g.drawString(this.hashCode(), position.x, position.y);
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
