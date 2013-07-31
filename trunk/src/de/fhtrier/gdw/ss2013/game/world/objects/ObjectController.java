package de.fhtrier.gdw.ss2013.game.world.objects;

import java.util.HashSet;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

public abstract class ObjectController extends Entity implements Interactable {
    protected boolean isActivated;
    protected HashSet<Interactable> connectedEntities;

    protected ObjectController() {
        isActivated = false;
        connectedEntities = new HashSet<Interactable>();
    }

    public void activate() {
        for (Interactable ia : connectedEntities) {
            ia.activate();
        }
    }

    public void deactivate() {
        for (Interactable ia : connectedEntities) {
            ia.deactivate();
        }
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void connectEntity(Interactable interactable) {
        connectedEntities.add(interactable);
    }

    public void disconnectEntity(Interactable interactable) {
        connectedEntities.remove(interactable);
    }
}
