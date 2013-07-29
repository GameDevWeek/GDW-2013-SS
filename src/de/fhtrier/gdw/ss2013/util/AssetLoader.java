/*
 * 
 * 
 * 
 * @author Janina, Benjamin
 */

package de.fhtrier.gdw.ss2013.util;

import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.ResourceLoader;

import de.fhtrier.gdw.commons.jackson.JacksonReader;
import de.fhtrier.gdw.ss2013.util.assets.AnimInfo;
import de.fhtrier.gdw.ss2013.util.assets.ImageInfo;
import de.fhtrier.gdw.ss2013.util.assets.SoundInfo;

public class AssetLoader {
    HashMap<String, Image> imageMap = new HashMap<>();
    HashMap<String, Animation> animMap = new HashMap<>();
    HashMap<String, Sound> soundMap = new HashMap<>();

    public AssetLoader() {
        setupImages("res/json/images.json");
        setupAnimation("res/json/animations.json");
        setupSound("res/json/sounds.json");
    }

    private void setupImages(String filename) {
        try {
            List<ImageInfo> imageInfos = JacksonReader.readList(filename,
                    ImageInfo.class);
            for (ImageInfo imageInfo : imageInfos) {
                imageMap.put(imageInfo.name, new Image(imageInfo.pfad));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupAnimation(String filename) {
        try {
            List<AnimInfo> animInfos = JacksonReader.readList(filename,
                    AnimInfo.class);
            for (AnimInfo animInfo : animInfos) {
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
