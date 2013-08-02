package de.fhtrier.gdw.ss2013.game.world.bullets;

import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.world.enemies.AbstractEnemy;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;

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

    @Override
    protected short getCategory() {
        return PhysixConst.BULLET_PLAYER;
    }

    @Override
    protected short getMask() {
        return PhysixConst.MASK_BULLET_PLAYER;
    }
}
