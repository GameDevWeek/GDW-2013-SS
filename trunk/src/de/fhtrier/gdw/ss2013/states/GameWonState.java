package de.fhtrier.gdw.ss2013.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.gui.utils.CenteredText;
import de.fhtrier.gdw.ss2013.gui.RollingCounter;

public class GameWonState extends BasicGameState {

    private boolean RunUpdateAtLeastOneTime = false;
	private Image winImage;
	final RollingCounter rollingCounter = new RollingCounter();
	private AssetLoader asset;
	int stellen = 6;
	int counter = 0;
	int zehnerpotenz = 10;
	int score = 123;
	int timer = 0;
	boolean ready = false;
	Image digits;
	
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		winImage = AssetLoader.getInstance().getImage("ability1");
		digits = AssetLoader.getInstance().getImage("digits");
		
		//Init RollingCounter
        Image[] rollingCounterImage = new Image[10];
        
        
        int height = digits.getHeight()/10; 
        int width = digits.getWidth();
        for(int i=0;i<10;i++)
        {
            rollingCounterImage[i]=digits.getSubImage(0, i*height-1, width, height);
        }
                        
        rollingCounter.init(rollingCounterImage, 0, new Vector2f(200.0f,200.0f)); 
        
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//rollingCounter Render
		rollingCounter.render(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	    if(RunUpdateAtLeastOneTime)
	    {
	        //rollingCounter Update
	        rollingCounter.update(gc, sbg, delta);
	        rollingCounter.up(0.2f);
	    }
	    else
	    {
	        RunUpdateAtLeastOneTime = true;
	    }
	    
	       /* DO NOT DELETE! FOR FUTURE USE
         * 
         * 
        timer += delta;
        
        if (timer >= 100 && counter < score){
           
            System.out.println("zaehler[0].up");
            counter++;
            timer = 0;
            
            for (int j = 1; j <= stellen; j++){
                
                if (counter % zehnerpotenz == 0){
                            System.out.println("zaehler["+ j +"].up");
                }
               
                zehnerpotenz *= 10;
            }
            
            
        }
        zehnerpotenz = 10;
        initTime += delta;
        */
	}

	@Override
	public int getID() {
		return MainGame.WINSTATE;
	}

}
