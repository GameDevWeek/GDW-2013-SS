package de.fhtrier.gdw.ss2013.game.world.enemies.flying;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.FlyingEnemy;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;

public class SmallFlyingEnemy extends FlyingEnemy {

    public SmallFlyingEnemy() {
        super(AssetLoader.getInstance().getAnimation(getRandomImage()));
    }

    @Override
    protected void initialize() {
        dieSound = SoundLocator.loadSound("fliegengegnertot");
        super.initialize();
    }

    private static String getRandomImage() {
        String imgString;
        int rand = (int) (Math.random() * 3);
        switch (rand) {
        case 1:
            imgString = "flying_ememy_small_red";
            break;
        case 2:
            imgString = "flying_ememy_small_green";
            break;
        default:
            imgString = "flying_ememy_small_blue";
            break;
        }
        return imgString;
    }

}
