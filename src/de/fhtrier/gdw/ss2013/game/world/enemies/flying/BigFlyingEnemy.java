package de.fhtrier.gdw.ss2013.game.world.enemies.flying;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.FlyingEnemy;

public class BigFlyingEnemy extends FlyingEnemy {

	public BigFlyingEnemy() {
		super(AssetLoader.getInstance().getAnimation("flying_ememy_big"));
	}

}
