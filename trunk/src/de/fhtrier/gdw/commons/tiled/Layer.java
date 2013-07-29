package de.fhtrier.gdw.commons.tiled;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.commons.tiled.decoders.Base64DataDecoder;
import de.fhtrier.gdw.commons.tiled.decoders.CsvDataDecoder;
import de.fhtrier.gdw.commons.tiled.decoders.IDataDecoder;
import de.fhtrier.gdw.commons.tiled.decoders.XmlDataDecoder;
import de.fhtrier.gdw.commons.tiled.tmx.TmxData;
import de.fhtrier.gdw.commons.tiled.tmx.TmxLayer;
import de.fhtrier.gdw.commons.tiled.tmx.TmxLayerBase;
import de.fhtrier.gdw.commons.tiled.tmx.TmxObject;
import de.fhtrier.gdw.commons.tiled.tmx.TmxObjectGroup;
import de.fhtrier.gdw.commons.utils.SafeProperties;

/**
 * A layer which can contain either tiles or objects
 * 
 * @author Santo Pfingsten
 */
public class Layer {

    /**
     * The map this layer belongs to
     */
    private final TiledMap map;
    /**
     * The type of this layer
     */
    private final Type type;
    /**
     * The index of this layer
     */
    private final int index;
    /**
     * The name of this layer - read from the XML
     */
    private final String name;
    /**
     * The width of this layer
     */
    private final int width;
    /**
     * The height of this layer
     */
    private final int height;
    /**
     * The layer opacity
     */
    private float opacity;
    /**
     * The original layer opacity
     */
    private final float originalOpacity;
    /**
     * the properties of this layer
     */
    private final SafeProperties properties;
    /**
     * The tile informations for this layer. null on object layers
     */
    private final TileInfo[][] tiles;
    /**
     * The layer objects. null on tile layers
     */
    private final ArrayList<LayerObject> objects;
    /**
     * An attachment for the game to store additional information
     */
    private Object attachment;

    /**
     * All possible layer types
     */
    public enum Type {

        TILE, OBJECT
    }

    /**
     * Create a new layer based on the XML definition
     * 
     * @param element
     *            The XML element describing the layer
     * @param map
     *            The map this layer is part of
     * @param index
     *            The index
     * @throws SlickException
     *             Indicates a failure to parse the XML layer
     */
    Layer(TiledMap map, TmxLayerBase element, int index,
            LayerObject.PolyMode polyMode) throws Exception {
        this.index = index;
        this.map = map;
        name = element.getName();
        width = element.getWidth();
        height = element.getHeight();
        Float o = element.getOpacity();
        opacity = o == null ? 1.0f : o.floatValue();
        originalOpacity = opacity;

        // now read the layer properties
        properties = TiledMap.readProperties(element.getProperties());

        if (element instanceof TmxLayer) {
            type = Type.TILE;
            tiles = loadTiles((TmxLayer) element);
            objects = null;
        } else {
            type = Type.OBJECT;
            objects = loadObjects((TmxObjectGroup) element, polyMode);
            tiles = null;
        }
    }

    private String getNodeValue(List<Serializable> content) {
        if (content.isEmpty() || !(content.get(0) instanceof String)) {
            return "";
        }
        return (String) content.get(0);
    }

    private IDataDecoder createDecoder(String encoding, TmxData dataNode)
            throws Exception {
        if (encoding == null || encoding.isEmpty() || encoding.equals("xml")) {
            return new XmlDataDecoder(dataNode.getContent());
        } else if (encoding.equals("csv")) {
            return new CsvDataDecoder(getNodeValue(dataNode.getContent()));
        } else if (encoding.equals("base64")) {
            return new Base64DataDecoder(getNodeValue(dataNode.getContent()),
                    dataNode.getCompression());
        } else {
            throw new Exception("Unsupport encoding: " + encoding
                    + ". We currently support base64, csv and none(xml)");
        }
    }

    /**
     * Load all tiles from the layer
     * 
     * @param element
     *            the layer element
     * @return an array containing all tile information of this layer
     * @throws IOException
     * @throws Exception
     */
    private TileInfo[][] loadTiles(TmxLayer element) throws IOException,
            Exception {
        TmxData dataNode = element.getData();
        String encoding = dataNode.getEncoding();
        try (IDataDecoder decoder = createDecoder(encoding, dataNode);) {
            TileInfo[][] result = new TileInfo[width][height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int tileId = decoder.getNextId();
                    if (tileId == 0) {
                        result[x][y] = null;
                    } else {
                        TileSet set = map.findTileSet(tileId);

                        if (set != null) {
                            result[x][y] = new TileInfo(set.getIndex(), tileId
                                    - set.getFirstGID(), tileId,
                                    set.getTileProperties(tileId));
                        } else {
                            result[x][y] = new TileInfo(0, 0, tileId, null);
                        }
                    }
                }
            }

            return result;
        }
    }

    /**
     * Load all objects from the layer
     * 
     * @param element
     *            the object group
     * @param polyMode
     *            the PolyMode to use
     * @return an ArrayList containing all objects
     * @throws Exception
     */
    private ArrayList<LayerObject> loadObjects(TmxObjectGroup element,
            LayerObject.PolyMode polyMode) {
        ArrayList<LayerObject> result = new ArrayList<>();
        for (TmxObject object : element.getObject()) {
            result.add(new LayerObject(map, object, polyMode));
        }
        return result;
    }

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @return true if this is a tile layer
     */
    public boolean isTileLayer() {
        return type == Type.TILE;
    }

    /**
     * @return true if this is an object layer
     */
    public boolean isObjectLayer() {
        return type == Type.OBJECT;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the opacity of this layer
     */
    public float getOpacity() {
        return opacity;
    }

    /**
     * @return the original opacity of this layer
     */
    public float getOriginalOpacity() {
        return originalOpacity;
    }

    /**
     * @param opacity
     *            the opacity for this layer
     */
    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public SafeProperties getProperties() {
        return properties;
    }

    public String getProperty(String propertyName, String def) {
        if (properties == null) {
            return def;
        }
        return properties.getProperty(propertyName, def);
    }

    public int getIntProperty(String propertyName, int def) {
        if (properties == null) {
            return def;
        }
        return properties.getInt(propertyName, def);
    }

    public float getFloatProperty(String propertyName, float def) {
        if (properties == null) {
            return def;
        }
        return properties.getFloat(propertyName, def);
    }

    public double getDoubleProperty(String propertyName, double def) {
        if (properties == null) {
            return def;
        }
        return properties.getDouble(propertyName, def);
    }

    public boolean getBooleanProperty(String propertyName, boolean def) {
        if (properties == null) {
            return def;
        }
        return properties.getBoolean(propertyName, def);
    }

    /**
     * @return the tile informations for this layer. null on object layers
     */
    public TileInfo[][] getTiles() {
        return tiles;
    }

    /**
     * @return the layer objects. null on tile layers
     */
    public ArrayList<LayerObject> getObjects() {
        return objects;
    }

    /**
     * @return the attachment
     */
    public Object getAttachment() {
        return attachment;
    }

    /**
     * @param attachment
     *            an attachment for the game to store additional information
     */
    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }
}
