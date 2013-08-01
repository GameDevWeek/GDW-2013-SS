package de.fhtrier.gdw.ss2013.game.player;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.enemies.AbstractEnemy;
import de.fhtrier.gdw.ss2013.game.world.enemies.EnemyBullet;

public class PlayerBullet extends EnemyBullet {
	public PlayerBullet() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize() {
		super.initialize();
	    livetime = 60 * 10;
	    setDamage(PlayerConstants.BULLET_DAMAGE);
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
	}
	
	@Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof AbstractEnemy) {
            ((AbstractEnemy) other).setHealth(((AbstractEnemy) other).getHealth() - PlayerConstants.BULLET_DAMAGE);
            this.livetime = 0;
        }
        
        if(other == null) {
            World.getInstance().getEntityManager().removeEntity(this);
        }
    }
}
