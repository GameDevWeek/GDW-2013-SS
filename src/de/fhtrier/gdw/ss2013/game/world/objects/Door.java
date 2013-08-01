/**
 * 
 * Door class
 * @author Justin, andra
 *
 * erzeugt Tür-Objekt mit Zustand I|O 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.ActivatableByAstronaut;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

public class Door extends Entity implements Interactable {

    private boolean open;

    public Door() {
        super(AssetLoader.getInstance().getImage("door_closed"));
        initialize();
    }
    
    @Override
    protected void initialize() {
        open = false;
    }

    public void activate() {
        if (!open) {
            open = true;
            Log.debug("Door opened" + this.getName());
        }
    }

    public void deactivate() {

    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean isActive() {
        return open;
    }

}
