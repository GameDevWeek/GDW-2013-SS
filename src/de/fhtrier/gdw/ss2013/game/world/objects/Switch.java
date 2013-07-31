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
public class Switch extends Entity implements Interactable {

    private boolean canBeTriggered = false;
    private boolean activated = false;

    public Switch() {
        // super(AssetLoader.getInstance().getImage("switch")); // Image does no
        // exist yet
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        // ToDo: Replace with proper function

    }

    public void setCanBeTtriggerd(boolean value) {
        canBeTriggered = value;
    }

    public void turnSwitch() {
        activated = !activated;
    }

    public boolean getSwitch() {
        return activated;
    }

    public void setSwitch(boolean value) {
        activated = value;
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
