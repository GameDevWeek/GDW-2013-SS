package de.fhtrier.gdw.ss2013.gui.counter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class WinScreenCounter {
    private int n; //number of digits the WinScreenCounter consists of.
    private AutonomousRollerCounter[] rollerCounter;
    private boolean moving; //indicates whether the RollerCounters of the WinScreenCounter are still moving.
    protected int value;
    
    public WinScreenCounter(Image digits, Vector2f position, int value, int numberOfDigits)
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
        
        rollerCounter = new AutonomousRollerCounter[n];
        for(int i=0;i<n;i++)
        {
            rollerCounter[i] = new AutonomousRollerCounter();
        }
       
        //make 10 seperate images out of the one big "digits" image.
        Image[] rollerCounterImage = new Image[10];
        int height = digits.getHeight()/10; 
        int width = digits.getWidth();
        
        // adept position, so that WinScreenCounters center is at the position passed to the init(...)
        position.x=position.x-n*width/2.0f;
        position.y=position.y-n*height/2.0f;
        
        for(int i=0;i<10;i++)
        {
            rollerCounterImage[i]=digits.getSubImage(0, i*height-1, width, height);
        }
        
        //initializing the RollerCounters
        int tmpValue = value;
        int desiredRollerCounterValue = 0;
        int startRollerCounterValue = 7; //some random Value 
        for(int i=0;i<n;i++)
        {
            //System.out.println("--------------\nrollerCounter["+i+"] ");
            desiredRollerCounterValue = tmpValue % 10;
            startRollerCounterValue = (startRollerCounterValue + 3)%10;  //this line juste creates a more or less random value for startRollerCounterValue
            //System.out.println("tmpValue : " + tmpValue);
            //System.out.println("\trollerCounterValue: "+rollerCounterValue);
            Vector2f rollerCounterPosition = new Vector2f(position.x + (n-i-1) * width,position.y);
            rollerCounter[i].init(rollerCounterImage, rollerCounterPosition, startRollerCounterValue, desiredRollerCounterValue, 10+i*10);
            
            
            tmpValue-=desiredRollerCounterValue;
            //System.out.println("tmpValue-=rollerCounterValue :" + tmpValue);
            tmpValue=tmpValue/10;
            //System.out.println("tmpValue=tmpValue/10 :" + tmpValue);            
        }
    }
    
    public void start()
    {
        for(int i=0;i<n;i++)
        {
            rollerCounter[i].start();
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

    int getValue()
    {
        return value;
    }
}