package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;
/**
 * Flying Enemy Class
 * @author Kevin, Georg
 *
 */
public class FlyingEnemy extends AbstractEnemy {

    private float health;
    private boolean alive;
    
    public FlyingEnemy(Vector2f pos, Vector2f velo, float dmg, float hp) {
        super(pos, velo, dmg);
        health = hp;
    }
    
    public FlyingEnemy() {
        super();
        health = 0;
    }
    
    @Override
    public void onCollide() {
        // TODO collision handling  
    }
    
    public void reduceHealth(float dmg) {  
        health -= dmg;
    }

}
