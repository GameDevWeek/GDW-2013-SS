package de.fhtrier.gdw.ss2013.game.camera;

public class PointOfInterest {
    public final float weight;
    public final float x;
    public final float y;
    
    public final float radius;

    public PointOfInterest(float _x, float _y, float weight, float radius) {
        this.weight = weight;
        x = _x;
        y = _y;
        this.radius = radius;
    }
}
