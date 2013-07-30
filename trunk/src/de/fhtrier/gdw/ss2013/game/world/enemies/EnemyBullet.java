package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

/**
 * Bullet Class
 * 
 * @author Kevin, Georg
 * 
 */
public class EnemyBullet extends AbstractEnemy implements ICollidable {

	final static float DEBUG_ENTITY_HALFEXTEND = 5;
	private Image img;
	private AssetLoader a = AssetLoader.getInstance();

	public EnemyBullet(Vector2f pos, Vector2f velo, float dmg) {
		super(pos.copy(), velo.copy(), dmg);
		img = a.getImage("GeschossAlien");
	}

	public EnemyBullet() {
		this(new Vector2f(), new Vector2f(), 0);
	}

	public EnemyBullet(Vector2f pos) {
		this(pos.copy(), new Vector2f(), 0);
	}

	@Override
	public void onCollision(Entity e) {
		if (e instanceof Astronaut) {
			((Astronaut) e).setOxygen(((Astronaut) e).getOxygen()
					- this.getDamage());
		}
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
	    img.draw(this.getPosition().x, this.getPosition().y);

		// g.drawString(this.hashCode(), position.x, position.y);
	}

	public void update(GameContainer container, int delta)
			throws SlickException {

		// float dt = delta / 1000.f;
		// TODO clamp dt if dt > 1/60.f ?
		this.position.x += this.getVelocity().x;
		this.position.y += this.getVelocity().y;

	}

	@Override
	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}
}
