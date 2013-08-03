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
import de.fhtrier.gdw.ss2013.gui.counter.AutonomousRollerCounter;
import de.fhtrier.gdw.ss2013.gui.counter.MechanicalCounter;
import de.fhtrier.gdw.ss2013.gui.counter.RollerCounter;
import de.fhtrier.gdw.ss2013.gui.counter.ScoreCounter;
import de.fhtrier.gdw.ss2013.gui.counter.WinScreenCounter;
import de.fhtrier.gdw.ss2013.gui.utils.CenteredText;

public class GameWonState extends BasicGameState {

    private boolean runUpdateAtLeastOneTime = false;
    
    AssetLoader asset;

    int timer = 0;
    int i = 0;
    float seed = 1;
	
	
    WinScreenCounter counter;
    
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
	    
	    i = 0;
	    
	    Image digits = AssetLoader.getInstance().getImage("digits");
	    Vector2f centerPosition = new Vector2f(gc.getWidth()/2,gc.getHeight()/2);

	    int score = 1337; //500 nur zum testen
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

}
