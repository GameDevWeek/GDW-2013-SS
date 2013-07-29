package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.dynamics.Fixture;

import de.fhtrier.gdw.ss2013.game.Entity;

public interface ICollidable {
	public void onCollision(Entity e);

	public Fixture getFixture();
}
