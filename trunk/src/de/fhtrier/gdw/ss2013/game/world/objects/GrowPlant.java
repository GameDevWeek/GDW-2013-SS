package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

public class GrowPlant extends Entity implements ICollidable {
    private GrowPlant next;
    private Vector2f growVelocity;
    private int number;
    
    public GrowPlant() {
    }
    
    public void init(GrowPlant n, Vector2f velo, int num) {
        next = n;
        growVelocity = velo;
        number = num;
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
