package de.fhtrier.gdw.ss2013.game.world.enemies.ground;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.GroundEnemy;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;

public class MiddleGroundEnemy extends GroundEnemy {

    public MiddleGroundEnemy() {
        super(AssetLoader.getInstance().getAnimation(getRandomImageString()));
    }

    @Override
    protected void initialize() {
        dieSound = SoundLocator.loadSound("tapiralientot");
        super.initialize();
    }

    private static String getRandomImageString() {
        int rand = (int) (Math.random() * 2);

        switch (rand) {
        case 1:
            return "ground_ememy_middle_duck";
        default:
            return "ground_ememy_middle_tall";
        }
    }

}
