package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;
/**
 * Ground Enemy Class
 * @author Kevin, Georg
 *
 */
public class GroundEnemy extends AbstractEnemy {
    
    public GroundEnemy(Vector2f pos, Vector2f velo, float dmg) {
        super(pos.copy(), velo.copy(), dmg);
    }
    
    public GroundEnemy() {
        this(new Vector2f(), new Vector2f(), 0);
    }
    public GroundEnemy(Vector2f pos) {
        this(pos.copy(), new Vector2f(), 0);
    }
    @Override
    public void onCollide() {
        // TODO collision handling
    }

}
