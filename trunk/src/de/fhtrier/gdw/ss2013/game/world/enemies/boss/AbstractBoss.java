package de.fhtrier.gdw.ss2013.game.world.enemies.boss;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;

public abstract class AbstractBoss extends Entity {

	protected Phase phase;

	public AbstractBoss(Image image) {
		super(image);
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

	protected abstract class Phase {

		void enter() {
		};

		abstract void update(int delta);

		abstract void render(Graphics g);

		void leave() {
		};
	}

}
