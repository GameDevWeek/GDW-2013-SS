package de.fhtrier.gdw.ss2013.game.world.bullets;

import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;

/**
 * Bullet Class
 * 
 * @author Kevin, Georg
 * 
 */
public class EnemyBullet extends Bullet {

    public EnemyBullet() {
        super();
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut) {
            ((Astronaut) other).setDamage(this.getDamage());
            this.livetime = 0;
        }

        super.checkForUnwantedContacts(contact);
    }

    @Override
    protected short getCategory() {
        return PhysixConst.BULLET_ENEMY;
    }

    @Override
    protected short getMask() {
        return PhysixConst.MASK_BULLET_ENEMY;
    }
}
