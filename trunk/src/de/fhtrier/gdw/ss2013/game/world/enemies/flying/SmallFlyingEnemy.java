package de.fhtrier.gdw.ss2013.game.world.enemies.flying;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.FlyingEnemy;

public class SmallFlyingEnemy extends FlyingEnemy {

	public SmallFlyingEnemy() {
		super(AssetLoader.getInstance().getAnimation("flying_ememy_small"));
	}

}
