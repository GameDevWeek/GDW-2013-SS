package de.fhtrier.gdw.ss2013.game.world.enemies.ground;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.GroundEnemy;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;

public class SmallGroundEnemy extends GroundEnemy {

    public SmallGroundEnemy() {
        super(AssetLoader.getInstance().getAnimation("ground_ememy_small"));

    }

    @Override
    protected void initialize() {
        dieSound = SoundLocator.loadSound("maulwurfgegnertot");
        super.initialize();
    }

}
