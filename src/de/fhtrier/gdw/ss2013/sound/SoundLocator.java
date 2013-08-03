/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.sound;

import org.newdawn.slick.Sound;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.sound.services.DefaultSoundPlayer;
import de.fhtrier.gdw.ss2013.sound.services.NoSoundPlayer;

public class SoundLocator {

    static private SoundPlayer _service = new NoSoundPlayer();

    static private AssetLoader soundLoader;

    static public void provideAssetLoader(AssetLoader loader) {
        soundLoader = loader;
    }

    static public void provide(SoundPlayer service) {
        _service = service;
    }

    static public SoundPlayer getPlayer() {
        return _service;
    }

    static public Sound loadSound(String path) {
        Sound s = soundLoader.getSound(path);

        return s;
    }
}
