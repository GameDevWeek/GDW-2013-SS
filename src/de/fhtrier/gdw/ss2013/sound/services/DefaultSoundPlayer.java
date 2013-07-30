package de.fhtrier.gdw.ss2013.sound.services;

import java.util.HashMap;
import java.util.HashSet;

import org.newdawn.slick.Sound;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

/**
 * 
 * @author Sebastian, Arnold
 * 
 */
public class DefaultSoundPlayer implements SoundPlayer {

    private HashMap<Entity, HashSet<Sound>> emitterSoundPlayingMap = new HashMap<>();
    private final Player listener;

    public DefaultSoundPlayer(Player listener) {
        this.listener = listener;
    }

    @Override
    public void playSound(Sound sound) {
        if (!sound.playing()) {
            sound.play();
        }
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
        sound.stop();
    }

    @Override
    public void playSoundAt(Sound sound, Entity emitter) {
        if (!manageEntitySoundPlayMap(emitter, sound))
            return;
        // float dSq =
        // listener.getPosition().distanceSquared(emitter.getPosition());
        // final float MidSound = 0.5f;

        sound.play();
        emitterSoundPlayingMap.get(emitter).add(sound);
    }

    @Override
    public void playMusic(Sound music) {

    }

    @Override
    public void stopAllSounds() {

    }

}
