package de.fhtrier.gdw.ss2013.util;

import org.newdawn.slick.geom.Vector2f;

public class VectorUtil {

    public static Vector2f subtract(Vector2f v1, Vector2f v2) {
        Vector2f v = new Vector2f(v1);
        return v.sub(v2);
    }
}
