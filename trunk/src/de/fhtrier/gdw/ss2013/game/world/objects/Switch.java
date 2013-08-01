package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
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

    public Switch() {
        super();
        img = AssetLoader.getInstance().getImage("switch_unpressed");
        // private Image pressedImg =
        // AssetLoader.getInstance().getImage("switch_pressed");
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        // TODO: Replace with proper function
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
