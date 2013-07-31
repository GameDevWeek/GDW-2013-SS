package de.fhtrier.gdw.ss2013.physix;

import static de.fhtrier.gdw.ss2013.physics.Transform.pointToVec2;
import java.awt.Point;
import java.util.List;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.geom.Vector2f;

public class PhysixUtil {

    public static final float SCALE = 40;

    /**
     * Convert world to box2d coordinates
     */
    public static float toBox2D(float pixel) {
        return pixel / SCALE;
    }

    /**
     * Convert box2d to world coordinates
     */
    public static float toWorld(float num) {
        return num * SCALE;
    }

    /**
     * Convert world to box2d coordinates
     */
    public static Vec2 toBox2D(Vector2f v) {
        return new Vec2(v.x / SCALE, v.y / SCALE);
    }

    /**
     * Convert box2d to world coordinates
     */
    public static Vector2f toWorld(Vec2 v) {
        return new Vector2f(v.x * SCALE, v.y * SCALE);
    }
    
    public static Vec2[] toBox2D(List<Point> pointList) {
        Vec2[] returner = new Vec2[pointList.size()];
        for (int pointCount = 0; pointCount < returner.length; pointCount++) {
            returner[pointCount] = pointToVec2(pointList.get(pointCount));
        }
        return returner;
    }
}
