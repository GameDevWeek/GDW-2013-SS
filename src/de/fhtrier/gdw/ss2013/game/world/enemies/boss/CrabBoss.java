package de.fhtrier.gdw.ss2013.game.world.enemies.boss;

import java.util.Random;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.camera.ThreePointCamera;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.objects.Box;
import de.fhtrier.gdw.ss2013.game.world.objects.Meteroid;

public class CrabBoss extends AbstractBoss {

	private Animation animation;
	private float physicsObject_x_offset = 20.0f;
	private float physicsObject_y_offset = 80.0f;
	private boolean facingRight;
	public int stompCount;
	private Vector2f cameraTarget;

	private int FiringStoneCount = 2;

	@Override
	protected void initialize() {
		animation = AssetLoader.getInstance().getAnimation("crab_boss_idle");
		img = AssetLoader.getInstance().getImage("boss_image");
		phase = new InitialPhase();
		phase.enter();
		animation.setAutoUpdate(false);

		setInitialSize(img.getWidth() - physicsObject_x_offset, img.getHeight()
				- physicsObject_y_offset);

		cameraTarget = new Vector2f();
		ThreePointCamera tpCamera = World.getInstance().getTPCamera();
		tpCamera.addDynamicTarget(cameraTarget);
		tpCamera.setZoom(0.5f);
	}

	private void recalculateCameraTargetPosition() {
		Vector2f playerPos = World.getInstance().getAstronaut().getPosition();
		cameraTarget.set(getPosition().sub(playerPos));
		float distancePlayerToBoss = cameraTarget.length();
		cameraTarget.normalise();
		cameraTarget.scale((1 / 3f) * distancePlayerToBoss);
		cameraTarget.add(playerPos);
	}

	@Override
	public void initPhysics() {
		createPhysics(BodyType.DYNAMIC, origin.x, origin.y).density(1)
				.friction(1).asBox(initialSize.x, initialSize.y);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (World.getInstance().getAstronaut().getOxygen() <= 0) { // disable
																	// update on
																	// player
																	// dead
			return;
		}
		super.update(container, delta);
		recalculateCameraTargetPosition();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		float x = physicsObject.getPosition().x;
		float y = physicsObject.getPosition().y;
		float halfheight = physicsObject.getDimension().y;
		float halfwidth = physicsObject.getDimension().x;

		if (facingRight) {
			// animation.draw(x - halfwidth - physicsObject_x_offset, y
			// - halfheight, 2 * halfwidth + 2 * physicsObject_x_offset,
			// 2 * halfheight);
			img.draw(x - halfwidth, y - halfheight,
					2 * halfwidth, 2 * halfheight);
		} else {
//			animation.draw(x + halfwidth + physicsObject_x_offset, y
//					- halfheight, -2 * halfwidth - 2 * physicsObject_x_offset,
//					2 * halfheight);
			img.draw(x + halfwidth, y - halfheight,
					-2 * halfwidth, 2 * halfheight);
		}
	}

	private void recalculateDirection() {
		Vector2f direction = World.getInstance().getAstronaut().getPosition()
				.sub(getPosition());

		boolean newDirection = direction.x >= 0;
		facingRight = newDirection;
	}

	private class InitialPhase extends Phase {
		private static final int PHASE_DURATION = 5000;
		private int timer = PHASE_DURATION;

		@Override
		void enter() {
			timer = PHASE_DURATION;
		}

		@Override
		void update(int delta) {
			timer -= delta;
			if (timer < 0) {
				setPhase(new TargetingPhase(new StepForwardPhase(), 200));
			}
		}
	}

	private class StepForwardPhase extends Phase {
		private static final float MAX_SPEED = 800.0f;
		private static final int PHASE_DURATION = 2000;
		private int timer;

		@Override
		void enter() {
			timer = PHASE_DURATION;
		}

		@Override
		void update(int delta) {
			timer -= delta;
			if (timer < 0) {
				setPhase(new TargetingPhase(new FiringEnemies(
						new Random().nextInt(21) + 10), 1000));
			}
			float speed = (float) (MAX_SPEED * Math.pow(
					1 - ((float) timer / PHASE_DURATION), 2.0));
			if (!facingRight) {
				speed *= -1;
			}
			setVelocityX(speed);
		}
	}

	private class TargetingPhase extends Phase {

		private Phase nextPhase;
		private int timer;

		public TargetingPhase(Phase nextPhase, int timer) {
			this.nextPhase = nextPhase;
			this.timer = timer;
		}

		@Override
		void update(int delta) {
			recalculateDirection();
			timer -= delta;
			if (timer < 0) {
				setPhase(nextPhase);
			}
		}

	}

	private class FiringEnemies extends Phase {

		private Entity enemy;
		private int remainingFires;

		public FiringEnemies(int remainingFires) {
			this.remainingFires = remainingFires;
		}

		@Override
		void enter() {

			boolean isRandomStone = Math.random() < 0.10;

			EntityManager entityManager = World.getInstance()
					.getEntityManager();

			Class<? extends Entity> classId = null;
			SafeProperties enemyProperties = new SafeProperties();
			if (isRandomStone && FiringStoneCount > 0) {
				classId = Box.class;
				FiringStoneCount--;
				enemyProperties.setProperty("animation", "box");
			} else {
				classId = Meteroid.class;
				enemyProperties.setProperty("animation", "rock");
			}

			enemy = entityManager.createEntity(classId);
			enemy.setProperties(enemyProperties);
			float offsetX = getPhysicsObject().getDimension().x;
			if (!facingRight) {
				offsetX *= -1;
			}
			enemy.setOrigin(getPosition().x + offsetX, getPosition().y);
		}

		@Override
		void update(int delta) {

			float distanceToPlayer = getPosition().distance(
					World.getInstance().getAstronaut().getPosition());
			float playerSpeed = World.getInstance().getAstronaut()
					.getVelocity().x;
			float velocity = distanceToPlayer / 5.0f + (float) Math.random()
					* 150.0f - 75f;
			if (!facingRight) {
				velocity *= -1;
			}
			velocity += playerSpeed / 2.0f;

			enemy.setVelocityX(velocity);
			enemy.setVelocityY(-(float) Math.random() * 200 - distanceToPlayer
					/ 3.0f);
			if (remainingFires == 0) {
				setPhase(new TargetingPhase(new StepForwardPhase(), 1000));
			} else {
				setPhase(new TargetingPhase(new FiringEnemies(
						remainingFires - 1), 200));
			}
		}
	}

	// private class StompingPhase extends Phase {
	//
	// private int timer = 2000;
	//
	// @Override
	// void enter() {
	// timer = 2000;
	// stompCount += 1;
	// setVelocityY(-800.0f);
	// if (facingRight) {
	// setVelocityX(270.0f);
	// } else {
	// setVelocityX(-270.0f);
	// }
	// }
	//
	// @Override
	// void update(int delta) {
	// timer -= delta;
	// if (timer < 0) {
	// if (stompCount == 3) {
	// stompCount = 0;
	// setPhase(new TargetingPhase(new StepForwardPhase(), 500));
	// } else {
	// setPhase(new StompPausePhase());
	// }
	// }
	// }
	// }
	//
	// private class StompPausePhase extends Phase {
	//
	// private int timer = 1000;
	//
	// @Override
	// void enter() {
	// timer = 1000;
	// recalculateDirection();
	// }
	//
	// @Override
	// void update(int delta) {
	// timer -= delta;
	// if (timer < 0) {
	// setPhase(new StompingPhase());
	// }
	// recalculateDirection();
	// }
	//
	// }
}
