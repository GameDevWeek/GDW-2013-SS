package de.fhtrier.gdw.ss2013.game.world.enemies.ground;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.GroundEnemy;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;

public class BigGroundEnemy extends GroundEnemy {

    public BigGroundEnemy() {
        super(AssetLoader.getInstance().getAnimation("ground_ememy_big"));
    }

    @Override
    protected void initialize() {
        dieSound = SoundLocator.loadSound("grossgegnertot");
        super.initialize();
    }

}
