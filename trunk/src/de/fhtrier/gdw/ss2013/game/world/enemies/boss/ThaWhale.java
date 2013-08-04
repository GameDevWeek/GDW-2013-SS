package de.fhtrier.gdw.ss2013.game.world.enemies.boss;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;

public class ThaWhale extends AbstractBoss {

	public ThaWhale() {
		super(AssetLoader.getInstance().getImage("whale"));
		phase = new IdlePhase();
	}
	
	@Override
	protected void initialize() {
        super.initialize();
		if (physicsObject != null) {
			physicsObject.removeFromWorld();
		}
        
        setOrigin(2000, 100);
	}

    @Override
    public void initPhysics() {
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(1).friction(1)
                .asBox(250, 100);
    }

    @Override
    public void reduceHealth(float damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	private class IdlePhase extends Phase {
		private int timer = 3000;

		@Override
		void enter() {
			timer = 3000;
		}

		@Override
		void update(int delta) {
			timer -= delta;
			if (timer < 0) {
				setPhase(new LoadingPhase());
			}
		}

		@Override
		void render(Graphics g) {
			float x = getPosition().x - (img.getWidth() / 2);
			float y = -110 + getPosition().y - (img.getHeight() / 2);
			g.drawImage(img, x, y);
		}
	}

	private class LoadingPhase extends Phase {
		private int timer;

		@Override
		void enter() {
			timer = 1000;
		}

		@Override
		void update(int delta) {
			timer -= delta;
			if (timer < 0) {
				setPhase(new AttackingPhase());
			}
		}

		@Override
		void render(Graphics g) {
			float x = getPosition().x - (img.getWidth() / 2);
			float y = -110 + getPosition().y - (img.getHeight() / 2);
			x += (Math.random() * 51) - 25;
			y += (Math.random() * 51) - 25;
			g.drawImage(img, x, y);
		}

	}

	private class AttackingPhase extends Phase {
		private int timer;
		Vector2f target;
		private static final float ATTACKSPEED = 5000;

		@Override
		void enter() {
			timer = 500;
			Astronaut astronaut = World.getInstance().getAstronaut();
			target = astronaut.getPosition().sub(getPosition()).normalise()
					.scale(ATTACKSPEED);
		}

		@Override
		void update(int delta) {
			timer -= delta;
			if (timer < 0) {
				setPhase(new IdlePhase());
			}
		}

		@Override
		void render(Graphics g) {
			float x = getPosition().x - (img.getWidth() / 2);
			float y = -110 + getPosition().y - (img.getHeight() / 2);
			g.drawImage(img, x, y);
		}

		@Override
		void leave() {
			setVelocity(target);
		}
	}
}
