/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.sound;

import org.newdawn.slick.Sound;

import de.fhtrier.gdw.ss2013.util.AssetLoader;

public class SoundLocator {

    static private ISoundPlayer _service = new DefaultPlayer();

    static private AssetLoader soundLoader;

    static public void provideAssetLoader(AssetLoader loader) {
        soundLoader = loader;
    }

    static public void provide(ISoundPlayer service) {
        _service = service;
    }

    static public ISoundPlayer getPlayer() {
        return _service;
    }

    static public Sound loadSound(String path) {
        Sound s = soundLoader.getSound(path);

        return s;
    }
}
