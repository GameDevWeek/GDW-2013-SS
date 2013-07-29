/**
 * 
 * OxygenFlower class
 * @author Justin, Sandra
 * 
 * Blume erzeugt Blasen und Blasenvorrat verringert sich
 */
package de.fhtrier.gdw.ss2013.game.world.objects;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;

public class OxygenFlower extends Entity {

    private int maxBubble;

    private ArrayList<OxygenBubble> bubbles = new ArrayList<OxygenBubble>();

    public OxygenFlower(Vector2f position) {
        super(position.copy());

        // Default
        maxBubble = 5;

    }

    public OxygenFlower(Vector2f position, int maxBubble) {

        super(position);
        this.maxBubble = maxBubble;
    }

    public void shootBubbles(EntityManager manager) {

        int count = maxBubble;
        float x = this.getPosition().getX() - 20;
        float y = this.getPosition().getY() + 11;

        for (int i = 0; i < count; i++) {

            Vector2f bubblePos = new Vector2f(x, y);
            OxygenBubble bubble = new OxygenBubble(bubblePos.copy());
            x += 10;
            bubbles.add(bubble);
            manager.createEntityAt(OxygenBubble.class, bubblePos.copy());
            maxBubble--;
        }

    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {

        g.setColor(Color.pink);
        super.render(container, g);

        // g.drawString(this.hashCode(), position.x, position.y);
    }

    public void setMaxBubble(int maxBubble) {
        this.maxBubble = maxBubble;
    }

    public ArrayList<OxygenBubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(ArrayList<OxygenBubble> bubbles) {
        this.bubbles = bubbles;
    }

    public int getMaxBubble() {
        return maxBubble;
    }

}
