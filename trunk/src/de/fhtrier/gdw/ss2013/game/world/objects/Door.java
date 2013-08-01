/**
 * 
 * Door class
 * @author Justin, andra
 *
 * erzeugt Tür-Objekt mit Zustand I|O 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.Image;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

public class Door extends Entity implements Interactable {

    private boolean open;
    private Image closedImg;
    private Image openImg;

    public Door() {
        super();
        closedImg = AssetLoader.getInstance().getImage("door_closed");
        openImg = AssetLoader.getInstance().getImage("door_open");
        setImage(closedImg);
        // TODO: mach den Tooltip bitte weg
    }
    
    @Override
    protected void initialize() {
        open = false;
    }

    public void activate() {
        if (!open ) {
            open = true;
            physicsObject.setActive(false);
            setImage(openImg);
        }
    }

    public void deactivate() {
    	if (open) {
    		open = false;
    		physicsObject.setActive(true);
    		setImage(closedImg);
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
