package de.fhtrier.gdw.ss2013.map;

import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;

public class MapLoader {
    private static MapLoader instance;
    
    public static MapLoader getInstance() {
        if (instance == null) {
            instance = new MapLoader();
        }
        return instance;
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
}
