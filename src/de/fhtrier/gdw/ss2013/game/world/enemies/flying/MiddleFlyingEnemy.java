package de.fhtrier.gdw.ss2013.game.world.enemies.flying;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.FlyingEnemy;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;

public class MiddleFlyingEnemy extends FlyingEnemy {

    public MiddleFlyingEnemy() {
        super(AssetLoader.getInstance().getAnimation("flying_ememy_middle"));
    }

    @Override
    protected void initialize() {
        dieSound = SoundLocator.loadSound("fantfliegegegnertot");
        super.initialize();
    }

}
