package de.fhtrier.gdw.ss2013.game.world.objects;

import java.util.HashSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.world.World;

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

    private boolean firstUpdateDone = false;

    protected ObjectController() {
        connectedEntities = new HashSet<Interactable>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();

        connectedEntities = new HashSet<>();

        if (properties != null) {
            isActivated = properties.getBoolean("isActive", false);
        }
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        if (!firstUpdateDone) {
            if (properties != null) {
                if (!properties.getProperty("trigger", "").isEmpty()) {
                    String[] triggers = properties.getProperty("trigger", "")
                            .split(",");
                    for (String triggerName : triggers) {
                        Entity toTrigger = World.getInstance()
                                .getEntityManager()
                                .getEntityByName(triggerName);
                        if (toTrigger instanceof Interactable) {
                            connectEntity((Interactable) toTrigger);
                        } else {
                            System.err
                                    .println(getName()
                                            + " trys to connect a Not Interactable Entity "
                                            + triggerName);
                        }
                    }
                }
            }
            firstUpdateDone = true;
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
