//Author: Jerry, Thomas M.
package de.fhtrier.gdw.ss2013.physics;

import java.awt.Point;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.camera.Camera;

public class Transform {

    public static final float PIXEL2WORLDSCALE = 0.0181f;

    private Camera camera;

    public Transform(Camera cam) {
        camera = cam;
    }

    public void transformToGameWorld(Vector2f transformedPosition,
            Vec2 physicsPosition) {

    }

    public void transformToPhysicWorld(Vec2 physicsPosition,
            Vector2f transformedPosition) {

        transformedPosition.set(physicsPosition.x * PIXEL2WORLDSCALE,
                physicsPosition.y * PIXEL2WORLDSCALE);
    }

    public static Vec2 worldToPixel(Vec2 input) {
        Vec2 returner = new Vec2(input);
        returner.x /= PIXEL2WORLDSCALE;
        returner.y /= PIXEL2WORLDSCALE;

        return returner;
    }

    public static Vec2 pointToVec2(Point point) {
        return new Vec2(point.x, point.y);
    }

    public static Point vec2ToPoint(Vec2 v) {
        return new Point((int) v.x, (int) v.y);
    }

    public static Vec2[] pointListToVec2Arr(List<Point> pointList) {
        Vec2[] returner = new Vec2[pointList.size()];
        for (int pointCount = 0; pointCount < returner.length; pointCount++) {
            returner[pointCount] = pointToVec2(pointList.get(pointCount));
        }
        return returner;
    }

    public Vector2f screenToWorldPosition(Vector2f screenPosition) {
        /**
         * Top-left (0,0) / Bottom-right (width,height)
         */
        Vector2f worldPos = new Vector2f(camera.getOffsetX(),
                camera.getOffsetY());

        return worldPos.add(screenPosition);
    }

    public Vector2f worldToScreenPosition(Vector2f worldPosition) {
        Vector2f screenPos = new Vector2f(-camera.getOffsetX(),
                -camera.getOffsetY());

        return screenPos.add(worldPosition);
    }
}
