package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class DynamicAbilityDisplay extends AbilityDisplay {

    private int countdown_start;
    private int countdown_timer = 0;
    private World worldinstance;
    
    public DynamicAbilityDisplay(Image image,float fadingSpeed, World worldinstance) {
        init(image, fadingSpeed, worldinstance);
    }
        
                        
        public void init(Image image,float fadingSpeed, World worldinstance) {

            this.countdown_start = 1000;
            
            this.worldinstance = worldinstance;

            alphaSeed = 0;
            
            alphaValue = 0;
            
            startAlphaValue = 0.0f;
            
            this.fadingSpeed= fadingSpeed;
 
            activated = false;
            this.image = image;

        }

       //public void render(GameContainer container, StateBasedGame game, Graphics g) {
            
            /*
            if (countdown_timer > 0) {
                Vector2f p = worldinstance.worldToScreenPosition(worldinstance.getAstronaut().getPosition());
                ability[selected - 1].draw(p.x   - ability[selected-1].getWidth()/2
                                           ,p.y - worldinstance.getAstronaut().getAnimation().getHeight()/2);
            }*/


        public void update(GameContainer container, StateBasedGame game, int delta) {
            if (activated)
            {
                if (countdown_timer > 0) {
                    countdown_timer -= delta;
                }
                else
                {
                    if(alphaValue > 0.0f)
                    {
                        alphaSeed+=delta*fadingSpeed;
                        alphaValue = computeAlphaValue(alphaSeed);
                    }
                }
                    
            }
            else //if not acitivated
            {
                //do nothing.
            }
            
            
            if (countdown_timer > 0) {
                countdown_timer -= delta;
            }

        }
        
        protected float computeAlphaValue(float x)
        {
            if (x <= 0)
                return 1.f;
            else if (x >= 1000)
                return 0.f;
            else
            {
                return (1.0f-x*(1.0f/1000.0f));
            }
        }
        
        public void render(GameContainer container, StateBasedGame game, Graphics g) {
            
            if(activated)
            {
                System.out.println("hey");
                image.setAlpha(alphaValue);
                Vector2f p = worldinstance.worldToScreenPosition(worldinstance.getAstronaut().getPosition());
                
                
                //image.draw(p.x   - image.getWidth()/2
                //                           ,p.y - worldinstance.getAstronaut().getAnimation().getHeight()/2);
                
                image.draw(p.x  - image.getWidth()/2,p.y- worldinstance.getAstronaut().getAnimation().getHeight()/2);                
            
            }
            else
            {
                //do nothing
            }
            
        }

        public void setActivated(boolean activated)
        {
            this.activated=activated;
            if (activated)
            {
                alphaSeed=0.0f;
                alphaValue=1.0f;
                countdown_timer = countdown_start;
            }
            else // if(!activated)
            {
                alphaValue=0.0f;
                countdown_timer=0;
            }
        }
       /* public void setSelected(int selected) {
            if (this.selected != selected) {
                this.selected = selected;
                countdown_timer = countdown_start; // counter zurücksetzen

            }*/

  }

    

