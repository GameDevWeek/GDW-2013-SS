package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.constants.EnemyConstants;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.bullets.EnemyBullet;
import de.fhtrier.gdw.ss2013.game.world.bullets.PlayerBullet;

/**
 * Flying Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class FlyingEnemy extends AbstractEnemy {
    private Astronaut player;
    private float interval;

    public FlyingEnemy(Animation animation) {
    	super(animation);
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.player = World.getInstance().getAstronaut();
        this.interval = 0f;
    }

    public void reduceHealth(float dmg) {
        setHealth(getHealth() - dmg);
    }

    /**
     * Creates an enemy shot to attack the player
     * @param shootDirection The direction from the flying enemy towards the player
     */
    public void shoot(Player player) {
    	Vector2f shootDirection = new Vector2f();
    	shootDirection = player.getPosition().sub(getPosition()).normalise().scale(PlayerConstants.BULLET_SPEED);
//    	System.out.println(player.getPosition().sub(getPosition()));
//    	System.out.println(player.getPosition());

		EntityManager entityManager = World.getInstance().getEntityManager();

		// Create a meteorite entity
		EnemyBullet bullet = entityManager.createEntity(EnemyBullet.class);
		bullet.setSpawnXY(getPosition().x, getPosition().y);
		bullet.setShootDirection(shootDirection);
    }
    
    public boolean enterAttackZone(float value) {
        float distance = player.getPosition().sub(getPosition()).length();
        
        if (distance < value) {
            return true;
        }
        return false;
    }

    public void update(GameContainer container, int delta) throws SlickException {
    	super.update(container, delta);
    	interval += delta;
    	if (player == null || player.getOxygen() <= 0) {
    	    player = World.getInstance().getAstronaut();
    	}
    	if (interval >= EnemyConstants.ENEMY_SHOOTING_INTERVAL) {
    	    if (enterAttackZone(EnemyConstants.ENEMY_SHOOTING_DISTANCE)) {
    	        shoot(player);
    	        interval %= EnemyConstants.ENEMY_SHOOTING_INTERVAL;
    	    }
    	}
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
