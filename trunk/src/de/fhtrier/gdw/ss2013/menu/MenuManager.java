package de.fhtrier.gdw.ss2013.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.pages.MenuPageGamePause;
import de.fhtrier.gdw.ss2013.menu.pages.MenuPageRoot;



/**
 * Menu state
 * 
 * @author Lusito
 * @author Kevin Korte
 */
public class MenuManager {
	public static final int MENU_WIDTH = 1024;
	public static final int MENU_HEIGHT = 720;
    
    MenuPage rootPage;
	MenuPage currentPage;
	/** The key repeat interval */
	private static final int INITIAL_KEY_REPEAT_INTERVAL = 400;
	/** The key repeat interval */
	private static final int KEY_REPEAT_INTERVAL = 50;
	/** The last key pressed */
	private int lastKey = -1;
	/** The last character pressed */
	private char lastChar = 0;
	/** The time since last key repeat */
	private long repeatTimer;
	/** The input we're responding to */
	protected Input input;
	protected Image foregroundBorder;
	protected Type type;

	float xOffset = 0;
	float yOffset = 0;
	
	private Color screenDarken = new Color(0, 0, 0, 0.75f);
	private Color mainMenuBgColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);

	public enum Type {
		MAINMENU,
		INGAME
	};
	
	public MenuManager(final GameContainer container, final StateBasedGame game, Type type)
			throws SlickException {
		input = container.getInput();
		
		this.type = type;
		
		switch(type) {
		case MAINMENU:
			rootPage = currentPage = new MenuPageRoot(container, game, this, false);
			break;
		case INGAME:
			rootPage = currentPage = new MenuPageGamePause(container, game, this);
			break;
		}
		//foregroundBorder = new Image("res/menu/fg_border.png");
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		if (currentPage != null) {
			// g.clear();
			// g.setBackground(Color.black);

			if (this.type == Type.INGAME) {
			    g.setColor(screenDarken);
			} else {
			    g.setColor(this.mainMenuBgColor);
			}
			
			g.fillRect(0, 0, container.getWidth(), container.getHeight());
			
			//Image i = AssetLoader.getInstance().getImage("menu_ref_image");
			xOffset = (container.getWidth() - MenuManager.MENU_WIDTH) / 2.0f;
			yOffset = (container.getHeight() - MenuManager.MENU_HEIGHT) / 2.0f;
			
			g.pushTransform();
			g.translate(xOffset, yOffset);		
			g.setWorldClip(0, 0, MenuManager.MENU_WIDTH, MenuManager.MENU_HEIGHT);
			
			g.setColor(Color.red);
			g.drawRect(0, 0, MenuManager.MENU_WIDTH-1, MenuManager.MENU_HEIGHT-1);
			//g.setColor(Color.white);
            //g.fillRect(2, 2, MenuManager.MENU_WIDTH-2, MenuManager.MENU_HEIGHT-2);
            
			g.setColor(Color.white);
			currentPage.render(g);
			//foregroundBorder.draw();
			
			g.clearWorldClip();
			g.popTransform();
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (lastKey != -1 && currentPage != null) {
			if (input.isKeyDown(lastKey)) {
				if (repeatTimer < System.currentTimeMillis()) {
					repeatTimer = System.currentTimeMillis() + KEY_REPEAT_INTERVAL;
					currentPage.keyPressed(lastKey, lastChar, true);
				}
			} else {
				lastKey = -1;
			}
		}
	}

	public MenuPage getCurrentPage() {
		return currentPage;
	}

	public void setPage(MenuPage page) {
		if (page == null) {
//				Transition transitionOut = new FadeOutTransition(Color.black, 500);
//				Transition transitionIn = new FadeInTransition(Color.black, 500);
//				game.enterState(SlickTestGameState.GAMEPLAYSTATE, transitionOut, transitionIn);
		} else {
			page.activate();
		}
		currentPage = page;
	}
	
	public void activate() {
		setPage(rootPage);
	}

	public boolean keyReleased(int key, char c) {
		if(key == Input.KEY_ESCAPE && currentPage == rootPage)
			return false;

		if (currentPage != null)
			currentPage.keyReleased(key, c);
		return true;
	}

	public void keyPressed(int key, char c) {
		if (currentPage != null) {
			if (lastKey != key) {
				lastKey = key;
				repeatTimer = System.currentTimeMillis() + INITIAL_KEY_REPEAT_INTERVAL;
			} else {
				repeatTimer = System.currentTimeMillis() + KEY_REPEAT_INTERVAL;
			}
			lastChar = c;
			
			currentPage.keyPressed(key, c, false);
		}
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if (currentPage != null)
			oldx -= xOffset;
			oldy -= yOffset;
			newx -= xOffset;
			newy -= yOffset;
			currentPage.mouseMoved(oldx, oldy, newx, newy);
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if (currentPage != null)
			oldx -= xOffset;
			oldy -= yOffset;
			newx -= xOffset;
			newy -= yOffset;
			currentPage.mouseDragged(oldx, oldy, newx, newy);
	}

	public void mouseReleased(int button, int x, int y) {
		if (currentPage != null)
			x -= xOffset;
			y -= yOffset;
			currentPage.mouseReleased(button, x, y);
	}

	public void mousePressed(int button, int x, int y) {
		if (currentPage != null)
			x -= xOffset;
			y -= yOffset;
			currentPage.mousePressed(button, x, y);
	}

	public void mouseWheelMoved(int newValue) {
		if (currentPage != null)
			currentPage.mouseWheelMoved(newValue);
	}
}