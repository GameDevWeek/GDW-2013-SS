/**
 * 
 * Door class
 * @author Justin, Sandra
 *
 * erzeugt Tür-Objekt mit Zustand I|O 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

public class Door extends EntityCollidable implements Interactable {

	private int opened;
	private Image closedImg;
	private Image openImg;
	private SoundPlayer soundPlayer;
	private Sound doorSoundHit;
	private Sound doorSoundOpen;
	private Sound doorSoundClosed;

	public Door() {
		super();
		closedImg = AssetLoader.getInstance().getImage("door_closed");
		openImg = AssetLoader.getInstance().getImage("door_open");
		setImage(closedImg);
		opened = 0;
	}

    @Override
    public boolean isBottomPositioned() {
        return true;
    }
	
	@Override
	protected void initialize() {
	    super.initialize();	    
	    
		float width = closedImg.getWidth();
		float height = closedImg.getHeight();
        setInitialSize(width, height);
        
        soundPlayer = SoundLocator.getPlayer();
        doorSoundHit = SoundLocator.loadSound("box_hit_weak");
        doorSoundOpen = SoundLocator.loadSound("dooropen");
        doorSoundClosed = SoundLocator.loadSound("doorclosed");
        
        if (properties != null) {
            String tileset = properties.getProperty("tileset", "")
                    .toLowerCase();
            if (!tileset.isEmpty()) {
                closedImg = AssetLoader.getInstance().getImage(
                        tileset + "_door_closed");
                openImg = AssetLoader.getInstance().getImage(
                        tileset + "_door_open");
            }
        }
	}

    @Override
	public void activate() {
		opened++;
		if (isOpen()) {
			physicsObject.setActive(false);
			setImage(openImg);
			doorSoundClosed.stop();
			soundPlayer.playSoundAt(doorSoundClosed, this);
		}
	}

    @Override
	public void deactivate() {
		opened--;
		if (!isOpen()) {
			physicsObject.setActive(true);
			setImage(closedImg);
	        doorSoundClosed.stop();
			soundPlayer.playSoundAt(doorSoundClosed, this);
		}
	}

	public boolean isOpen() {
		return opened > 0;
	}

	@Override
	public boolean isActive() {
		return isOpen();
	}
	
	@Override
	public void beginContact(Contact other) {
	    if (other != null) {
	        soundPlayer.playSoundAt(doorSoundHit, this);
	    }
	}

    @Override
    public void endContact(Contact object) {
        // TODO Auto-generated method stub
        
    }

}
