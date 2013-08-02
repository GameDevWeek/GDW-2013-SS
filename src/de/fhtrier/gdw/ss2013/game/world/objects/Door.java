/**
 * 
 * Door class
 * @author Justin, andra
 *
 * erzeugt Tür-Objekt mit Zustand I|O 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.GameDataInfo.WorldInfo;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public class Door extends Entity implements Interactable {

	private int opened;
	private Image closedImg;
	private Image openImg;
	private float startX, startY;

	public Door() {
		super();
		closedImg = AssetLoader.getInstance().getImage("door_closed");
		openImg = AssetLoader.getInstance().getImage("door_open");
		setImage(closedImg);
		opened = 0;
		// TODO: mach den Tooltip bitte weg
	}
	
	@Override
	protected void initialize() {
	    super.initialize();	    
	    
		float width = closedImg.getWidth();
		float height = closedImg.getHeight();
		float x = getStartPosition().x;
		float y = getStartPosition().y-height/1.6f;
		WorldInfo worldInfo = AssetLoader.getInstance().getGameData().world;
	    
		PhysixObject box = new PhysixBox(World.getInstance().getPhysicsManager(), x, y, width, height,
				BodyType.STATIC, worldInfo.density, worldInfo.friction,
				false);
		setPhysicsObject(box);
	}

	private Vector2f getStartPosition() {
		return new Vector2f(startX, startY);
	}

	public void setStartPosition(float x, float y) {
		this.startX = x; this.startY = y;
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
