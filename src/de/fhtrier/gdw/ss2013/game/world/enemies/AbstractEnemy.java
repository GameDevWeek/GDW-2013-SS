package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.util.AssetLoader;

/**
 * Abstract Enemy Class for Enemys and Meteroids
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class AbstractEnemy extends Entity {

    private Vector2f velocity;
    private float damage;
    private Animation rechts_ani, links_ani;
    private Animation current_ani;
    private AssetLoader asset=new AssetLoader();
    public AbstractEnemy() {
        this(new Vector2f(), new Vector2f(), 0);
    }

    public AbstractEnemy(Vector2f pos) {
        this(pos.copy(), new Vector2f(), 0);
        rechts_ani=asset.getAnimation("animtes");
        links_ani=asset.getAnimation("animtes");
    }

    public AbstractEnemy(Vector2f pos, Vector2f velo, float dmg) {
        super(pos.copy());
        this.velocity = velo.copy();
        this.damage = dmg;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float dmg) {
        damage = dmg;
    }

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        // TODO Auto-generated method stub
        if (velocity.x > 0) {
            current_ani = rechts_ani;
        }
        if (velocity.x < 0) {
            current_ani = links_ani;
        }
        current_ani.draw(this.getPosition().x, this.getPosition().y);

    }
}
