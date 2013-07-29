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
import de.fhtrier.gdw.ss2013.interfaces.AlienControls;
import de.fhtrier.gdw.ss2013.interfaces.AstronautControls;

/**
 * Player class
 */
public class Player extends Entity implements AlienControls, AstronautControls{

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
    
    
    public void moveForward(int key)
    {
        if (key == forwardKey) 
        {
            // Vorw�rtsbewegung
        }
    }
    
    public void moveBackward(int key)
    {
        if (key == backwardKey) 
        {
            // R�ckw�rtsbewegung
        } 
    }
    
    public void jump(int key)
    {
        if(key == jumpKey)
        {
            // Springen
        }
    }
    
    public void action(int key)
    {
        if(key == actionKey)
        {
            // Aktion (Hebel)
        }
    }
    
    public void shoot(int button)
    {
        if(button == shootButton)
        {
            // Schie�en
        }
    }
    
    public void rotateAbilities(int value)
    {
        if(value == rotateWheel)
        {
            // F�higkeiten ausw�hlen
        }
    }
    
    public void useAbility(int button)
    {
        if(button == abilityButton)
        {
          // F�higkeit benutzen   
        }
    }
   
}