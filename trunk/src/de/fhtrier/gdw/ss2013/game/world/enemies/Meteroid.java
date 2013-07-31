package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Meteroid class
 * 
 * @author Kevin, Georg
 * 
 */
public class Meteroid extends AbstractEnemy {

    private EntityManager m;
	final static float DEBUG_ENTITY_HALFEXTEND = 5;

	public Meteroid() {
		super(AssetLoader.getInstance().getAnimation("meteorite"));
	    this.m = World.getInstance().getEntityManager();
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
		float dt = delta / 1000.f;
		// TODO clamp dt if dt > 1/60.f ?
		this.getPosition().x += this.getVelocity().x * dt;
		this.getPosition().y += this.getVelocity().y * dt;

		if (getPosition().y > 5000) { // FIXME: rausnehmen, wenn Objekt aus dem Level raus ist
	         m.removeEntity(this);
	    }
	}

	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
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
        }
    }

    @Override
    public void endContact(Contact object) {
    }
}
