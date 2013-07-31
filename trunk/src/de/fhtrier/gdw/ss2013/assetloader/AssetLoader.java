/*
 * @author Janina, Benjamin
 */

package de.fhtrier.gdw.ss2013.assetloader;

import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import de.fhtrier.gdw.commons.jackson.JacksonReader;
import de.fhtrier.gdw.commons.jackson.JacksonWriter;
import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.assetloader.infos.AnimationInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.ControlsInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.FontInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.GameStatsInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.ImageInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.InfoInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.MapInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.PartikelInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.ScoreInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.SettingsInfo;
import de.fhtrier.gdw.ss2013.assetloader.infos.SoundInfo;

public class AssetLoader {
	private HashMap<String, Image> imageMap = new HashMap<>();
	private HashMap<String, Animation> animMap = new HashMap<>();
	private HashMap<String, Sound> soundMap = new HashMap<>();
	private HashMap<String, String> mapHashmap = new HashMap<>();
	private HashMap<String, Font> fontMap = new HashMap<>();
	private HashMap<String, ParticleSystem> partikelMap = new HashMap<>();
	private HashMap<String, String> infosMap = new HashMap<>();

	private SettingsInfo settings;
	private GameStatsInfo gameStats;

	private static AssetLoader instance;

	public static AssetLoader getInstance() {
		if (instance == null) {
			instance = new AssetLoader();
		}
		return instance;
	}
    private ControlsInfo controlsInfo;

	private AssetLoader() {
		setupMaps("res/json/maps.json");
		setupAnimations("res/json/animations.json");
		setupSounds("res/json/sounds.json");
		setupFonts("res/json/fonts.json");
		setupImages("res/json/images.json");
		setupPartikel("res/json/partikel.json");
		setupSettings("res/json/settings.json");
		setupInfos("res/json/infos.json");
		setupGameStats("res/json/gameStats.json");
        setupControls("res/json/controls.json");
	}

	// BACKSLASH CHECK
	// ///////////////////////////////////////////////////////////////

	private void checkForBackslashes(String filename) {
		if (filename.contains("\\\\")) {
			throw new IllegalArgumentException(
					"You shall not use backslashes for paths! Check the JSON-files!");
		}
	}

	// SETUP
	// //////////////////////////////////////////////////////////////////////////

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
	
