package de.fhtrier.gdw.ss2013.game.camera;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class ThreePointCamera {

    private final Vector2f cameraPosition;
    private final Vector2f focusPoint;
    private final Vector2f topLeftPoint;
    private final int mapPixelWidth;
    private final int mapPixelHeight;
    private final int mapTileWidth;
    private final int mapTileHeight;
    private final Vector2f targetPos;
    private final float cameraSpeed;

    private static final float CAM_ZOOM_EPSILON = 0.0001f;

    private float zoomFactor;

    private final LinkedList<PointOfInterest> pointOfInterests;
    
    private final Vector2f viewport;


    public ThreePointCamera(CameraInfo info) {
        targetPos = new Vector2f();
        cameraSpeed = info.cameraSpeed;
        viewport = new Vector2f();
        pointOfInterests = new LinkedList<>();
        topLeftPoint = new Vector2f();
        cameraPosition = new Vector2f();
        focusPoint = new Vector2f();
        zoomFactor = 0.0f;

        mapTileWidth = info.map.getTileWidth();
        mapTileHeight = info.map.getTileHeight();
        mapPixelWidth = mapTileWidth * info.map.getWidth();
        mapPixelHeight = mapTileHeight * info.map.getHeight();
    }

    public Vector2f getFocus() {
        return focusPoint;
    }

    public void setFocus(float x, float y) {
        focusPoint.set(x, y);
    }

    private float zoomedTranslateX = 0.0f;
    private float zoomedTranslateY = 0.0f;

    public void update(int deltaTime, int width, int height, float targetX,
            float targetY) {

        float dt = deltaTime / 1000.f;
        
        viewport.set(width,height);

        if (!pointOfInterests.isEmpty()) {
            Iterator<PointOfInterest> iterator = pointOfInterests.iterator();
            float totalWeight = 2.0f;
            float finalPosX = targetX;
            float finalPosY = targetY;
            do {

//                System.out
//                        .println("Step(" + finalPosX + ", " + finalPosY + ")");
                PointOfInterest poi = iterator.next();
                if( ((targetX - poi.x )* (targetX - poi.x) + (targetY-poi.y) * (targetY-poi.x)) > poi.radius*poi.radius) {
                    continue;
                }
                float poiTotalWeight = totalWeight + poi.weight;

                finalPosX = finalPosX * ((totalWeight / poiTotalWeight))
                        + (poi.x) * (poi.weight / poiTotalWeight);

                finalPosY = finalPosY * ((totalWeight / poiTotalWeight))
                        + (poi.y) * (poi.weight / poiTotalWeight);
                totalWeight += poi.weight;

            } while (iterator.hasNext());

//            System.out.println("Final(" + finalPosX + ", " + finalPosY + ")");

            focusPoint.set(focusPoint.x + (finalPosX - focusPoint.x) * (dt*cameraSpeed),
                    focusPoint.y + (finalPosY - focusPoint.y) * (dt*cameraSpeed));
            // focusPoint.set(finalPosX, finalPosY);

        } else {
            focusPoint.set(focusPoint.x + (targetX - focusPoint.x) * (dt*cameraSpeed),
                    focusPoint.y + (targetY - focusPoint.y) * (dt*cameraSpeed));
        }
        
        targetPos.set(targetX - focusPoint.x, targetY - focusPoint.y);
        
        cameraPosition
                .set(cameraPosition.x + (focusPoint.x - cameraPosition.x) * dt*cameraSpeed,
                        cameraPosition.y + (focusPoint.y - cameraPosition.y)
                                * dt*cameraSpeed);

        float xadjust = width * this.zoomFactor ;
        float yadjust = height * this.zoomFactor ;

        zoomedTranslateX = -xadjust;
        zoomedTranslateY = -yadjust;
        
        
        topLeftPoint.set(focusPoint.x - width/2, focusPoint.y - height/2);
//        topLeftPoint.set(-width/2 * scaleX() + a, -height/2 * scaleX() + b);

        updateTiled(width, height, focusPoint.x, focusPoint.y);
    }

    public Vector2f screenToWorldPosition(Vector2f screen) {
//        System.out.println(screen);
        Vector2f world = new Vector2f();
        world.set(this.topLeftPoint.x + screen.x, this.topLeftPoint.y + screen.y);
//        System.out.println(world);
        
        return world;
    }
    
    public Vector2f screenToWorldPositionFromTarget(Vector2f screen) {
        Vector2f world = new Vector2f();
//        System.out.print(targetPos); System.out.println(focusPoint);
        
        world.set(this.topLeftPoint.x + targetPos.x + screen.x, this.topLeftPoint.y + targetPos.y + screen.y);
//        System.out.println(world);
        
        return world;
    }
    
    // PRIORITY!
    public Vector2f worldToScreenPosition(Vector2f world) {
        Vector2f screen = new Vector2f();
        
        screen.set(-topLeftPoint.x + world.x, -topLeftPoint.y  + world.y);
//        System.out.println(screen);
//        System.out.println(world);
        return screen;
    }
    
    public float getZoomedTranslateX() {
        return zoomedTranslateX;
    }

    public float getZoomedTranslateY() {
        return zoomedTranslateY;
    }

    public float scaleX() {
        return (1 + zoomFactor);
    }

    public float scaleY() {
        return (1 + zoomFactor);
    }

    public Vector2f getTopLeftPoint() {
        return topLeftPoint;
    }

    /**
     * 
     * @param zoomFactor
     *            [-1,1] value to define zoom (1 = 100% ; -1 = -100% zoom) (zoom
     *            ist nicht proportional)
     */
    public void setZoom(float zf) {
        this.zoomFactor = Math.min(Math.max(zf, -1 + CAM_ZOOM_EPSILON), 1.f);
    }

    public void zoom(float zf) {
        this.zoomFactor = Math.min(
                Math.max(zoomFactor + zf, -1 + CAM_ZOOM_EPSILON), 1.f);
    }

    public void addPointOfInterest(PointOfInterest poi) {
        pointOfInterests.add(poi);
    }

    public void debugdraw(Graphics g, float targetX, float targetY) {
        g.setColor(Color.blue);
        g.drawRect(cameraPosition.x - 5, cameraPosition.y - 5, 10, 10);
        g.drawString("camPos", cameraPosition.x, cameraPosition.y);

        g.setColor(Color.white);
        g.drawRect(focusPoint.x - 5, focusPoint.y - 5, 10, 10);
        g.drawString("focus", focusPoint.x, focusPoint.y);

        g.setColor(Color.green);
        g.drawRect(targetX - 5, targetY - 5, 10, 10);
        g.drawString("target", targetX, targetY - 10);

        for (PointOfInterest p : pointOfInterests) {
            g.setColor(Color.cyan);
            g.drawRect(p.x - 5, p.y - 5, 10, 10);
            g.drawString("PoI(" + p.weight + ")", p.x, p.y);
            

            g.drawOval(p.x - p.radius, p.y - p.radius, p.radius*2, p.radius*2);
        }



    }

    public void pushViewMatrix(Graphics g) {
        g.pushTransform();
        g.translate(this.getZoomedTranslateX() / 2 ,
                this.getZoomedTranslateY() / 2);

        g.scale(this.scaleX(), this.scaleY());
        g.translate(-this.topLeftPoint.x , -this.topLeftPoint.y);
    }
    
    public void popViewMatrix(Graphics g) {
        g.popTransform();
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

    public void updateTiled(int windowWidth, int windowHeight, float playerX,
            float playerY) {
        numTilesX = windowWidth / mapTileWidth + 2;
        numTilesY = windowHeight / mapTileHeight + 2;
        
        

        tileX = tileY = offsetX = offsetY = 0;
        tileOverlapX = tileOverlapY = 0;

        if (windowWidth > mapPixelWidth) {
            // Center in window
            offsetX = (int) Math.floor((windowWidth - mapPixelWidth) * 0.5);
        } else if (windowWidth < mapPixelWidth) {
//            offsetX = limit(playerX, windowWidth, mapPixelWidth);
            tileX = (int) Math.floor(focusPoint.x / (float) mapTileWidth);
            tileOverlapX = getOffsetX() % mapTileWidth;
        }
        if (windowHeight > mapPixelHeight) {
            // Center in window
            offsetY = (int) Math.floor((windowHeight - mapPixelHeight) * 0.5);
        } else if (windowHeight < mapPixelHeight) {
//            offsetY = limit(playerY, windowHeight, mapPixelHeight);
            tileY = (int) Math.floor(focusPoint.y / (float) mapTileHeight);
            tileOverlapY = getOffsetY() % mapTileHeight;
        }
    }
    
    private int numTilesX; // x tiles rendered
    private int numTilesY; // y tiles renderd
    private int tileX;
    private int tileY;
    private int tileOverlapX;
    private int tileOverlapY;
    private int offsetY;
    private int offsetX;

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
        return (int) focusPoint.x;
    }

    public int getOffsetX() {
        return (int) focusPoint.y;
    }
}
