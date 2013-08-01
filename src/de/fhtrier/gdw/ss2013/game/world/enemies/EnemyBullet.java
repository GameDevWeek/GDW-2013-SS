package de.fhtrier.gdw.ss2013.game.world.enemies;

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
import de.fhtrier.gdw.ss2013.game.world.World;

/**
 * Bullet Class
 * 
 * @author Kevin, Georg
 * 
 */
public class EnemyBullet extends AbstractEnemy implements
        RecycleableEntity {

    private Image img;
    protected int livetime = 60 * 10;
    private EntityManager m;

    public EnemyBullet() {
    	super(null);
    	img = AssetLoader.getInstance().getImage("boltEnemy");
    	m = World.getInstance().getEntityManager();
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        img.draw(this.getPosition().x - (img.getWidth() / 2),
                this.getPosition().y - (img.getHeight() / 2));

        // g.drawString(this.hashCode(), position.x, position.y);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
    	float x = physicsObject.getX();
    	float y = physicsObject.getY();
    	physicsObject.setPosition(x+getVelocity().x, y+getVelocity().y);
    	
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
