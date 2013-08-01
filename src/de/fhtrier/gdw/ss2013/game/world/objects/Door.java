/**
 * 
 * Door class
 * @author Justin, andra
 *
 * erzeugt Tür-Objekt mit Zustand I|O 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.ActivatableByAstronaut;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

public class Door extends Entity implements Interactable {

    private boolean open;
    private long lastOpened;

    public Door() {
        super(AssetLoader.getInstance().getImage("door_closed"));
        initialize();
    }
    
    @Override
    protected void initialize() {
        open = false;
    }

    public void activate() {
        if (!open ) {
            open = true;
        }

    }

    public void deactivate() {

    }

    @Override
   public void render (GameContainer container, Graphics g) throws SlickException {
   	if(open){
   		Color filter = new Color(Color.orange);
         g.drawImage(img, getPosition().x - (img.getWidth() / 2),
            getPosition().y - (img.getHeight() / 2),filter);
   	}else {
         g.drawImage(img, getPosition().x - (img.getWidth() / 2),
            getPosition().y - (img.getHeight() / 2));
		}
   }
    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean isActive() {
        return open;
    }

}
