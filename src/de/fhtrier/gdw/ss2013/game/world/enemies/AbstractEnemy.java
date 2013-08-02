package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.constants.EnemyConstants;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;

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
	private boolean invertAni;

	public AbstractEnemy(Animation current_ani) {
		this.current_ani = current_ani;
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
		createPhysics(BodyType.DYNAMIC, origin.x, origin.y).density(1).friction(1).category(PhysixConst.ENEMY)
				.mask(PhysixConst.MASK_ENEMY).asBox(width, height);
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
		invertAni = getVelocity().x < 0;

		if (physicsObject != null && current_ani != null) {
			if (invertAni) {
				current_ani.draw(this.getPosition().x + current_ani.getWidth() / 2,
						this.getPosition().y - current_ani.getHeight() / 2, -current_ani.getWidth(),
						current_ani.getHeight());
			}
			else {
				current_ani.draw(this.getPosition().x - (current_ani.getWidth() / 2), this.getPosition().y
						- (current_ani.getHeight() / 2));
			}
		}
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
}
