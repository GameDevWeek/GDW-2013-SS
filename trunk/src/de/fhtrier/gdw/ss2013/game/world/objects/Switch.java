package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

/**
 * Switch Class
 * 
 * @author Kevin, Georg
 * 
 */
public class Switch extends ObjectController implements Interactable {

    private boolean canBeTriggered = false;

    public Switch() {
        // super(AssetLoader.getInstance().getImage("switch")); // Image does no
        // exist yet
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        // ToDo: Replace with proper function

    }

    public void setCanBeTriggered(boolean value) {
        canBeTriggered = value;
    }

    public void turnSwitch() {
        isActivated = !isActivated;
    }

    public boolean getSwitch() {
        return isActivated;
    }

    public void setSwitch(boolean value) {
        isActivated = value;
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
        
    }
}
