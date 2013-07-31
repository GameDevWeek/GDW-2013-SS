package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.RecycleableEntity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;

/**
 * Bullet Class
 * 
 * @author Kevin, Georg
 * 
 */
public class EnemyBullet extends AbstractEnemy implements
        RecycleableEntity {

    private Image img;
    private int livetime = 60 * 10;
    private AssetLoader a = AssetLoader.getInstance();
    private EntityManager m;

    public EnemyBullet() {
    	super(null);
        img = a.getImage("boltEnemy");
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        img.draw(this.getPosition().x - (img.getWidth() / 2),
                this.getPosition().y - (img.getHeight() / 2));

        // g.drawString(this.hashCode(), position.x, position.y);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {

        if (livetime <= 0) {
            m.removeEntity(this);
        }
        livetime--;
    }

    public void setReferences(EntityManager m) {
        this.m = m;
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut) {
            ((Astronaut) other).setOxygen(((Astronaut) other).getOxygen()
                    - this.getDamage());
            this.livetime = 0;
        }
    }

    @Override
    public void endContact(Contact object) {
    }
}
