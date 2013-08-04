package de.fhtrier.gdw.ss2013.game.world.objects;


import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Sound;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.constants.MathConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.LevelLoader;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

public class Checkpoint extends EntityCollidable {
    private SoundPlayer soundPlayer;
    private Sound checkpointSound;
    private boolean activated;
   
   public Checkpoint(){
       super(AssetLoader.getInstance().getImage("Checkpoint"));
   }

   @Override
   protected void initialize() {
       super.initialize();
       soundPlayer = SoundLocator.getPlayer();
       checkpointSound = SoundLocator.loadSound("checkpoint");
       activated = false;
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
           if (!activated) {
               soundPlayer.playSoundAt(checkpointSound, this);
               activated = true;
           }
       }
    
   }

   @Override
   public void endContact(Contact object) {
    
    

   }

}
