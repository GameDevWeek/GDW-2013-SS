package de.fhtrier.gdw.ss2013.game.world.enemies.ground;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.GroundEnemy;

public class MiddleGroundEnemy extends GroundEnemy {

	public MiddleGroundEnemy() {
		super(AssetLoader.getInstance().getAnimation("ground_ememy_middle"));
	}

}
