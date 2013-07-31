/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class Crosshair {

     private Image img;
    private World worldinstance;
    
    
    public Crosshair() {
        // this.img = AssetLoader.getInstance().getImage("ability1");
    }

    public void init(World worldinstance, Image img){
        
        this.worldinstance = worldinstance;
        this.img = img;
        
    
    }
    
    
    public void update() {

    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {

        img.draw(worldinstance.getAlien().getCursor().x-img.getWidth()/2, worldinstance.getAlien().getCursor().y-img.getHeight()/2);

    }
}
