package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.EntityConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.enemies.AbstractEnemy;
import de.fhtrier.gdw.ss2013.game.world.enemies.boss.AbstractBoss;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;

public class ActivatableMeteroid extends Meteroid implements Interactable {

    public ActivatableMeteroid() {
        super();
        setParticle(null);
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(PhysixManager.DENSITY).friction(PhysixManager.FRICTION)
                .category(PhysixConst.BULLET_PLAYER).mask(PhysixConst.MASK_BULLET_PLAYER)
                .fixedRotation(false)
                .linearDamping(0.1f)
                .angularDamping(0.8f)
                .asCircle(initialSize.x / 2);
        physicsObject.setGravityScale(0.5f);
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        soundPlayer.playSound(impactSound);
        if (other instanceof AbstractBoss) {
            if(isActive()) {
                ((AbstractBoss) other).reduceHealth(EntityConstants.METEORITE_DAMAGE);
                World.getInstance().getEntityManager().removeEntity(this);
            }
        }
        else if (other instanceof AbstractEnemy) {
            ((AbstractEnemy) other).reduceHealth(EntityConstants.METEORITE_DAMAGE);
            World.getInstance().getEntityManager().removeEntity(this);
        }
    }

    @Override
    public void endContact(Contact object) {
    }

    @Override
    public void activate() {
        if(!isActive())
            setParticle(AssetLoader.getInstance().getParticle("meteorid5"));
    }

    @Override
    public void deactivate() {
        setParticle(null);
    }

    @Override
    public boolean isActive() {
        return getParticle() != null;
    }
}
