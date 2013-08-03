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
import de.fhtrier.gdw.ss2013.gui.MechanicalCounter;
import de.fhtrier.gdw.ss2013.gui.RollerCounter;
import de.fhtrier.gdw.ss2013.gui.ScoreCounter;

public class GameWonState extends BasicGameState {

    private boolean RunUpdateAtLeastOneTime = false;
    
    AssetLoader asset;

    int timer = 0;
    int i = 0;
    int score = 500; //500 nur zum testen
    float seed = 1;
	
	
	ScoreCounter counter;
	
	

	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
	    
	    i = 0;
	    
	    asset = AssetLoader.getInstance();
	    
	    Image img = asset.getImage("digits");
	    
	    //score = World.getScoreCounter().getScore();
	    
	    
	    counter = new ScoreCounter(img, new Vector2f(gc.getWidth()/2-img.getWidth()/2, gc.getHeight()/2 - img.getHeight()/2), score, 4, 1f);
	    
	    counter.setDesiredValue(0);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	    counter.render(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	  
	  
	   timer += delta + (i*i);
	   
	   if (counter.speed < 3.0f)
	       counter.speed = 0.1f * i;
	   
	   counter.update(gc, sbg, delta);
	       
	   if (timer >= 200 && i <= score){
	       
	       counter.setDesiredValue(i);
	       timer = 0;
	       i++;
	    }
	    
	}

	@Override
	public int getID() {
		return MainGame.WINSTATE;
	}

}
