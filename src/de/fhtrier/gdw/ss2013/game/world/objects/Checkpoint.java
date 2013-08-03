package de.fhtrier.gdw.ss2013.game.world.objects;


import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.MathConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.LevelLoader;
import de.fhtrier.gdw.ss2013.game.world.World;

public class Checkpoint extends EntityCollidable {
    
   
   public Checkpoint(){
       super(AssetLoader.getInstance().getImage("Checkpoint"));
   }

   @Override
   public void initPhysics() {
       createPhysics(BodyType.STATIC, origin.x, origin.y).sensor(true).asBox(
               initialSize.x, initialSize.y);
   }

   @Override
   public void beginContact(Contact contact) {

       Entity other = getOtherEntity(contact);
       if(other instanceof Astronaut) {
           Astronaut player = World.getInstance().getAstronaut();
           player.setOrigin(player.getPosition()); 
       }
    
   }

   @Override
   public void endContact(Contact object) {
    
    

   }

}
