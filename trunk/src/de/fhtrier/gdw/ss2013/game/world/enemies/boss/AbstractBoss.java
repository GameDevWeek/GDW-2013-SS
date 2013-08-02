package de.fhtrier.gdw.ss2013.game.world.enemies.boss;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.GameDataInfo.WorldInfo;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixBox;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public abstract class AbstractBoss extends Entity {

	protected Phase phase;

	public AbstractBoss() {
		
	}

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

		void render(Graphics g) {};

		void leave() {
		};
	}

}
