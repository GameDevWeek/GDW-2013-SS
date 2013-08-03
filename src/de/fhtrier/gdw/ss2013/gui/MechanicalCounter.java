package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class MechanicalCounter {
    private int n; //number of digits the MechanicalCounter consists of.
    private RollerCounter[] rollerCounter;
    private boolean moving; //indicates whether the RollerCounters of the MechanicalCounter are still moving.
    protected int value;
    
    public MechanicalCounter(Image digits, Vector2f position, int value, int numberOfDigits)
    {        
        init(digits, position, value, numberOfDigits);
    }
    
    private void init(Image digits, Vector2f position, int value, int numberOfDigits)
    {
        /*to-do
         * throw error if value<0 or value>((10^n)-1)
         * 
         */
        moving = false;
        n = numberOfDigits;
        this.value = value;
        
        rollerCounter = new RollerCounter[n];
        for(int i=0;i<n;i++)
        {
            rollerCounter[i] = new RollerCounter();
        }
       
        //make 10 seperate images out of the one big "digits" image.
        Image[] rollerCounterImage = new Image[10];
        int height = digits.getHeight()/10; 
        int width = digits.getWidth();
        for(int i=0;i<10;i++)
        {
            rollerCounterImage[i]=digits.getSubImage(0, i*height-1, width, height);
        }
        
        //initializing the RollerCounters
        int tmpValue = value;
        int rollerCounterValue = 0;
        
        for(int i=0;i<n;i++)
        {
            //System.out.println("--------------\nrollerCounter["+i+"] ");
            rollerCounterValue = tmpValue % 10;
            //System.out.println("tmpValue : " + tmpValue);
            //System.out.println("\trollerCounterValue: "+rollerCounterValue);
            Vector2f rollerCounterPosition = new Vector2f(position.x + (n-i-1) * width,position.y);
            rollerCounter[i].init(rollerCounterImage, rollerCounterPosition, rollerCounterValue);
            
            tmpValue-=rollerCounterValue;
            //System.out.println("tmpValue-=rollerCounterValue :" + tmpValue);
            tmpValue=tmpValue/10;
            //System.out.println("tmpValue=tmpValue/10 :" + tmpValue);            
        }
    }
    
    public void setValue(int toSetTo)
    {
        this.value = toSetTo;
    }
    
    public void setDisplayTo(int toSetTo)
    {
        int tmpValue = toSetTo;
        int rollerCounterValue = 0;
        
        for(int i=0;i<n;i++)
        {
            rollerCounterValue = tmpValue % 10;
            rollerCounter[i].setValue(rollerCounterValue);
            tmpValue-=rollerCounterValue;
            tmpValue=tmpValue/10;
        }
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta)
    {     
        for(int i=0;i<n;i++)
        {
            rollerCounter[i].update(container, game, delta);
        }
                
        if(! rollerCounter[0].isMoving())
        {
            moving=false;
        }
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        for(int i=0;i<n;i++)
        {
            rollerCounter[i].render(container, game, g);            
        }
    }
    
    public boolean up(float speed)
    {
        if (moving)
        {
            return false;
        }
        
        else //if not moving
        {
            moving = true;
            value++;
           // movingUp = true;
            boolean countUpNext = true;
            for(int i=0;i<n;i++)
            {
                if(countUpNext)
                {
                    if(rollerCounter[i].getValue()==9)
                    {          
                        countUpNext = true;
                    }
                    else 
                    {
                        countUpNext = false;
                    }
                    rollerCounter[i].up(speed);
                }
            }
            return true;
        }
     }
    
    int getValue()
    {
        return value;
    }
}







//gibt false zurück falls Zähler noch in Animation ist. Gibt true zurück falls nicht.
/* if (moving)
 {
     return false;
 }
 else //if not moving
 {
     moving = true;
     movingUp = true;
     this.speed=speed;
     return true;
 }*/