	private void setupControls(String filename) {
        try {
            controlsInfo = JacksonReader.read(filename, ControlsInfo.class);
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
				fontMap.put(fontInfo.name, new AngelCodeFont(fontInfo.file,
						fontInfo.image));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupGameStats(String filename) {
		try {
			gameStats = JacksonReader.read(filename, GameStatsInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupImages(String filename) {
		try {
			List<ImageInfo> imageInfos = JacksonReader.readList(filename, ImageInfo.class);
			for (ImageInfo imageInfo : imageInfos) {
				checkForBackslashes(imageInfo.pfad);
				Image tmpImg = null;
				try {
				    tmpImg = new Image("res/images/"+imageInfo.pfad+".png");
				} catch (Exception e1) {
				    try {
				        tmpImg = new Image("res/images/dummies/"+imageInfo.pfad+".png");
				        Log.warn("AssetLoader: Lade dummy image von '" + filename + "'");
				    } catch (Exception e2) {
				        try {
				            tmpImg = imageMap.get("error");
				            Log.warn("AssetLoader: Image '" + filename + "' existiert weder in res/images noch dummies. Lade error.png");
				        } catch (Exception e3) {
				            Log.warn("AssetLoader: Fatal Error - kann nicht error.png laden.");
				            e1.printStackTrace();
				            e2.printStackTrace();
				            e3.printStackTrace();
				        }
				    }
		        }
				imageMap.put(imageInfo.name, tmpImg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupInfos(String filename) {
		try {
			List<InfoInfo> infoInfos = JacksonReader.readList(filename,
					InfoInfo.class);
			for (InfoInfo infoInfo : infoInfos) {
				infosMap.put(infoInfo.name, infoInfo.info);
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
				mapHashmap.put(mapInfo.name, mapInfo.pfad); // don't load every
															// map during setup
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupPartikel(String filename) {
		try {
			List<PartikelInfo> partikelInfos = JacksonReader.readList(filename,
					PartikelInfo.class);
			for (PartikelInfo partikelInfo : partikelInfos) {
				checkForBackslashes(partikelInfo.pfad);
				partikelMap.put(partikelInfo.name, new ParticleSystem(
						partikelInfo.pfad));
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
				scoreInfos = JacksonReader.readList(
						"res/json/scores/default.json", ScoreInfo.class);
			} catch (Exception e1) {
				e1.printStackTrace();
				e.printStackTrace();
			}
		}
		return scoreInfos;
	}

	private void setupSettings(String filename) {
		try {
			settings = JacksonReader.read(filename, SettingsInfo.class);
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

	// GET
	// //////////////////////////////////////////////////////////////////////////

	public Animation getAnimation(String name) {
		if (animMap.get(name) == null) {
		    Log.warn("AssetLoader: Animation '" + name + "' existiert nicht.");
			return animMap.get("error").copy();
		} else {
			return animMap.get(name).copy();
		}
	}
	
	public ControlsInfo getControls() {
        return controlsInfo;
    }

	public Font getFont(String name) {
	    if(fontMap.get(name) == null){
	        Log.warn("AssetLoader: Font '" + name + "' existiert nicht. Du bekommst 'verdana_90'.");
            return fontMap.get("verdana_90");      
	    }else{
	        return fontMap.get(name);
	    }
	}

	public GameStatsInfo getGameStats() {
		return gameStats;
	}

	public Image getImage(String name) {
		Image image = imageMap.get(name);
		if (image == null) {
			Log.warn("AssetLoader: Image '" + name + "' existiert nicht.");
			return imageMap.get("error");
		} else {
			return image;
		}
	}

	public String getInfo(String name) {
	    if(infosMap.get(name) == null){
	        Log.warn("AssetLoader: Info '" + name + "' existiert nicht.");
	        return infosMap.get("error");
	    }else{
	        return infosMap.get(name);
	    }	
	}

	/**
	 * 
	 * @param name
	 *            JSON map name
	 * @return Path to map file
	 */
	public String getMapPath(String name) {
		return mapHashmap.get(name);
	}

	public ParticleSystem getParticle(String name) {
		if (partikelMap.get(name) == null) {
		    Log.warn("AssetLoader: Partikelanimtion '" + name + "' existiert nicht.");
			return partikelMap.get("error");
		} else {
			return partikelMap.get(name);
		}
	}

	public List<ScoreInfo> getScore(String scoreName) {
		return setupScore("res/json/scores/" + scoreName + ".json");
	}

	public SettingsInfo getSettings() {
		return settings;
	}

	public Sound getSound(String name) {
	    if(soundMap.get(name) == null){
	        Log.warn("AssetLoader: Sound '" + name + "' existiert nicht.");
	        return soundMap.get("error");
	    }else{
	        return soundMap.get(name);
	    }
	}

	//WRITE
	// ///////////////////////////////////////////////////////////////////////
	
	public void writeSettings(SettingsInfo settings) {
		try {
			JacksonWriter.write("res/json/settings.json", settings);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeScore(String scoreName, List<ScoreInfo> scoreList) {
		try {
			JacksonWriter.writeList("res/json/scores/" + scoreName + ".json",
					scoreList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// LOADMAP
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param mapname
	 *            Mapname from JSON-Files
	 * @return Loaded TiledMap
	 * @throws Exception
	 */
	public TiledMap loadMap(String mapname) throws Exception {
		AssetLoader assetLoader = AssetLoader.getInstance();
		TiledMap map = new TiledMap(assetLoader.getMapPath(mapname),
				LayerObject.PolyMode.ABSOLUTE);
		return map;
	}
}
