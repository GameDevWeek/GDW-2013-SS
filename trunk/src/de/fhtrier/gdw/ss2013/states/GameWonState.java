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
import de.fhtrier.gdw.ss2013.gui.counter.WinScreenCounter;

public class GameWonState extends BasicGameState {

    private boolean runUpdateAtLeastOneTime = false;
    WinScreenCounter counter;
    
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {	    
	    Image digits = AssetLoader.getInstance().getImage("digits");
	    Vector2f centerPosition = new Vector2f(gc.getWidth()/2,gc.getHeight()/2);
	    //centerPosition = new Vector2f(0.0f,0.0f); //this line is DEV only

	    int score = World.getScoreCounter().getScore();
	    score = 1337; //this line is DEV only
        
	    counter = new WinScreenCounter(digits, centerPosition,score, 4);
        counter.start();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	    counter.render(gc, sbg, g);
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
