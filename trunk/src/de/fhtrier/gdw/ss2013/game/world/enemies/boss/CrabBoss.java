package de.fhtrier.gdw.ss2013.game.world.enemies.boss;

import de.fhtrier.gdw.ss2013.MainGame;
import java.util.Random;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.camera.ThreePointCamera;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.objects.ActivatableMeteroid;
import de.fhtrier.gdw.ss2013.game.world.objects.Meteroid;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

import org.newdawn.slick.Font;

public class CrabBoss extends AbstractBoss {

    private float SCALE = 1.3f;
	private Animation animation;
	private float physicsObject_x_offset = 20.0f;
	private float physicsObject_y_offset = 80.0f;
	private boolean facingRight;
	private Entity[] meteroids = new Entity[2];
	public int stompCount;
	private Vector2f cameraTarget;
	private int damage = 0;
	private int meteroidsFired = 0;
	private boolean isActive;
    private Animation animation_attacking;
    private Animation animation_walking;
    private Font font;
    private SoundPlayer soundPlayer;
    private Sound bossCharge;
    private Sound bossHit;
    private Sound bossShot;

	@Override
	protected void initialize() {
		animation = AssetLoader.getInstance().getAnimation("crab_boss_idle");
		animation_walking = AssetLoader.getInstance().getAnimation("crab_boss_walking");
		animation_attacking = AssetLoader.getInstance().getAnimation("crab_boss_attacking");
		phase = new InitialPhase();
		phase.enter();
		animation.setAutoUpdate(false);
        font = AssetLoader.getInstance().getFont("verdana_24");

		setInitialSize(animation.getWidth() * SCALE, animation.getHeight() * SCALE);

		cameraTarget = new Vector2f();
		ThreePointCamera tpCamera = World.getInstance().getTPCamera();
		tpCamera.addDynamicTarget(cameraTarget);
		tpCamera.setZoom(0.5f);
		damage = 0;
		
		soundPlayer = SoundLocator.getPlayer();
		bossCharge = SoundLocator.loadSound("boss_mama_charge");
		bossHit = SoundLocator.loadSound("boss_mama_hit");
		bossShot = SoundLocator.loadSound("boss_mama_shot");
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
		createPhysics(BodyType.DYNAMIC, origin.x, origin.y).density(500)
				.friction(0.4f).asCircle(Math.min(initialSize.x, initialSize.y) * 0.4f );
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

		if (phase instanceof DiePhase) {
			phase.render(g);
		} else {
			float x = physicsObject.getPosition().x;
			float y = physicsObject.getPosition().y;
			float halfheight = SCALE * animation.getHeight()/2;
			float halfwidth = SCALE * animation.getWidth()/2;
            
            Animation anim;
            if(phase instanceof StepForwardPhase || phase instanceof TargetingPhase)
                anim = animation_walking;
            else if(phase instanceof FiringEnemies)
                anim = animation_attacking;
            else
                anim = animation;
            if(anim.isStopped())
                anim.restart();

			if (!facingRight) {
				anim.draw(x - halfwidth, y - halfheight, 2 * halfwidth,
						2 * halfheight);
			} else {
				anim.draw(x + halfwidth, y - halfheight, -2 * halfwidth,
						2 * halfheight);
			}
            font.drawString(x, y, phase.getClass().getSimpleName());
		}
	}

	public void setPhase(Phase newPhase) {
        super.setPhase(newPhase);
        
        animation.restart();
        animation.stop();
        animation_walking.restart();
        animation_walking.stop();
        animation_attacking.restart();
        animation_attacking.stop();
	}
    
	private void recalculateDirection() {
		Vector2f direction = World.getInstance().getAstronaut().getPosition()
				.sub(getPosition());

		boolean newDirection = direction.x >= 0;
		facingRight = newDirection;
	}

    @Override
    public void reduceHealth(float damage) {
        setPhase(new TakeDamagePhase());
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
		private static final float MAX_SPEED = 600.0f;
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
						new Random().nextInt(5) + 3), 1000));
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

	private class TakeDamagePhase extends Phase {

		private static final int PHASE_DURATION = 3000;
		private int timer;

		@Override
		void enter() {
			timer = PHASE_DURATION;
		}

		@Override
		void update(int delta) {
			if (damage >= 3) {
				setPhase(new DiePhase());
			}
			timer -= delta;
			if (timer < 0) {
				damage += 1;
                meteroidsFired--;
				setPhase(new TargetingPhase(new StepForwardPhase(), 300));
			}
		}
	}

	private class DiePhase extends Phase {

		private static final int PHASE_DURATION = 15000;
		private int timer;

		@Override
		void enter() {
			timer = PHASE_DURATION;
		}

		@Override
		void update(int delta) {
			timer -= delta;
			if (timer < 0) {
				World.getInstance().getEntityManager()
						.removeEntity(CrabBoss.this);
                MainGame.changeState(MainGame.WINSTATE);
			}
		}

		@Override
		void render(Graphics g) {
			float x = physicsObject.getPosition().x;
			float y = physicsObject.getPosition().y;
			float halfheight = SCALE * animation.getHeight()/2;
			float halfwidth = SCALE * animation.getWidth()/2;

			float offsetx = (float) Math.random() * 50 * (float) PHASE_DURATION
					/ timer;
			float offsety = (float) Math.random() * 50 * (float) PHASE_DURATION
					/ timer;
			if (!facingRight) {
				animation.draw(x - halfwidth + offsetx, y - halfheight + offsety,
						2 * halfwidth, 2 * halfheight);
			} else {
				animation.draw(x + halfwidth + offsetx, y - halfheight + offsety, -2
						* halfwidth, 2 * halfheight);
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

			boolean isRandomStone = Math.random() < 0.15;

			EntityManager entityManager = World.getInstance()
					.getEntityManager();

			Class<? extends Entity> classId = null;
			if (isRandomStone && meteroidsFired < 2) {
				classId = ActivatableMeteroid.class;
				meteroidsFired += 1;
			} else {
				classId = Meteroid.class;
			}

			enemy = entityManager.createEntity(classId);
			float offsetX = getPhysicsObject().getDimension().x;
			if (!facingRight) {
				offsetX *= -1;
			}
			enemy.setOrigin(getPosition().x + offsetX, getPosition().y);
			if (enemy instanceof ActivatableMeteroid) {
				meteroids[meteroidsFired - 1] = enemy;
			}
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
