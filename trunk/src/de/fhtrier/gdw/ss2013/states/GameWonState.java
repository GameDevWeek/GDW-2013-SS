package de.fhtrier.gdw.ss2013.states;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.gui.utils.CenteredText;

public class GameWonState extends BasicGameState {

	private Image winImage;
	private float x, y;

	private final int WAIT_TIME = 4000;
	private final int LOAD_TIME = 5000;
	private int time = 0;
	private int initTime = 0;
	private AssetLoader asset;
	private Font font;
	int stellen = 6;
	int counter = 0;
	int zehnerpotenz = 10;
	int score = 123;
	int timer = 0;
	boolean ready = false;
	
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		winImage = AssetLoader.getInstance().getImage("winning");
		x = gc.getWidth() / 2 - winImage.getWidth() / 2;
		y = gc.getHeight();
		asset = AssetLoader.getInstance();
		font = asset.getFont("verdana_24");
		
    
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		winImage.draw(x, y);
		
		CenteredText.draw(gc.getWidth()/2, gc.getHeight()/2 - font.getHeight("Congrats! Your Score:"), "Congrats! Your Score:", font);
		CenteredText.draw(gc.getWidth()/2, gc.getHeight()/2, String.valueOf(World.getScoreCounter().getScore()), font);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	
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
	    
		if (initTime > WAIT_TIME) {
			time += delta;
			if (y > gc.getHeight() - winImage.getHeight()) {
				float f = (time * 1f / LOAD_TIME);
				y = gc.getHeight() - (f * winImage.getHeight());
			}
		}
	}

	@Override
	public int getID() {
		return MainGame.WINSTATE;
	}

}
