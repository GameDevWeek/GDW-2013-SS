package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Player class
 */
public class Player extends Entity {

    public Player(float x, float y) {
        position.set(x, y);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.green);
        g.setLineWidth(2);
        g.drawRect(position.x - 5, position.y - 5, 10, 10);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    }
}
