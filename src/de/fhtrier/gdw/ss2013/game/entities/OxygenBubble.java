/**
 * OxygenBubble class
 * @author Justin, Sandra
 * 
 * erzeugt Sauerstoffblasen und soll Sauerstoffvorrat erhöhen
 * 
 */

package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;

public class OxygenBubble extends Entity {

    float oxygenLevel;
    
    // Standard-Konstruktor
    public OxygenBubble() {

    }

    public OxygenBubble(Vector2f position, float oxygenLevel) {
        super(position);
        this.oxygenLevel = oxygenLevel;
    }

    public void collected() {

    }

    public void sprite() {
    }
}
