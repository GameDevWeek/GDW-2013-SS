package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.world.World;


public class Tooltip {
    
    private Image image;
    private Vector2f imagePosition;
    private Vector2f triggerPosition;
    private float triggerRadius;
    private static World worldinstance;
    
    public Tooltip(Vector2f position, Image img, Vector2f trigger, float triggerRadius){
        
        init(position, img, trigger, triggerRadius);
        
    }
    
    public void init(Vector2f imagePosition, Image image, Vector2f triggerPosition, float triggerRadius){
        
        this.image = image;
        this.imagePosition = imagePosition;
        this.triggerPosition = triggerPosition;
        this.triggerRadius = triggerRadius;    
    }

    public void update(){
        
    }
    
    public void render(){
        Vector2f worldPosition = worldinstance.worldToScreenPosition(imagePosition);
        image.draw(worldPosition.x, worldPosition.y);
    }
    
    public float getRadius(){
        return triggerRadius;
    }
    
    public Vector2f getTrigger(){
        return triggerPosition;
    }
    
    public static void setWorld(World worldinstance){
        Tooltip.worldinstance = worldinstance; 
    }
}
