package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.geom.Vector2f;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import org.jbox2d.dynamics.contacts.Contact;

public class GrowPlant extends EntityCollidable {
    private GrowPlant next;
    private Vector2f growVelocity;
    private int number;
   
    public GrowPlant() {
        
       // setParticle(AssetLoader.getInstance().getParticle("pollen1"));
    }
    
    public void init(GrowPlant n, Vector2f velo, int num) {
        next = n;
        growVelocity = velo;
        number = num;
    }
    
    public void grow() {
        
    }

    @Override
    public void beginContact(Contact object) {
    }

    @Override
    public void endContact(Contact object) {
    }

 
    
    
}
