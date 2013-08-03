package de.fhtrier.gdw.ss2013.gui.AbilityDisplay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;



public class StaticAbilityDisplay implements AbilityDisplay{
    private Image image;
    private Vector2f position;
    
    private boolean activated;
    
    private float startAlphaValue;
    private float alphaValue;
    private float alphaSeed;
    private float fadingSpeed;
    
    public StaticAbilityDisplay(Image image, Vector2f position, float fadingSpeed)
    {
        init(image, position, fadingSpeed);
    }
    
    public void init(Image image, Vector2f position, float fadingSpeed)
    {
        this.image = image.copy();
        this.position = position;
        this.fadingSpeed = fadingSpeed;
        
        alphaSeed = 0;
        
        alphaValue = 0;
        
        startAlphaValue = 0.5f;
        
        
        while(alphaValue < startAlphaValue)
        {
            alphaSeed+=fadingSpeed;
            alphaValue = computeAlphaValue(alphaSeed);  
        }
    }
     
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
      //wenn diese Anzeige aktiviert ist, soll die Transperenz soweit heruntergeregelt werden, bis es gar nicht mehr transperent ist.
        if (activated)
        {
            if(alphaValue < 1.0f)
            {
                alphaSeed+=delta*fadingSpeed;
                alphaValue = computeAlphaValue(alphaSeed);
            }
        }
        
      //wenn diese Anzeige nicht aktiviert ist, soll die Transperenz so weit hochgereglet werden, bis es nur noch halb transperent ist.
        else
        {
            if(alphaValue > 0.5f)
            {
                alphaSeed-=delta*fadingSpeed;
                alphaValue = computeAlphaValue(alphaSeed);  
            }
        }
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        image.setAlpha(alphaValue);
        image.draw(container.getWidth() - position.x,position.y);
    }
    
    @Override
    public void setActivated(boolean activated)
    {
        this.activated=activated;
    }
    
    protected float computeAlphaValue(float x)
    {
        if (x <= 0)
            return 0.f;
        else if (x >= 1000)
            return 1.f;
        else
        {
            return (x*x*(1.f/1000000.f));
            
        }
    }
}
