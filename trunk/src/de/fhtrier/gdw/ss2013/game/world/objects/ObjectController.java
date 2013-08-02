package de.fhtrier.gdw.ss2013.game.world.objects;

import java.util.HashSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

/**
 * {@link Entity}, which de/activates connected interactable Entities,<br>
 * when getting de/activated itself<br>
 * <br>
 * Examples: {@link Button}, {@link Switch}<br>
 * 
 * @author BreakingTheHobbit
 * 
 * @see Entity
 * @see Interactable
 * @see Switch
 * @see Button
 */
public abstract class ObjectController extends Entity implements Interactable {
    protected boolean isActivated;
    protected HashSet<Interactable> connectedEntities;

    protected ObjectController() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();
        isActivated = false;
        connectedEntities = new HashSet<>();
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        for (Interactable ia : connectedEntities) {
            ia.isActive();
        }
        super.update(container, delta);
    }

    @Override
    public boolean isActive() {
        return isActivated;
    }

    /**
     * activate all connected Entities
     */
    @Override
    public void activate() {
        isActivated = true;
        for (Interactable ia : connectedEntities) {
            ia.activate();
        }
    }

    /**
     * deactivate all connected Entities
     */
    @Override
    public void deactivate() {
        isActivated = false;
        for (Interactable ia : connectedEntities) {
            ia.deactivate();
        }
    }

    /**
     * @return is this Entity currently activated
     */
    public boolean isActivated() {
        return isActivated;
    }

    /**
     * connect interactable Entity to this Controller
     * 
     * @param interactable
     *            interactable Entity to be connected
     */
    public void connectEntity(Interactable interactable) {
        connectedEntities.add(interactable);
    }

    /**
     * disconnect interactable Entity from this Controller
     * 
     * @param interactable
     *            interactable Entity to be disconnected
     */
    public void disconnectEntity(Interactable interactable) {
        connectedEntities.remove(interactable);
    }
}
