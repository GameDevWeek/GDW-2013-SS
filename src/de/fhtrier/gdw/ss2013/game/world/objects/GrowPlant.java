package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.game.EntityCollidable;

public class GrowPlant extends EntityCollidable {
   
    public GrowPlant() {
        
       // setParticle(AssetLoader.getInstance().getParticle("pollen1"));
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
