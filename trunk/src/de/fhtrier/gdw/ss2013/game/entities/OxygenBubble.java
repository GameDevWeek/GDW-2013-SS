/**
 * OxygenBubble class
 * @author Justin, Sandra
 * 
 * erzeugt Sauerstoffblasen und soll Sauerstoffvorrat erhöhen
 * 
 */

package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Astronaut;
import de.fhtrier.gdw.ss2013.game.Entity;

public class OxygenBubble extends Entity {

    private float oxygenLevel;

    // Standard-Konstruktor
    public OxygenBubble() {

    }

    public OxygenBubble(Vector2f position, float oxygenLevel) {
        super(position.copy());
        this.oxygenLevel = oxygenLevel;
    }

    public void collected(Astronaut astro) {
        float currentOxygen = astro.getOxygen();
        if (currentOxygen + oxygenLevel < astro.getMaxOxygen()) {
            astro.setOxygen(currentOxygen + oxygenLevel);
        } else {
            astro.setOxygen(astro.getMaxOxygen());
        }

    }

    public void sprite() {
    }
}
