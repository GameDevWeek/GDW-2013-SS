package de.fhtrier.gdw.ss2013.gui.AbilityDisplay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface AbilityDisplay {
    public void update(GameContainer container, StateBasedGame game, int delta);
    
    public void render(GameContainer container, StateBasedGame game, Graphics g);
    
    public void setActivated(boolean activated);
}
