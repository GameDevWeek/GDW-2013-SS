package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

public class GrowPlant extends Entity implements ICollidable {
    private GrowPlant next;
    private Vector2f growVelocity;
    private int number;
    
    public GrowPlant(Vector2f pos, GrowPlant n, Vector2f velo, int num) {
        super(pos.copy());
        next = n;
        growVelocity = velo;
        number = num;
    }
    public GrowPlant() {
        this(new Vector2f(), null, new Vector2f(), 0);
    }
    public GrowPlant(Vector2f pos) {
        this(pos.copy(), null, new Vector2f(), 0);
    }
    @Override
    public void onCollision(Entity e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public Fixture getFixture() {
        // TODO Auto-generated method stub
        return null;
    }
    public void grow() {
        
    }
}
