package de.fhtrier.gdw.commons.tiled;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.fhtrier.gdw.commons.interfaces.IResourceLocator;
import de.fhtrier.gdw.commons.tiled.tmx.TmxLayerBase;
import de.fhtrier.gdw.commons.tiled.tmx.TmxMap;
import de.fhtrier.gdw.commons.tiled.tmx.TmxProperties;
import de.fhtrier.gdw.commons.tiled.tmx.TmxProperty;
import de.fhtrier.gdw.commons.tiled.tmx.TmxTileSet;
import de.fhtrier.gdw.commons.utils.SafeProperties;

/**
 * A TilED map loader
 * 
 * @author Santo Pfingsten
 */
public class TiledMap implements IResourceLocator {

	/**
	 * The map width (in tiles)
	 */
	protected final int width;
	/**
	 * The map height (in tiles)
	 */
	protected final int height;
	/**
	 * The default tile width (TileSets may have varying values)
	 */
	protected final int tileWidth;
	/**
	 * The default tile height (TileSets may have varying values)
	 */
	protected final int tileHeight;
	/**
	 * The map properties
	 */
	protected final SafeProperties properties;
	/**
	 * The list of TileSets
	 */
	protected final ArrayList<TileSet> tileSets = new ArrayList<>();
	/**
	 * The list of Layers
	 */
	protected final ArrayList<Layer> layers = new ArrayList<>();
	/**
	 * The resource locator
	 */
	private final IResourceLocator resourceLocator;
	/**
	 * The filename of this map
	 */
	private final String filename;

	/**
	 * Load a map using a the default resource locator (file system) and the
	 * default PolyMode (RELATIVE_TO_FIRST)
	 * 
	 * @param filename
	 *            the filename of the file to load
	 * @throws Exception
	 *             when the map could not be loaded
	 */
	public TiledMap(String filename) throws Exception {
		this(filename, LayerObject.PolyMode.RELATIVE_TO_FIRST, null);
	}

	/**
	 * Load a map using a the default resource locator (file system)
	 * 
	 * @param filename
	 *            the filename of the file to load
	 * @param polyMode
	 *            The mode how polygon points should be converted
	 * @throws Exception
	 *             when the map could not be loaded
	 */
	public TiledMap(String filename, LayerObject.PolyMode polyMode)
			throws Exception {
		this(filename, polyMode, null);
	}

	/**
	 * Load a map using a custom resource locator
	 * 
	 * @param filename
	 *            the filename of the file to load
	 * @param polyMode
	 *            The mode how polygon points should be converted
	 * @param resourceLocator
	 *            the custom resource locator
	 * @throws Exception
	 *             when the map could not be loaded
	 */
	public TiledMap(String filename, LayerObject.PolyMode polyMode,
			IResourceLocator resourceLocator) throws Exception {
		this.filename = filename;
		this.resourceLocator = resourceLocator;

		try {
			TmxMap map = (TmxMap) readFrom(filename);
			String orient = map.getOrientation();
			if (!orient.equals("orthogonal")) {
				throw new Exception("Only orthogonal maps supported, found: "
						+ orient);
			}

			width = map.getWidth();
			height = map.getHeight();
			tileWidth = map.getTilewidth();
			tileHeight = map.getTileheight();
			// now read the map properties
			properties = readProperties(map.getProperties());

			TileSet lastSet = null;

			int i = 0;
			for (TmxTileSet current : map.getTileset()) {
				TileSet tileSet = new TileSet(this, current, i++);

				if (lastSet != null) {
					lastSet.setLimit(tileSet.getFirstGID() - 1);
				}
				lastSet = tileSet;

				tileSets.add(tileSet);
			}

			i = 0;
			for (TmxLayerBase current : map.getLayerOrObjectgroup()) {
				Layer layer = new Layer(this, current, i++, polyMode);
				layers.add(layer);
			}
		} catch (Exception e) {
			throw new Exception("Failed to parse tilemap", e);
		}
	}

	/**
	 * @return the file name of this map
	 */
	String getFilename() {
		return filename;
	}

	/**
	 * @return the list of TileSets
	 */
	public ArrayList<TileSet> getTileSets() {
		return tileSets;
	}

	/**
	 * @return The map width (in tiles)
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return The map height (in tiles)
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return The default tile height (TileSets may have varying values)
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * @return The default tile width (TileSets may have varying values)
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * @return The list of Layers
	 */
	public ArrayList<Layer> getLayers() {
		return layers;
	}

	/**
	 * Find a layer by its name
	 * 
	 * @param name
	 *            The name of the layer
	 * @return The layer or null if not found
	 */
	public Layer findLayer(String name) {
		for (Layer layer : layers) {
			if (name.equals(layer.getName())) {
				return layer;
			}
		}

		return null;
	}

	/**
	 * @return the properties for this map
	 */
	SafeProperties getProperties() {
		return properties;
	}

	/**
	 * Get a map property
	 * 
	 * @param key
	 *            the key of the property
	 * @param def
	 *            the default value to return if the property has not been set.
	 * @return the property value or def
	 */
	public String getProperty(String key, String def) {
		if (properties == null) {
			return def;
		}
		return properties.getProperty(key, def);
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
	 * Find a TileSet by a global tile id
	 * 
	 * @param gid
	 *            the global tile id
	 * @return the TileSet matching the tile id or null if not found
	 */
	public TileSet findTileSet(int gid) {
		for (TileSet set : tileSets) {
			if (set.contains(gid)) {
				return set;
			}
		}

		return null;
	}

	/**
	 * Find an object by its name
	 * 
	 * @param name
	 *            The name of the object
	 * @return The layer or null if not found
	 */
	public LayerObject findObject(String name) {
		for (Layer layer : layers) {
			if (layer.getType() == Layer.Type.OBJECT) {
				for (LayerObject object : layer.getObjects()) {
					if (name.equals(object.getName())) {
						return object;
					}
				}
			}
		}

		return null;
	}

	/**
	 * @return The current resource locator or this, if null
	 */
	public IResourceLocator getResourceLocator() {
		if (resourceLocator == null) {
			return this;
		} else {
			return resourceLocator;
		}
	}

	@Override
	public InputStream locateResource(String filename)
			throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(new File(filename)));
	}

	@Override
	public String combinePaths(String parent, String filename) {
		File file = new File(new File(parent).getParentFile(), filename);
		return file.toString();
	}

	/**
	 * Read an xml file
	 * 
	 * @param filename
	 *            the file to read from
	 * @return a TmxTileSet object or a TmxMap object
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	final Object readFrom(String filename) throws JAXBException,
			FileNotFoundException, IOException {
		IResourceLocator locator = getResourceLocator();
		try (InputStream in = locator.locateResource(filename);) {
			JAXBContext jc = JAXBContext
					.newInstance("de.fhtrier.gdw.commons.tiled.tmx");
			Unmarshaller u = jc.createUnmarshaller();
			JAXBElement o = (JAXBElement) u.unmarshal(in);
			return o.getValue();
		}
	}

	/**
	 * Read properties from an xml element
	 * 
	 * @param element
	 *            the properties element
	 * @return a new SafeProperties object containing all key/value pairs
	 */
	static SafeProperties readProperties(TmxProperties element) {
		SafeProperties properties = null;
		if (element != null) {
			properties = new SafeProperties();
			for (TmxProperty property : element.getProperty()) {
				properties.setProperty(property.getName(), property.getValue());
			}
		}
		return properties;
	}
}
