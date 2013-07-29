package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;

/**
 * Ground Enemy Class
 * 
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
    public void onCollision(Entity e) {
        if (e instanceof Astronaut) {
            ((Astronaut) e).setOxygen(((Astronaut) e).getOxygen()
                    - this.getDamage());
        }
    }
}
