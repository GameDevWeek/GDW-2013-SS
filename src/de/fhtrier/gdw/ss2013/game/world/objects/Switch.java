package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
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
        super();
        this.setImage(AssetLoader.getInstance().getImage("switch_unpressed"));
        // private Image pressedImg = AssetLoader.getInstance().getImage("switch_pressed");
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        // TODO: Replace with proper function
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
        if(canBeTriggered) {
            super.activate();
        }
    }
    
    @Override
    public void deactivate() {
        if(canBeTriggered) {
            super.deactivate();
        }
    }
}
