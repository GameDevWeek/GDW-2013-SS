package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;

/**
 * Abstract Enemy Class for Enemys and Meteroids
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class AbstractEnemy extends Entity {

    private Vector2f velocity;
    private float damage;
    private Animation current_ani;
    private String left_animation = "animtest", right_animation = "animtest",
            current = "animtest", oben = "animtest", unten = "animtest";

    private AssetLoader asset = AssetLoader.getInstance();

    public AbstractEnemy() {
        this(new Vector2f(), new Vector2f(), 0);
        current_ani = asset.getAnimation(current);

    }

    public AbstractEnemy(Vector2f pos) {
        this(pos.copy(), new Vector2f(), 0);
        current_ani = asset.getAnimation(current);

    }

    public AbstractEnemy(Vector2f pos, Vector2f velo, float dmg) {
        super(pos.copy());
        this.velocity = velo.copy();
        this.damage = dmg;
        current_ani = asset.getAnimation(current);

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
        // links-rechts
        // rechts-links

        if (velocity.x > 0
                && current_ani.equals(asset.getAnimation(right_animation))) {
            current_ani = asset.getAnimation(right_animation);

        }
        if (velocity.x < 0
                && current_ani.equals(asset.getAnimation(left_animation))) {
            current_ani = asset.getAnimation(left_animation);

            // oben-unten
        }
        /*if (velocity.x > 0) {
            current_ani = asset.getAnimation(right_animation);

        }
        if (velocity.x < 0) {
            current_ani = asset.getAnimation(left_animation);

        }*/
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

    public void setOben(String oben) {
        this.oben = oben;
    }

    public void setUnten(String unten) {
        this.unten = unten;
    }

}
