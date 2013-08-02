package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.filter.ActivatableByAstronaut;
import org.jbox2d.dynamics.BodyType;

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
    }

    @Override
    public boolean isBottomPositioned() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();
        setImage(unpressedImg);
        setInitialSize(unpressedImg.getWidth(), unpressedImg.getHeight());
    }
    
    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y).sensor(true)
                .asBox(initialSize.x, initialSize.y);
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        super.update(container, delta);
        if (!isActivated()) {
        	setImage(unpressedImg);
        }
        else if (isActivated()) {
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
