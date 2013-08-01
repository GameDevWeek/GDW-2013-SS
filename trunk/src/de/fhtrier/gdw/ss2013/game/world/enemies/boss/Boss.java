package de.fhtrier.gdw.ss2013.game.world.enemies.boss;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;

public class Boss extends Entity {

	private Phase phase;

	public Boss() {
		super(AssetLoader.getInstance().getImage("whale"));
		phase = new IdlePhase();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		phase.render(g);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		phase.update(delta);
	}

	public void setPhase(Phase newPhase) {
		phase.leave();
		phase = newPhase;
		phase.enter();
	}

	private abstract class Phase {

		void enter() {
		};

		abstract void update(int delta);

		abstract void render(Graphics g);

		void leave() {
		};
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
