package de.fhtrier.gdw.ss2013.game.player;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.world.enemies.EnemyBullet;
import de.fhtrier.gdw.ss2013.game.world.enemies.FlyingEnemy;

public class PlayerBullet extends EnemyBullet {
	public PlayerBullet() {
		super();
	}
	
	@Override
	protected void initialize() {
		this.livetime = 60 * 10;
		super.initialize();
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
	}
	
	@Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof FlyingEnemy) {
            ((FlyingEnemy) other).reduceHealth(PlayerConstants.BULLET_DAMAGE);
            this.livetime = 0;
        }
    }
}
