//Author: Jerry, Thomas M.
package de.fhtrier.gdw.ss2013.physics;

import java.awt.Point;
import java.util.List;

import org.jbox2d.common.Vec2;

public class PhysicsTools {
    
    public static final float PIXEL2WORLDSCALE = 0.0181f;
    
    public static float pixelToWorld(float input)
    {
        return input*PIXEL2WORLDSCALE;
    }
    
    public static Vec2 pixelToWorld(Vec2 input)
    {
        return input.mul(PIXEL2WORLDSCALE);
    }
    
    public static float worldToPixel(float input)
    {
        return input/PIXEL2WORLDSCALE;
    }

    public static Vec2 worldToPixel(Vec2 input)
    {
        Vec2 returner = new Vec2(input);
        returner.x /= PIXEL2WORLDSCALE;
        returner.y /= PIXEL2WORLDSCALE;
        
        return returner;
    }
    
    public static Vec2 pointToVec2(Point point)
    {
        return new Vec2(point.x,point.y);
    }
    
    public static Point vec2ToPoint(Vec2 v)
    {
        return new Point((int)v.x,(int)v.y);
    }
    
    public static Vec2[] pointListToVec2Arr(List<Point> pointList)
    {
        Vec2[] returner = new Vec2[pointList.size()];
        for(int pointCount = 0;pointCount < returner.length; pointCount++)
        {
            returner[pointCount] = pointToVec2(pointList.get(pointCount));
        }
        return returner;
    }

}
