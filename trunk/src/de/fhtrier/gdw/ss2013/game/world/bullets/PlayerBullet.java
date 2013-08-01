package de.fhtrier.gdw.ss2013.game.world.bullets;

import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.world.enemies.AbstractEnemy;

public class PlayerBullet extends Bullet {

    public PlayerBullet() {
		super();
	}
	
	@Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof AbstractEnemy) {
            ((AbstractEnemy) other).setHealth(((AbstractEnemy) other).getHealth() - PlayerConstants.BULLET_DAMAGE);
            this.livetime = 0;
        }
        
        super.checkForUnwantedContacts(contact);   
    }
}
