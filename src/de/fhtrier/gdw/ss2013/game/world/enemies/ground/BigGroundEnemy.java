package de.fhtrier.gdw.ss2013.game.world.enemies.ground;

import org.newdawn.slick.Animation;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.GroundEnemy;

public class BigGroundEnemy extends GroundEnemy {

	public BigGroundEnemy(Animation animation) {
		super(AssetLoader.getInstance().getAnimation("ground_ememy_big"));
	}

}
