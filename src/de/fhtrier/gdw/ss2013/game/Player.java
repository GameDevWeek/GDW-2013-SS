/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.input.AlienControls;
import de.fhtrier.gdw.ss2013.input.AstronautControls;
import de.fhtrier.gdw.ss2013.util.AssetLoader;

/**
 * Player class
 */
public class Player extends Entity implements AlienControls, AstronautControls{
   
    Animation bewegungs_ani;
    private SpriteSheet sheet;
    private Animation animation;
    private String zustand="animtest";
    private Vector2f velocity;
    AssetLoader asset=new AssetLoader();
    
    public Player(Vector2f position) {
        super(position);
        velocity = new Vector2f();
        //animation von assetloader beziehen
       
       bewegungs_ani=asset.getAnimation(zustand);
        
    }

    
    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
       /*super.render(container, g);
        g.setColor(Color.green);
        g.setLineWidth(2);
        g.drawRect(position.x - 5, position.y - 5, 10, 10);
       */
        bewegungs_ani.draw(position.x,position.y);
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    }
    

 /*  public enum Bewegung{
       forward, backward, jump, still, sprint; 
   }*/

    public void moveForward(int key)
    {
        if (key == forwardKey) 
        {
            // Vorwärtsbewegung
            zustand="animtest";
        }
    }
    
    public void moveBackward(int key)
    {
        if (key == backwardKey) 
        {
            // Rückwärtsbewegung
            zustand="animtest";
        } 
    }
    
    public void jump(int key)
    {
        if(key == jumpKey)
        {
            // Springen
            zustand="animtest";
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
            // Schießen
        }
    }
    
    public void rotateAbilities(int value)
    {
        if(value == rotateWheel)
        {
            // Fähigkeiten ausw�hlen
        }
    }
    
    public void useAbility(int button)
    {
        if(button == abilityButton)
        {
          // Fähigkeit benutzen   
        }
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }
   
  /* public void ani()
   {
       switch(zustand)
       {
       case "forward": for_ani.draw();
           break;
       case "backward": back_ani.draw();
           break;
       case "jump": jump_ani.draw();
           break; 
       default: still_ani.draw();
           break;
           
       }
   }*/
   
  
}
