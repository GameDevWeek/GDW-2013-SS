package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.constants.EnemyConstants;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.world.World;

/**
 * Abstract Enemy Class for Enemys and Meteroids
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class AbstractEnemy extends EntityCollidable {

	private float damage;
	private float health;
	protected Animation current_ani;
	private Animation leftAnimation, rightAnimation;

	public AbstractEnemy(Animation moveToRightAnimation) {
		this.rightAnimation = moveToRightAnimation;
		leftAnimation = rightAnimation; // FIXME: flip me, baby!
	    current_ani = rightAnimation;
	    damage = 0;
	    setHealth(EnemyConstants.ENEMY_HEALTH);
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
	protected void initialize() {
	    super.initialize();
	}

    @Override
    public void initPhysics() {
		float width = current_ani.getWidth();
		float height = current_ani.getHeight();
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(1).friction(1)
                .asBox(width, height);
    }

	public float getDamage() {
		return damage;
	}

	public void setDamage(float dmg) {
		damage = dmg;
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		
        if (getHealth() <= 0) {
            World.getInstance().getEntityManager().removeEntity(this);
			World.getScoreCounter().addScore(5);
        }
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// FIXME: change ani?

		if (physicsObject != null && current_ani != null) {
			current_ani.draw(this.getPosition().x - (current_ani.getWidth() / 2),
					this.getPosition().y - (current_ani.getHeight() / 2));
		}
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
}
