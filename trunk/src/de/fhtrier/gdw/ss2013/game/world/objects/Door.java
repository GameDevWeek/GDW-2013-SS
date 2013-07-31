/**
 * 
 * Door class
 * @author Justin, andra
 *
 * erzeugt Tür-Objekt mit Zustand I|O 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

public class Door extends Entity implements Interactable {

	private boolean open;
	
	public Door() {
	    // super(AssetLoader.getInstance().getImage("door")); // Image does not exist yet
	}

	public void activate() {
		if (!open) {
			open = true;
		}
	}
	
	public void deactivate() {
	    
	}

	public boolean isOpen() {
		return open;
	}

}
