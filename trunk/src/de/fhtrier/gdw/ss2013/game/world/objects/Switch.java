package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.filter.ActivatableByAstronaut;

/**
 * Switch Class
 * 
 * @author Kevin, Georg
 * 
 */
public class Switch extends ObjectController implements ActivatableByAstronaut {
	
	private Image unpressedImg;
	private Image pressedImg;

    public Switch() {
        super();
        unpressedImg = AssetLoader.getInstance().getImage("switch_unpressed");
        pressedImg = AssetLoader.getInstance().getImage("switch_pressed");
        initialize();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();
        setImage(unpressedImg);
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        if (isActivated() && getImage().equals(unpressedImg)) {
        	setImage(unpressedImg);
        }
        else if (!isActivated() && getImage().equals(pressedImg)) {
        	setImage(pressedImg);
        }
    }

    public boolean getSwitch() {
        return isActivated;
    }

    public void setSwitch(boolean value) {
        isActivated = value;
    }

    public void turnSwitch() {
        if (isActivated) {
            deactivate();
        } else {
            activate();
        }
    }
}
