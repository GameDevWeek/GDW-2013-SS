package de.fhtrier.gdw.ss2013.sound.services;

import java.util.HashMap;
import java.util.HashSet;

import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

/**
 * 
 * @author Sebastian, Arnold
 * 
 */
public class DefaultSoundPlayer implements SoundPlayer {

    private HashMap<Entity, HashSet<Sound>> emitterSoundPlayingMap = new HashMap<>();
    private final Entity listener;

    public DefaultSoundPlayer(Entity listener) {
        this.listener = listener;
        playAtDirection = new Vector2f();
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

    private Vector2f playAtDirection;

    private float stepfunction(float val) {
        if (val > 0)
            return 1.0f;
        if (val < 0)
            return -1.0f;
        return 0.0f;
    }

    @Override
    public void playSoundAt(Sound sound, Entity emitter) {
        if (!manageEntitySoundPlayMap(emitter, sound))
            return;
        // float dSq =
        // listener.getPosition().distanceSquared(emitter.getPosition());
        // final float MidSound = 0.5f;

        playAtDirection.set(listener.getPosition().x, listener.getPosition().y);
        playAtDirection.sub(emitter.getPosition());
        // lerp
        // a*v + (1-a)*v
        // System.out.println("" + playAtDirection);

        /**
         * 1.0f - falloff * ( distance - refDistance ) / ( maxDistance -
         * distance )
         */
        float MAX_DISTANCE = 1000.0f;
        float distance = Math.min(playAtDirection.length(), MAX_DISTANCE);

        float volume = 1.0f - 1.0f * (distance - 50.0f)
                / (MAX_DISTANCE - 50.0f);

        volume = Math.max(0.0f, Math.min(1.0f, volume));
        
        // Calculate sound direction
        //float soundX = (1.0f - volume) * -stepfunction(playAtDirection.x); 
        
        // x = -1 = LeftSpeaker ; x = 0 = Center ; x = 1 = RightSpeaker
        sound.playAt(1.0f, volume, 0.0f, 0.0f, 0.0f);
        // sound.play();
        emitterSoundPlayingMap.get(emitter).add(sound);
    }

    @Override
    public void playMusic(Sound music) {
        if (!music.playing()) {
            music.loop(1.0f, 1.0f);
        }
    }

    @Override
    public void stopAllSounds() {

    }

}
