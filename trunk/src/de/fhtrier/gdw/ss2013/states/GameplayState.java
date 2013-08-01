package de.fhtrier.gdw.ss2013.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.cheats.Cheats;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.gui.HUD;
import de.fhtrier.gdw.ss2013.input.InputManager;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;

/**
 * Gameplay state
 */
public class GameplayState extends BasicGameState {

    private World world;
    private Cheats cheats;
    private HUD hud;
    private InputManager inputManager;
    private GameContainer container;
    static boolean menuOpened;
    MenuManager menuManager;
    private Music music;

	protected long storedDelta;
	protected long maximumLogicInterval = 16;
    protected long minimumLogicInterval = 1;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        this.container = container;
        InputManager.init(container);
        inputManager = InputManager.getInstance();
        world = new World(container, game);
        cheats = new Cheats(world);
        hud = new HUD(container, world);

        menuManager = new MenuManager(container, game, MenuManager.Type.INGAME);
        music = AssetLoader.getInstance().getMusic("gameplay");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.setBackground(Color.black);
        g.setColor(Color.white);

        world.render(container, g);
        hud.render(container, game, g);

        if(menuOpened)
			menuManager.render(container, game, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        storedDelta += delta;

        if (storedDelta >= minimumLogicInterval) {
            long cycles = (long)Math.floor(storedDelta / (double)maximumLogicInterval);
            for (int i=0;i<cycles;i++) {
                update2(container, game, (int) maximumLogicInterval);
            }

            storedDelta -= (cycles * maximumLogicInterval);
        }
    }
    public void update2(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        ((MainGame) game).checkFullscreenToggle();
        world.getAstronaut().preInput();
        if(!menuOpened)
            inputManager.update(delta);
        // world.getPhysicsManager().update(container, delta);
        world.update(container, delta);
        hud.update(container, game, delta);
        cheats.update(container, game, delta);
    
        if(menuOpened)
			menuManager.update(container, game, delta);
    }

    @Override
    public int getID() {
        return MainGame.GAMEPLAYSTATE;
    }

    @Override
	public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_F1:
                container.setMouseGrabbed(!container.isMouseGrabbed());
                break;
            case Input.KEY_F2:
                world.debugDraw = !world.debugDraw;
                break;
            case Input.KEY_F3:
                inputManager.printControllerInfo();
                break;
            case Input.KEY_F4:
                if(music.playing())
                    music.stop();
                else
                    music.play();
                break;
            default:
                if(menuOpened) {
                    menuOpened = menuManager.keyReleased(key, c);
                    if(!menuOpened)
                        container.setMouseGrabbed(true);
                } else if(key == Input.KEY_ESCAPE) {
                    menuOpened = true;
                    container.setMouseGrabbed(false);
                } else {
                    cheats.addKey(c);
                }
                break;
        }
	}

    @Override
	public void keyPressed(int key, char c) {
		if(menuOpened)
			menuManager.keyPressed(key, c);
	}

    @Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if(menuOpened)
			menuManager.mouseMoved(oldx, oldy, newx, newy);
	}

    @Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(menuOpened)
			menuManager.mouseDragged(oldx, oldy, newx, newy);
	}

    @Override
	public void mouseReleased(int button, int x, int y) {
		if(menuOpened)
			menuManager.mouseReleased(button, x, y);
	}

    @Override
	public void mousePressed(int button, int x, int y) {
		if(menuOpened)
			menuManager.mousePressed(button, x, y);
	}
	
    @Override
	public void mouseWheelMoved(int newValue) {
		if(menuOpened)
			menuManager.mouseWheelMoved(newValue);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		menuOpened = false;
		menuManager.activate();
		if (music != null)
			music.loop(1f, 1f);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		menuOpened = false;
		menuManager.activate();
		if (music != null)
			music.stop();
	}
	
	public static void hideMenu() {
		menuOpened = false;
	}
}
