//Author: Jerry, Thomas M.
package de.fhtrier.gdw.ss2013.physics;

import java.awt.Point;
import java.util.List;

import org.jbox2d.common.Vec2;

public class PhysicsTools {
    
    public static final float Pixel2WorldScale = 0.0181f;
    
    public static float PixelToWorld(float input)
    {
        return input*Pixel2WorldScale;
    }
    
    public static Vec2 PixelToWorld(Vec2 input)
    {
        return input.mul(Pixel2WorldScale);
    }
    
    public static float WorldToPixel(float input)
    {
        return input/Pixel2WorldScale;
    }

    public static Vec2 WorldToPixel(Vec2 input)
    {
        Vec2 returner = new Vec2(input);
        returner.x /= Pixel2WorldScale;
        returner.y /= Pixel2WorldScale;
        
        return returner;
    }
    
    public static Vec2 PointToVec2(Point point)
    {
        return new Vec2(point.x,point.y);
    }
    
    public static Point Vec2ToPoint(Vec2 v)
    {
        return new Point((int)v.x,(int)v.y);
    }
    
    public static Vec2[] PointArrToVec2Arr(List<Point> pointList)
    {
        Vec2[] returner = new Vec2[pointList.size()];
        for(int pointCount = 0;pointCount < returner.length; pointCount++)
        {
            returner[pointCount] = PointToVec2(pointList.get(pointCount));
        }
        return returner;
    }

}
