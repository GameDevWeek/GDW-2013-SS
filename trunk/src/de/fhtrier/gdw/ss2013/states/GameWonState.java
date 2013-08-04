package de.fhtrier.gdw.ss2013.states;

import java.util.ArrayList;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.ScoreInfo;
import de.fhtrier.gdw.ss2013.game.score.HighscoreManager;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.gui.counter.WinScreenCounter;
import de.fhtrier.gdw.ss2013.gui.utils.CenteredText;

public class GameWonState extends BasicGameState {

    private boolean runUpdateAtLeastOneTime = false;
    
    Font font;
    ScoreInfo scoreInfo;
    int rank;
    int score;
    Image digits;
    Vector2f centerPosition;	
    WinScreenCounter counter;
    String winMessage;
    
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
	    winMessage = "Congrats!\nYour Score:";
	    
	    digits = AssetLoader.getInstance().getImage("digits");
	    font = AssetLoader.getInstance().getFont("jabjai_heavy");
	     
	    	    //centerPosition = new Vector2f(0.0f,0.0f); //this line is DEV only
	    centerPosition = new Vector2f(gc.getWidth()/2,gc.getHeight()/2 + font.getHeight(winMessage)*2);
	    counter = new WinScreenCounter(digits, centerPosition,score, 4);               
	    

        
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	    score = World.getScoreCounter().getScore();
	    
	    centerPosition = new Vector2f(container.getWidth()/2,container.getHeight()/2 + font.getHeight(winMessage)*2);
        counter = new WinScreenCounter(digits, centerPosition,score, 4);
       
        counter.start();
        
        rank = HighscoreManager.getHighscoreRank(World.getInstance().getLevelName(), score); //rank ist immer zwischen 1 und 10
        scoreInfo = new ScoreInfo();
        scoreInfo.score = score;
        scoreInfo.astronautName = World.getInstance().getAstronaut().getName();
        scoreInfo.alienName = World.getInstance().getAstronaut().getName();
        
        //DEV
        System.out.print("alienName in alien\t: " + scoreInfo.alienName);
        System.out.print("astronautName in astronaut\t: " + scoreInfo.alienName);
                
        System.out.print("alienName in scoreInfo\t: " + scoreInfo.alienName);
        System.out.print("astronautName in scoreInfo\t: " + scoreInfo.alienName);
        //DEV
        
        if (World.getInstance().getLevelName() != null){
            HighscoreManager.setHighscore(World.getInstance().getLevelName(), scoreInfo);
        }	    
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	    
	    CenteredText.draw(gc.getWidth()/2, gc.getHeight()/2 - (font.getHeight(winMessage) * 2), winMessage, font);
	    
	    counter.render(gc, sbg, g);
	    
	    if (rank != 0){
	                
	            CenteredText.draw(gc.getWidth()/2, gc.getHeight()/2 + 300, "Your Highscore is now in the Top Ten!", font);
	        
	    }
	    
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	  
	  if (runUpdateAtLeastOneTime)
	  {    
	      counter.update(gc, sbg, delta);
	  }
	  else
	  {
	      runUpdateAtLeastOneTime = true;
	  }
	  
	  
	  
	}

	@Override
	public int getID() {
		return MainGame.WINSTATE;
	}

    @Override
	public void keyReleased(int key, char c) {
        MainGame.changeState(MainGame.MAINMENUSTATE);
    }
}
