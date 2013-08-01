package de.fhtrier.gdw.ss2013.game.player;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.enemies.AbstractEnemy;
import de.fhtrier.gdw.ss2013.game.world.enemies.EnemyBullet;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;

public class PlayerBullet extends EnemyBullet {
	private float spawny;
    private float spawnx;
    private Vector2f shootDirection;



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
	    
	    
        // FIXME: Dirty hack to get width and height of an 
        int width = AssetLoader.getInstance().getImage("boltEnemy").getWidth();
        int height = AssetLoader.getInstance().getImage("boltEnemy").getHeight();
	    
        PhysixBox box = new PhysixBox(World.getInstance().getPhysicsManager(), spawnx,
               spawny, width, height, BodyType.DYNAMIC, 1, 0.5f, true);
        setPhysicsObject(box);
        box.setLinearVelocity(shootDirection);

	}
	
	public void setSpawnXY(float x, float y) {
	    spawnx = x;
	    spawny = y;
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

    public void setShootDirection(Vector2f shootDirection) {
        this.shootDirection = shootDirection;
        
    }
}
