package de.fhtrier.gdw.ss2013.gui.counter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class RollerCounter {
    
    private Image[] image;
    private int value; //the value the RollerCounter displays or will display after finishing it's animation.
    private Vector2f position;
    
    private Vector2f startDrawingPosition;
    private Vector2f currentDrawingPosition;
    private boolean moving;
    private boolean movingUp; //true wenn es aufwärtes, false wennn es abwärts Zählt. 
    private float speed;
    private float sizeOfWindow;

    public RollerCounter()
    {
        
    }
    
    public RollerCounter(Image[] image, Vector2f position, int value)
    {
        init(image, position, value); 
    }
    
    public void init(Image[] image, Vector2f position, int value)
    {
        /*to-do
         * throw error if  value>0 or value<0         *
         */
       // sizeOfWindow = 0.0f;
        this.image = image;
        this.value = value;
        
        this.position = position;
        startDrawingPosition = new Vector2f();
        startDrawingPosition.x = position.x;
        startDrawingPosition.y = position.y - 2.0f*image[0].getHeight() ;
        currentDrawingPosition = startDrawingPosition.copy();
        speed = 0.0f;
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
        if (moving && movingUp)
        {
            currentDrawingPosition.y -= delta*speed;
            if (currentDrawingPosition.y < startDrawingPosition.y)
            {
                currentDrawingPosition.y=startDrawingPosition.y;
                moving = false;
            }
        }
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        g.setClip((int)position.x, (int)position.y, image[0].getWidth(), (1)*image[0].getHeight());
        draw5er(value, currentDrawingPosition);
        g.setClip(0, 0, container.getWidth(), container.getHeight());
    }
    
    private void draw5er(int counterReading, Vector2f position)
    {
        int temp = ( (counterReading -2+10) % 10); //der Wert auf der Rolle vor counterReading
        float yOffset = image[0].getHeight();
        for(int i=0;i<5;i++)
        {
            int image_index = (temp+i)%10;
            image[image_index].draw(position.x,position.y+i*yOffset);          
        }        
    }
    public boolean up(float speed)
    //gibt false zurück falls Zähler noch in Animation ist. Gibt true zurück falls nicht.
    {
        if (moving)
        {
            return false;
        }
        else //if not moving
        {
            moving = true;
            movingUp = true;
            currentDrawingPosition.x = startDrawingPosition.x;
            currentDrawingPosition.y = startDrawingPosition.y +image[0].getHeight();
            value = (value + 1) % 10;
            this.speed=speed;
            return true;
        }
    }
    
    public void down() //optional. Funktion ist eignetlich nicht nötig.
    {
        
    }
    
    public void setValue(int value)
    {
        this.value=value;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public boolean isMoving()
    {
        return moving;
    }
  }
