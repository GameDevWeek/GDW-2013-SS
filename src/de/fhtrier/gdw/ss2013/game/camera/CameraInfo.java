package de.fhtrier.gdw.ss2013.game.camera;

import de.fhtrier.gdw.commons.tiled.TiledMap;

public class CameraInfo {
    protected final float cameraSpeed;
    protected final TiledMap map;

    public CameraInfo(float camSpeed, TiledMap map) {
        this.cameraSpeed = camSpeed;
        this.map = map;
    }

    public float cameraSpeed() {
        return cameraSpeed;
    }
}
