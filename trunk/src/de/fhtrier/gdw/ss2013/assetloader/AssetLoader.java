/*
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
import de.fhtrier.gdw.commons.jackson.JacksonWriter;
import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.assetloader.infos.AnimationInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.FontInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.ImageInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.InfoInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.KeyInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.MapInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.ScoreInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.SoundInfo;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;

public class AssetLoader {
	private HashMap<String, Image> imageMap = new HashMap<>();
	private HashMap<String, Animation> animMap = new HashMap<>();
	private HashMap<String, Sound> soundMap = new HashMap<>();
	private HashMap<String, String> mapHashmap = new HashMap<>();
	private HashMap<String, Font> fontMap = new HashMap<>();
	private HashMap<String, String> settingsMap = new HashMap<>();
	private HashMap<String, String> infosMap = new HashMap<>();
	
	private static AssetLoader instance;
	
	public static AssetLoader getInstance() {
	    if (instance == null) {
	        instance = new AssetLoader();
	    }
	    return instance;
	}

	private AssetLoader() {
	    setupMaps("res/json/maps.json");
		setupAnimations("res/json/animations.json");
		setupSounds("res/json/sounds.json");
		setupFonts("res/json/fonts.json");
		setupImages("res/json/images.json");
		setupSetups("res/json/setup.json");
		setupInfos("res/json/infos.json");
	}

    private void checkForBackslashes(String filename) {
		for (int i = 0; i < filename.length(); ++i) {
			if (filename.charAt(i) == '\\') {
				throw new IllegalArgumentException(
						"You shall not use backslashes for paths! Check the JSON-files!");
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

	private void setupAnimations(String filename) {
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

	private void setupSounds(String filename) {
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

    private void setupMaps(String filename) {
        try {
            List<MapInfo> mapInfos = JacksonReader.readList(filename,
                    MapInfo.class);
            for (MapInfo mapInfo : mapInfos) {
                checkForBackslashes(mapInfo.pfad);
                mapHashmap.put(mapInfo.name, mapInfo.pfad); // don't load every map during setup
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void setupFonts(String filename) {
		try {
			List<FontInfo> soundInfos = JacksonReader.readList(filename,
					               FontInfo.class);
			for (FontInfo fontInfo : soundInfos) {
				checkForBackslashes(fontInfo.file);
				checkForBackslashes(fontInfo.image);
				fontMap.put(fontInfo.name,
						new AngelCodeFont(fontInfo.file, fontInfo.image));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private List<ScoreInfo> setupScore(String filename) {
        List<ScoreInfo> scoreInfos = null;
        try {
            scoreInfos = JacksonReader.readList(filename, ScoreInfo.class);
        } catch (Exception e) {
            try {
                scoreInfos = JacksonReader.readList("res/json/scores/default.json", ScoreInfo.class);
            } catch (Exception e1) {
                e1.printStackTrace();
                e.printStackTrace();
            }
        }
        return scoreInfos;
    }
    
    private void setupSetups(String filename){
        // TODO
    }
    
    private void setupInfos(String filename){
        try {
            List<InfoInfo> infoInfos = JacksonReader.readList(filename,
                    InfoInfo.class);
            for (InfoInfo infoInfo : infoInfos) {
                infosMap.put(infoInfo.name, infoInfo.info); // don't load every map during setup
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private List<KeyInfo> setupKeys(String filename) { 
        List<KeyInfo> keyInfos = null;
        try {
            keyInfos = JacksonReader.readList(filename, KeyInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyInfos;
    }
    
	public Image getImage(String name) {
	    if(imageMap.get(name) == null){
	        System.out.println("ERROR: Image '"+name+"' existiert nicht.");
	        return imageMap.get("error");
	    }else{
	        return imageMap.get(name);
	    }
		
	}

	public Animation getAnimation(String name) {
	    if(animMap.get(name) == null){
	        System.out.println("ERROR: Animation '"+name+"' existiert nicht.");
	        return animMap.get("error").copy();
	    }else{
	        return animMap.get(name).copy();
	    }	
	}

	public Sound getSound(String name) {
		return soundMap.get(name);
	}
	
	public List<ScoreInfo> getScore(String scoreName){
	    return setupScore("res/json/scores/"+scoreName+".json");
	}
	
	public List<KeyInfo> getKeyList(String device){ //mouse //keyboard //controller
	    return setupKeys("res/json/keys/"+device+".json");
	}

    /**
	 * 
	 * @param name JSON map name
	 * @return Path to map file
	 */
	public String getMapPath(String name) {
	    return mapHashmap.get(name);
	}
    
    /**
     * 
     * @param mapname Mapname from JSON-Files
     * @return Loaded TiledMap
     * @throws Exception
     */
    public TiledMap loadMap(String mapname) throws Exception {
        AssetLoader assetLoader = AssetLoader.getInstance();
        TiledMap map = new TiledMap(assetLoader.getMapPath(mapname), LayerObject.PolyMode.ABSOLUTE);
        return map;
    }

	public Font getFont(String name) {
		return fontMap.get(name);
	}
	
	/*public Setups getSetup(){
	    return null; // TODO not jet implemented
	}*/
	
	public String getInfo(String name){
	    
	    return infosMap.get(name);
	}
	
	public void writeScore(String scoreName, List<ScoreInfo> scoreList){
	    try {
            JacksonWriter.writeList("res/json/scores/"+scoreName+".json", scoreList);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void writeKeys(String device, List<KeyInfo> keyList){
        try {
            JacksonWriter.writeList("res/json/keys/"+device+".json", keyList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
