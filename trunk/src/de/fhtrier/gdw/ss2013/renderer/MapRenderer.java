package de.fhtrier.gdw.ss2013.renderer;

import de.fhtrier.gdw.commons.interfaces.IResourceLocator;
import de.fhtrier.gdw.commons.tiled.Layer;
import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TileInfo;
import de.fhtrier.gdw.commons.tiled.TileSet;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.commons.tiled.tmx.TmxImage;
import java.awt.Point;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * A simple map renderer which renders the TiledMap with Slick
 *
 * @author Santo Pfingsten
 */
public class MapRenderer {

    final TiledMap map;
    final int mapTileWidth;
    final int mapTileHeight;

    public MapRenderer(TiledMap map) throws SlickException {
        this.map = map;
        mapTileWidth = map.getTileWidth();
        mapTileHeight = map.getTileHeight();

        IResourceLocator locator = map.getResourceLocator();
        for (TileSet tileset : map.getTileSets()) {
            TmxImage img = tileset.getImage();
            String trans = img.getTrans();
            Color transColor = trans == null ? null : Color.decode(trans);
            TiledImage image = new TiledImage(locator.combinePaths(tileset.getFilename(), img.getSource()), transColor);
            tileset.setAttachment(image);
        }
    }

    /**
     * Render the whole tile map at a given location
     *
     * @param x The x location to render at
     * @param y The y location to render at
     */
    public void render(Graphics g, int x, int y) {
        render(g, x, y, 0, 0, map.getWidth(), map.getHeight());
    }

    /**
     * Render a single layer from the map
     *
     * @param x The x location to render at
     * @param y The y location to render at
     * @param layer The layer to render
     */
    public void render(Graphics g, int x, int y, Layer layer) {
        render(g, x, y, 0, 0, map.getWidth(), map.getHeight(), layer);
    }

    /**
     * Render a section of the tile map
     *
     * @param x The x location to render at
     * @param y The y location to render at
     * @param sx The x tile location to start rendering
     * @param sy The y tile location to start rendering
     * @param width The width of the section to render (in tiles)
     * @param height The height of the secton to render (in tiles)
     */
    public void render(Graphics g, int x, int y, int sx, int sy, int width, int height) {
        for (int ty = 0; ty < height; ty++) {
            for (Layer layer : map.getLayers()) {
                renderLayerLine(g, layer, x, y, sx, sy, width, ty);
            }
        }
    }

    /**
     * Render a section of the tile map, but only the tile layers
     *
     * @param x The x location to render at
     * @param y The y location to render at
     * @param sx The x tile location to start rendering
     * @param sy The y tile location to start rendering
     * @param width The width of the section to render (in tiles)
     * @param height The height of the secton to render (in tiles)
     */
    public void renderTileLayers(Graphics g, int x, int y, int sx, int sy, int width, int height) {
        for (int ty = 0; ty < height; ty++) {
            for (Layer layer : map.getLayers()) {
                if (layer.getType() == Layer.Type.TILE) {
                    renderTileLayerLine(g, layer, x, y, sx, sy, width, ty);
                }
            }
        }
    }

    /**
     * Render a section of the specified layer
     *
     * @param x The x location to render at
     * @param y The y location to render at
     * @param sx The x tile location to start rendering
     * @param sy The y tile location to start rendering
     * @param width The width of the section to render (in tiles)
     * @param height The height of the secton to render (in tiles)
     * @param layer The layer to render
     */
    public void render(Graphics g, int x, int y, int sx, int sy, int width, int height, Layer layer) {
        for (int ty = 0; ty < height; ty++) {
            renderLayerLine(g, layer, x, y, sx, sy, width, ty);
        }
    }

    /**
     * Render a section of this layer
     *
     * @param x The x location to render at
     * @param y The y location to render at
     * @param sx The x tile location to start rendering
     * @param sy The y tile location to start rendering
     * @param width The number of tiles across to render
     * @param ty The line of tiles to render
     */
    private void renderLayerLine(Graphics g, Layer layer, int x, int y, int sx, int sy, int width, int ty) {
        switch (layer.getType()) {
            case TILE:
                renderTileLayerLine(g, layer, x, y, sx, sy, width, ty);
                break;
            case OBJECT:
                renderObjectLayerLine(g, layer, x, y, sx, sy, width, ty);
                break;
        }
    }

