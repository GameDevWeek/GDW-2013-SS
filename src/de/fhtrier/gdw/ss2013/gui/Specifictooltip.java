package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.world.World;


public class Specifictooltip {
    
    private Image img;
    private Vector2f position;
    private Vector2f trigger;
    private float triggerRadius;
    private static World worldinstance;
    
    public Specifictooltip(Image img, Vector2f position, Vector2f trigger, float triggerRadius){
        
        init(img, position, trigger, triggerRadius);
        
    }
    
    public void init(Image img, Vector2f position, Vector2f trigger, float triggerRadius){
        
        this.img = img;
        this.position = position;
        this.trigger = trigger;
        this.triggerRadius = triggerRadius;    
    }

    public void update(){
        
    }
    
    public void render(){
        Vector2f worldPosition = worldinstance.worldToScreenPosition(position);
        img.draw(worldPosition.x, worldPosition.y);
    }
    
    public float getRadius(){
        return triggerRadius;
    }
    
    public Vector2f getTrigger(){
        return trigger;
    }
    
    public static void setWorld(World worldinstance){
        Specifictooltip.worldinstance = worldinstance; 
    }
}
