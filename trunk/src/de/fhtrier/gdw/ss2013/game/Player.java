/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.MainGame;

/**
 * Player class
 */
public class Player extends Entity {

    public Player(Vector2f position) {
        super(position);
    }

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        super.render(container, g);
        g.setColor(Color.green);
        g.setLineWidth(2);
        g.drawRect(position.x - 5, position.y - 5, 10, 10);
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    }
    
   
}
