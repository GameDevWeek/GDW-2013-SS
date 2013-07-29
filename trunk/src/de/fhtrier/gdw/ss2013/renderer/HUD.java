/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.renderer;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class HUD {
    
    private Progressbar healthbar;
    private Progressbar manabar;
    
    
    public HUD()
    {
        healthbar = new Progressbar();
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
