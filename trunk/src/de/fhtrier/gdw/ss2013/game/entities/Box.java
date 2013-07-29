package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Astronaut;
import de.fhtrier.gdw.ss2013.game.Entity;

/**
 * Box class
 * 
 * @author Kevin, Georg
 * 
 */
public class Box extends Entity {

    public Box(Vector2f pos) {
        super(pos.copy());
    }

    public void onCollision(Entity e) {
        if (e instanceof Astronaut) {
            this.getPosition().x += ((Astronaut) e).getVelocity().x;
        }
    }
}
