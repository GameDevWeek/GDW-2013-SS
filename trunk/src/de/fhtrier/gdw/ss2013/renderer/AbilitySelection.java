/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.renderer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class AbilitySelection {
    
    private Image[] ability ;
    private Vector2f position; //ist hier jeweils der Abstand den die Position 
    private int selected;
    
    
    public AbilitySelection(){
        
        
    }
    
    public void init (Image ability1, Image ability2, Image ability3, Vector2f position){
       
       ability = new Image[3];
        
       ability[0] = ability1;
       ability[1] = ability2;
       ability[2] = ability3;
        
       this.position = position.copy(); 
        
        
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta){
        
    }
    
    
    public void render(GameContainer container, StateBasedGame game, Graphics g){
        
        ability[0].draw(container.getWidth() - ( position.x + ability[0].getWidth()), 
                       position.y);
        ability[1].draw(container.getWidth() - ( position.x + ability[0].getWidth()),
                        position.y + ability[0].getHeight() + 20);
        ability[2].draw(container.getWidth() - ( position.x + ability[0].getWidth()),
                        position.y + ability[0].getHeight() + 20 + ability[1].getHeight() + 20);
    }
    

}
