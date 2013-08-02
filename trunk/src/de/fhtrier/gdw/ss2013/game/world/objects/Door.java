/**
 * 
 * Door class
 * @author Justin, Sandra
 *
 * erzeugt Tür-Objekt mit Zustand I|O 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.Image;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

public class Door extends Entity implements Interactable {

	private int opened;
	private Image closedImg;
	private Image openImg;

	public Door() {
		super();
		closedImg = AssetLoader.getInstance().getImage("door_closed");
		openImg = AssetLoader.getInstance().getImage("door_open");
		setImage(closedImg);
		opened = 0;
	}
	
	@Override
	protected void initialize() {
	    super.initialize();	    
	    
		float width = closedImg.getWidth();
		float height = closedImg.getHeight();
        setInitialSize(width, height);
	}

	public void activate() {
		opened++;
		if (isOpen()) {
			physicsObject.setActive(false);
			setImage(openImg);
		}
	}

	public void deactivate() {
		opened--;
		if (!isOpen()) {
			physicsObject.setActive(true);
			setImage(closedImg);
		}
	}

	public boolean isOpen() {
		return opened > 0;
	}

	@Override
	public boolean isActive() {
		return isOpen();
	}

}
