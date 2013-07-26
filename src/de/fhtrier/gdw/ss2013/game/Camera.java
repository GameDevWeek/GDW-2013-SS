package de.fhtrier.gdw.ss2013.game;

import de.fhtrier.gdw.commons.tiled.TiledMap;

/**
 * Helper class to calculate the best camera offset for rendering the map
 *
 * @author Santo Pfingsten
 */
class Camera {

    private final int mapPixelWidth;
    private final int mapPixelHeight;
    private final int mapTileWidth;
    private final int mapTileHeight;
    private int numTilesX;
    private int numTilesY;
    private int tileX;
    private int tileY;
    private int tileOverlapX;
    private int tileOverlapY;
    private int offsetY;
    private int offsetX;

    public Camera(TiledMap map) {
        mapTileWidth = map.getTileWidth();
        mapTileHeight = map.getTileHeight();
        mapPixelWidth = mapTileWidth * map.getWidth();
        mapPixelHeight = mapTileHeight * map.getHeight();
    }

    private int limit(float pos, float windowSize, float mapSize) {
        float camX = pos - windowSize * 0.5f;
        if (camX < 0) {
            camX = 0;
        } else if (camX > mapSize - windowSize) {
            camX = mapSize - windowSize;
        }
        return (int) Math.floor(camX);
    }

    public void update(int windowWidth, int windowHeight, float playerX, float playerY) {
        numTilesX = windowWidth / mapTileWidth + 2;
        numTilesY = windowHeight / mapTileHeight + 2;

        tileX = tileY = offsetX = offsetY = 0;
        tileOverlapX = tileOverlapY = 0;

        if (windowWidth > mapPixelWidth) {
            // Center in window
            offsetX = (int) Math.floor((windowWidth - mapPixelWidth) * 0.5);
        } else if (windowWidth < mapPixelWidth) {
            offsetX = limit(playerX, windowWidth, mapPixelWidth);
            tileX = (int) Math.floor(getOffsetX() / (float) mapTileWidth);
            tileOverlapX = getOffsetX() % mapTileWidth;
        }
        if (windowHeight > mapPixelHeight) {
            // Center in window
            offsetY = (int) Math.floor((windowHeight - mapPixelHeight) * 0.5);
        } else if (windowHeight < mapPixelHeight) {
            offsetY = limit(playerY, windowHeight, mapPixelHeight);
            tileY = (int) Math.floor(getOffsetY() / (float) mapTileHeight);
            tileOverlapY = getOffsetY() % mapTileHeight;
        }
    }

    public int getNumTilesX() {
        return numTilesX;
    }

    public int getNumTilesY() {
        return numTilesY;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public int getTileOverlapX() {
        return tileOverlapX;
    }

    public int getTileOverlapY() {
        return tileOverlapY;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getOffsetX() {
        return offsetX;
    }
}