    /**
     * Render a section of this tile layer
     *
     * @param x The x location to render at
     * @param y The y location to render at
     * @param sx The x tile location to start rendering
     * @param sy The y tile location to start rendering
     * @param width The number of tiles across to render
     * @param ty The line of tiles to render
     */
    private void renderTileLayerLine(Graphics g, Layer layer, int x, int y, int sx, int sy, int width, int ty) {
        if (layer.getBooleanProperty("invisible", false)) {
            return;
        }

        float opacity = layer.getOpacity();
        if (opacity == 0) {
            return;
        }

        Color filter = new Color(1, 1, 1, opacity);

        TileInfo[][] tiles = layer.getTiles();
        for (TileSet tileset : map.getTileSets()) {
            int id = tileset.getIndex();

            TiledImage image = null;
            for (int tx = 0; tx < width; tx++) {
                if ((sx + tx < 0) || (sy + ty < 0)) {
                    continue;
                }
                if (sx + tx >= map.getWidth() || sy + ty >= map.getHeight()) {
                    continue;
                }

                TileInfo info = tiles[sx + tx][sy + ty];
                if (info != null && info.tileSetId == id) {
                    if (image == null) {
                        image = (TiledImage) tileset.getAttachment();
                        image.startUse();
                        filter.bind();
                    }

                    int sheetX = tileset.getTileX(tiles[sx + tx][sy + ty].localId);
                    int sheetY = tileset.getTileY(tiles[sx + tx][sy + ty].localId);

                    int tileOffsetY = tileset.getTileHeight() - mapTileHeight;

                    float px = x + (tx * mapTileWidth);
                    float py = y + (ty * mapTileHeight) - tileOffsetY;

                    image.drawTile(px, py, tileset.getTileWidth(), tileset.getTileHeight(), sheetX * tileset.getTileWidth(), sheetY * tileset.getTileHeight());
                }
            }

            if (image != null) {
                image.endUse();
            }
        }
    }

    /**
     * Render a section of this object layer
     *
     * @param x The x location to render at
     * @param y The y location to render at
     * @param sx The x tile location to start rendering
     * @param sy The y tile location to start rendering
     * @param width The number of tiles across to render
     * @param ty The line of tiles to render
     */
    private void renderObjectLayerLine(Graphics g, Layer layer, int x, int y, int sx, int sy, int width, int ty) {
        g.setLineWidth(2.0f);
        g.setAntiAlias(true);

        int offsX = x + sx * mapTileWidth - x;
        int offsY = sy * mapTileHeight - y;
        g.translate(-offsX, -offsY);

        //fixme: render only the objects actually within the limits
        for (LayerObject object : layer.getObjects()) {
            switch (object.getPrimitive()) {
                case POLYGON:
                    drawPolyLine(g, object.getPoints(), true);
                    break;
                case POLYLINE:
                    drawPolyLine(g, object.getPoints(), false);
                    break;
                case TILE:
                    drawTile(object);
                    break;
                case RECT:
                    g.drawRect(object.getX(), object.getY(), object.getWidth(), object.getHeight());
                    break;
                case POINT:
                    g.drawRect(object.getX() - 1, object.getY() - 1, 2, 2);
                    break;
                default:
                    throw new AssertionError(object.getPrimitive().name());
            }
        }
        g.translate(offsX, offsY);
    }

    private void drawPolyLine(Graphics g, ArrayList<Point> points, boolean close) {
        Point first = null;
        Point last = null;
        for (Point point : points) {
            if (last != null) {
                g.drawLine(last.x, last.y, point.x, point.y);
            } else {
                first = point;
            }
            last = point;
        }
        if (close && last != null && first != null) {
            g.drawLine(last.x, last.y, first.x, first.y);
        }
    }

    private void drawTile(LayerObject object) {
        int gid = object.getGid();
        TileSet tileset = map.findTileSet(gid);
        TiledImage image = (TiledImage) tileset.getAttachment();

        int sheetX = tileset.getTileX(gid - tileset.getFirstGID());
        int sheetY = tileset.getTileY(gid - tileset.getFirstGID());

        image.startUse();
        image.drawTile(object.getLowestX(), object.getLowestY(), tileset.getTileWidth(), tileset.getTileHeight(), sheetX * tileset.getTileWidth(), sheetY * tileset.getTileHeight());
        image.endUse();
    }
}
