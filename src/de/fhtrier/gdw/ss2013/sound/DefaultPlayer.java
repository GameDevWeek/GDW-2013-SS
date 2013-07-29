package de.fhtrier.gdw.ss2013.sound;

import java.util.HashMap;
import java.util.HashSet;

import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.util.VectorUtil;

/**
 * 
 * @author Sebastian, Arnold
 * 
 */
public class DefaultPlayer implements ISoundPlayer {

    // Emitter
    private HashMap<Entity, HashSet<Sound>> emitterSoundPlayingMap = new HashMap<>();

    @Override
    public void playSound(Sound sound) {

    }

    @Override
    public void stopSound(Sound sound) {

    }

    @Override
    public void playSoundAt(Sound sound, Entity listener, Entity emitter) {

        Vector2f dir = VectorUtil.subtract(listener.getPosition(),
                emitter.getPosition());
        float dSq = dir.lengthSquared();
        final float MidSound = 0.5f;

        // sound.playAt(1.0f, 1.0f, 1, 0, 0);
        sound.playAt(1, 0, 0);

    }

    @Override
    public void playMusic(Sound music) {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopAllSounds() {
        // TODO Auto-generated method stub

    }

}
