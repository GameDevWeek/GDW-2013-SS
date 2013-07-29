package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;
/**
 * Ground Enemy Class
 * @author Kevin, Georg
 *
 */
public class GroundEnemy extends AbstractEnemy {
    
    public GroundEnemy(Vector2f pos, Vector2f velo, float dmg) {
        super(pos, velo, dmg);
    }
    
    public GroundEnemy() {
        super();
    }
    
    @Override
    public void onCollide() {
        // TODO collision handling
    }

}
