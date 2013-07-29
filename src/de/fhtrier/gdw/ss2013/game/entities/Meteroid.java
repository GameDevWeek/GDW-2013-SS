package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;

/**
 * Meteroid class
 * @author Kevin, Georg
 *
 */
public class Meteroid extends AbstractEnemy {

    private float health;
    
    public Meteroid(Vector2f pos, Vector2f velo, float dmg, float hp) {
        super(pos.copy(), velo.copy(), dmg);
        health = hp;
    }
    
    public Meteroid() {
        this(new Vector2f(), new Vector2f(), 0, 0);
    }
    public Meteroid(Vector2f pos) {
        this(pos.copy(), new Vector2f(), 0, 0);
    }
    @Override
    public void onCollide() {
        // TODO Auto-generated method stub

    }
    public void reduceHealth(float dmg) {  
        health -= dmg;
    }
}
