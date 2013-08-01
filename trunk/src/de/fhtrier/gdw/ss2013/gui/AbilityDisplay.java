package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbilityDisplay {
    protected Image image;
    protected Vector2f position;
    
    protected boolean activated;
    
    protected float startAlphaValue;
    protected float alphaValue;
    protected float alphaSeed;
    protected float fadingSpeed;
    
    public void update(GameContainer container, StateBasedGame game, int delta) {
        
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        
    }
    
    public void setActivated(boolean activated) {
        
    }
     
    public AbilityDisplay() {
        // TODO Auto-generated constructor stub
    }

}
