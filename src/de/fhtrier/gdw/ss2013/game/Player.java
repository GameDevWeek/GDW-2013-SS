package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.commons.interfaces.Keyboard;
import de.fhtrier.gdw.commons.interfaces.Mouse;
import de.fhtrier.gdw.ss2013.MainGame;

/**
 * Player class
 */
public class Player extends Entity implements Mouse, Keyboard {

    public Player(float x, float y) {
        super.position.set(x, y);
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
    
    
    public void keyPressed(int key, char c)
    {
        if (key == Input.KEY_A) 
        {
           
        }
        else if (key == Input.KEY_D)
        {
            
        }
    }
    
    public void keyReleased(int key, char c)
    {
        if (key == Input.KEY_W || key == Input.KEY_SPACE)
        {
            
        }
    }
    
    
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
        
    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy)
    {
        
    }

    public void mouseReleased(int button, int x, int y)
    {
        
    }

    public void mousePressed(int button, int x, int y)
    {
        
    }
    
    public void mouseWheelMoved(int newValue)
    {
        
    }
   
}
