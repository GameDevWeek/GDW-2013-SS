//Author: Jerry, Thomas M.
package de.fhtrier.gdw.ss2013.physics;

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
    

}
