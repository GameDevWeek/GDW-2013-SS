/*
 * 
 * 
 * 
 * @author Janina, Benjamin
 */

package de.fhtrier.gdw.ss2013.assetloader;

import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.ResourceLoader;

import de.fhtrier.gdw.commons.jackson.JacksonReader;
import de.fhtrier.gdw.ss2013.assetloader.infos.AnimationInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.ImageInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.SoundInfo;

public class AssetLoader {
    HashMap<String, Image> imageMap = new HashMap<>();
    HashMap<String, Animation> animMap = new HashMap<>();
    HashMap<String, Sound> soundMap = new HashMap<>();

    public AssetLoader() {
        setupImages("res/json/images.json");
        setupAnimation("res/json/animations.json");
        setupSound("res/json/sounds.json");
    }
    
    private void checkForBackslashes(String filename) {
        for (int i=0;i<filename.length();++i) {
            if (filename.charAt(i) == '\\') {
                throw new IllegalArgumentException("You shall not use backslashes for paths! Check the JSON-files!");
            }
        }
    }

    private void setupImages(String filename) {
        try {
            List<ImageInfo> imageInfos = JacksonReader.readList(filename,
                    ImageInfo.class);
            for (ImageInfo imageInfo : imageInfos) {
                checkForBackslashes(imageInfo.pfad);
                imageMap.put(imageInfo.name, new Image(imageInfo.pfad));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupAnimation(String filename) {
        try {
            List<AnimationInfo> animInfos = JacksonReader.readList(filename,
                    AnimationInfo.class);
            for (AnimationInfo animInfo : animInfos) {
                checkForBackslashes(animInfo.pfad);
                Image img = new Image(animInfo.pfad);
                Animation anim = new Animation();
                SpriteSheet sheet = new SpriteSheet(img, img.getWidth()
                        / animInfo.anzBilder, img.getHeight());
                for (int i = 0; i < animInfo.anzBilder; i += 1) {
                    anim.addFrame(sheet.getSprite(i, 0), animInfo.animSpeed);
                }
                animMap.put(animInfo.name, anim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupSound(String filename) {
        try {
            List<SoundInfo> soundInfos = JacksonReader.readList(filename,
                    SoundInfo.class);
            for (SoundInfo soundInfo : soundInfos) {
                checkForBackslashes(soundInfo.pfad);
                soundMap.put(soundInfo.name,
                        new Sound(ResourceLoader.getResource(soundInfo.pfad)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Image getImage(String name) {
        return imageMap.get(name);
    }

    public Animation getAnimation(String name) {
        return animMap.get(name);
    }

    /**
     * @param name Name out of the JSON file
     * @return Instance of the sound
     */
    public Sound getSound(String name) {
        return soundMap.get(name);
    }

}
