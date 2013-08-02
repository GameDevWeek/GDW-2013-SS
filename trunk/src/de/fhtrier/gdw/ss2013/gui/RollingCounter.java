package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class RollingCounter {
    
    private Image[] image;
    private int counterReading;
    private Vector2f position;
    
    private Vector2f startDrawingPosition;
    private Vector2f currentDrawingPosition;
    private boolean moving;
    private boolean movingUp; //true wenn es aufwärtes, false wennn es abwärts Zählt. 
    private float speed;

    public void init(Image[] image, int counterReading, Vector2f position)
    {
        this.image = image;
        this.counterReading = counterReading;
        
        this.position = position;
        startDrawingPosition = new Vector2f();
        startDrawingPosition.x = position.x;
        startDrawingPosition.y = position.y - 1.5f*image[0].getHeight() ;
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
        g.setClip((int)position.x, (int)position.y, image[0].getWidth(), 2*image[0].getHeight());
        draw5er(counterReading, currentDrawingPosition);
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
            counterReading = (counterReading + 1) % 10;
            this.speed=speed;
            return true;
        }
    }
    
    public void down()
    {
        
    }
  }
