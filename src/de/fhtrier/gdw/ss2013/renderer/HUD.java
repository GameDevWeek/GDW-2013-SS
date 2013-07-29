/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.renderer;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class HUD {
    
    private Progressbar healthbar;
    private Crosshair crosshair;
    private Annotation notation; 
    private AbilitySelection abilityWheel;
   
    
    
    public HUD()
    {
        //Init healthbar        
        healthbar = new Progressbar();
        
        final Vector2f position = new Vector2f(10, 10);
        final Vector2f size = new Vector2f(240, 40);
        final int cornerradius = 5;
        healthbar.init(position, size, cornerradius);
        
        
        
        
        
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
    {
        healthbar.update(container, game, delta);
    }
    
    public void render (GameContainer container, StateBasedGame game, Graphics g)
    {
        healthbar.render(container, game, g);
    }
}
