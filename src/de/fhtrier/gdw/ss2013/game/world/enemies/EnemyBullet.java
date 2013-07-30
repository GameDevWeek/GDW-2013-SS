package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.RecycleableEntity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

/**
 * Bullet Class
 * 
 * @author Kevin, Georg
 * 
 */
public class EnemyBullet extends AbstractEnemy implements ICollidable,
        RecycleableEntity {

    private Image img;
    private int livetime = 60 * 10;
    private AssetLoader a = AssetLoader.getInstance();
    private EntityManager m;

    public EnemyBullet(Vector2f pos, Vector2f velo, float dmg) {
        super(pos, velo.copy(), dmg);
        img = a.getImage("boltEnemy");
    }

    public EnemyBullet() {
        this(new Vector2f(), new Vector2f(), 0);
    }

    public EnemyBullet(Vector2f pos) {
        this(pos, new Vector2f(), 0);
    }

    @Override
    public void onCollision(Entity e) {
        if (e instanceof Astronaut) {
            ((Astronaut) e).setOxygen(((Astronaut) e).getOxygen()
                    - this.getDamage());
            this.livetime = 0;
        }
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        img.draw(this.getPosition().x - (img.getWidth() / 2),
                this.getPosition().y - (img.getHeight() / 2));

        // g.drawString(this.hashCode(), position.x, position.y);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {

        // float dt = delta / 1000.f;
        // TODO clamp dt if dt > 1/60.f ?
        this.position.x += this.getVelocity().x;
        this.position.y += this.getVelocity().y;
        if (livetime <= 0) {
            m.removeEntity(this);
        }
        livetime--;
    }

    @Override
    public Fixture getFixture() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setReferences(EntityManager m) {
        this.m = m;
    }
}
