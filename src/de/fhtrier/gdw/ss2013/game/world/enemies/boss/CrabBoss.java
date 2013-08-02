package de.fhtrier.gdw.ss2013.game.world.enemies.boss;

import java.util.Random;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.camera.PointOfInterest;
import de.fhtrier.gdw.ss2013.game.camera.ThreePointCamera;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.enemies.ground.SmallGroundEnemy;

public class CrabBoss extends AbstractBoss {

	private Animation animation;
	private float scale = 2.0f;
	private float physicsObject_x_offset = 80.0f;
	private boolean facingRight;
	public int stompCount;
	private Vector2f cameraTarget;

	@Override
	protected void initialize() {
		animation = AssetLoader.getInstance().getAnimation("crab_boss_idle");
		phase = new TargetingPhase(new StepForwardPhase(), 500);
		animation.setAutoUpdate(false);
        
        setInitialSize( animation.getWidth() * scale - 2 * physicsObject_x_offset,
        animation.getHeight() * scale);

        cameraTarget = new Vector2f();
        ThreePointCamera tpCamera = World.getInstance().getTPCamera();
        if(tpCamera != null)
        	World.getInstance().getTPCamera().addDynamicTarget(cameraTarget);
	}

	private void recalculateCameraTargetPosition() {
		Vector2f playerPos = World.getInstance().getAstronaut().getPosition();
        cameraTarget.set(getPosition().sub(playerPos));
        float distancePlayerToBoss = cameraTarget.length();
        cameraTarget.normalise();
        cameraTarget.scale((1/3f)*distancePlayerToBoss);
        cameraTarget.add(playerPos);
	}

    @Override
    public void initPhysics() {
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(1).friction(1)
                .asBox(initialSize.x, initialSize.y);
    }
    
    @Override
    public void update(GameContainer container, int delta)
    		throws SlickException {
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
			animation.draw(x - halfwidth - physicsObject_x_offset, y
					- halfheight, 2 * halfwidth + 2 * physicsObject_x_offset,
					2 * halfheight);
		} else {
			animation.draw(x + halfwidth + physicsObject_x_offset, y
					- halfheight, -2 * halfwidth - 2 * physicsObject_x_offset,
					2 * halfheight);
		}
	}

	private void recalculateDirection() {
		Vector2f direction = World.getInstance().getAstronaut().getPosition()
				.sub(getPosition());

		boolean newDirection = direction.x >= 0;
		facingRight = newDirection;
	}

	private class StepForwardPhase extends Phase {
		private static final float SPEED = 400.0f;
		Vector2f walkingLeft = new Vector2f(-SPEED, 0.0f);
		Vector2f walkingRight = new Vector2f(SPEED, 0.0f);
		private int timer;

		@Override
		void enter() {
			timer = 4000;
		}

		@Override
		void update(int delta) {
			timer -= delta;
			if (timer < 0) {
				setPhase(new TargetingPhase(new FiringEnemies(
						new Random().nextInt(5)), 500));
			}
			if (facingRight) {
				setVelocity(walkingRight);
			} else {
				setVelocity(walkingLeft);
			}
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
			enemy = World.getInstance().getEntityManager()
					.createEntity(SmallGroundEnemy.class);
            enemy.setOrigin(getPosition().x - 100.0f, getPosition().y - 350.0f);
		}

		@Override
		void update(int delta) {
			enemy.setVelocityY(-300.0f);
			if (facingRight) {
				enemy.setVelocityX(200.0f + (float)Math.random()*450.0f);
			} else {
				enemy.setVelocityX(-200.0f - (float)Math.random()*450.0f);
			}
			if (remainingFires == 0) {
				setPhase(new TargetingPhase(new StompingPhase(), 1000));
			} else {
				setPhase(new TargetingPhase(new FiringEnemies(
						remainingFires - 1), 500));
			}
		}
	}

	private class StompingPhase extends Phase {

		private int timer = 2000;

		@Override
		void enter() {
			timer = 2000;
			stompCount += 1;
			setVelocityY(-800.0f);
			if (facingRight) {
				setVelocityX(270.0f);
			} else {
				setVelocityX(-270.0f);
			}
		}

		@Override
		void update(int delta) {
			timer -= delta;
			if (timer < 0) {
				if (stompCount == 3) {
					stompCount = 0;
					setPhase(new TargetingPhase(new StepForwardPhase(), 500));
				} else {
					setPhase(new StompPausePhase());
				}
			}
		}
	}

	private class StompPausePhase extends Phase {

		private int timer = 1000;

		@Override
		void enter() {
			timer = 1000;
			recalculateDirection();
		}

		@Override
		void update(int delta) {
			timer -= delta;
			if (timer < 0) {
				setPhase(new StompingPhase());
			}
			recalculateDirection();
		}

	}
}
