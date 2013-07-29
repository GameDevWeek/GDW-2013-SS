package de.fhtrier.gdw.ss2013.sound;

import java.util.HashMap;
import java.util.HashSet;

import org.newdawn.slick.Sound;

import de.fhtrier.gdw.ss2013.game.Entity;

/**
 * 
 * @author Sebastian, Arnold
 * 
 */
public class DefaultSoundPlayer implements SoundPlayer {

    // Emitter
    private HashMap<Entity, HashSet<Sound>> emitterSoundPlayingMap = new HashMap<>();

    @Override
    public void playSound(Sound sound) {

    }

    private boolean manageEntitySoundPlayMap(Entity emitter, Sound sound) {
        if (!emitterSoundPlayingMap.containsKey(emitter)) {
            emitterSoundPlayingMap.put(emitter, new HashSet<Sound>());
        }
        if (emitterSoundPlayingMap.containsKey(emitter)) {
            if (emitterSoundPlayingMap.get(emitter).contains(sound)) {
                if (!sound.playing()) {
                    return true;
                } else
                    return false;
            }

        }
        return true;
    }

    @Override
    public void stopSound(Sound sound) {
        for (Entity e : emitterSoundPlayingMap.keySet()) {
            if (!sound.playing()) {
                if (emitterSoundPlayingMap.containsKey(e))
                    emitterSoundPlayingMap.get(e).remove(sound);
            }
        }
    }

    @Override
    public void playSoundAt(Sound sound, Entity listener, Entity emitter) {
        if (!manageEntitySoundPlayMap(emitter, sound))
            return;
        float dSq = listener.getPosition().distanceSquared(emitter.getPosition());
        final float MidSound = 0.5f;

        sound.play();
        emitterSoundPlayingMap.get(emitter).add(sound);
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
