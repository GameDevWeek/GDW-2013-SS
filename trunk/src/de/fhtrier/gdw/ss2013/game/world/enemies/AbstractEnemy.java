package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Abstract Enemy Class for Enemys and Meteroids
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class AbstractEnemy extends EntityCollidable {

    private float damage;
    private Animation current_ani;
    private String left_animation = "animtest", right_animation = "animtest",
            current = "animtest";

    private AssetLoader asset = AssetLoader.getInstance();

    public AbstractEnemy() {
        current_ani = asset.getAnimation(current);
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
        // Move right
        Vector2f velocity = getVelocity();
        if (velocity.x > 0
                && current_ani.equals(asset.getAnimation(right_animation))) {
            current_ani = asset.getAnimation(right_animation);

        }
        // Move left
        if (velocity.x < 0
                && current_ani.equals(asset.getAnimation(left_animation))) {
            current_ani = asset.getAnimation(left_animation);
        }
        
        current_ani.draw(this.getPosition().x - (current_ani.getWidth()/2), this.getPosition().y - (current_ani.getHeight()/2));
    }

    public void setLeft_animation(String left_animation) {
        this.left_animation = left_animation;
    }

    public void setRight_animation(String right_animation) {
        this.right_animation = right_animation;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getLeft_animation() {
        return left_animation;
    }

    public String getRight_animation() {
        return right_animation;
    }
}
