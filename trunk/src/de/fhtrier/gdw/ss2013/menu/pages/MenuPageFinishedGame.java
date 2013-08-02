package de.fhtrier.gdw.ss2013.menu.pages;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.Label;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.MenuPageAction;

public class MenuPageFinishedGame extends MenuPage {

    Font font;
    
    int points = 4567;
    int killedEnemies = 53;
    int timeNeeded = 351;

    Label pointsLabel = null;
    Label enemyLabel = null;
    Label timeLabel = null;

    boolean aniPointsComplete = false;
    boolean aniEnemiesComplete = false;
    boolean aniTimeComplete = false;
    
    long startTimePoints = -1;
    long startTimeEnemy = -1;
    long startTimeTime = -1;

    double animationFactor = 400;

    float x = 100;
    float y = 300;
    float h;
    int xOffset;

    public MenuPageFinishedGame(final GameContainer container,
            final StateBasedGame _game, final MenuManager manager,
            final boolean ingame) throws SlickException {
        super(container, _game, manager, null, null, "root");

        font = AssetLoader.getInstance().getFont("jabjai_heavy");
        xOffset = font.getWidth("Enemies killed:") + 25;
        h = font.getLineHeight() * 1.2f;

        addCenteredTextField("Congratulations!", MenuManager.MENU_WIDTH / 2,
                font.getHeight("Congratulations!") / 2 + 50, font).color(Color.pink);
        
        addLeftAlignedButton("Exit", MenuManager.MENU_WIDTH-font.getWidth("Exit")-50, MenuManager.MENU_HEIGHT-font.getHeight("Exit")-50, font, new MenuPageAction(manager, new MenuPageGameOver(container, _game, manager, ingame)));
    }

    @Override
    public void render(Graphics g) {
        updatePage();
        super.render(g);
    }

    private void showPoints() {
        if (pointsLabel != null) {
            if (startTimePoints > 0) {
                if (!aniPointsComplete) {
                    long shownPoints = Math.round(points - Math.exp(-(System.currentTimeMillis()-startTimePoints)/animationFactor) * points);
                    pointsLabel.text(Long.toString(shownPoints));
                    if (shownPoints == points) {
                        aniPointsComplete = true;
                    }
                } else {
                    pointsLabel.text(Long.toString(points));
                }
            } else {
                startTimePoints = System.currentTimeMillis();
                pointsLabel.text("0");
            }
        } else {
            try {
                addLeftAlignedTextField("Score:", x, y + h * 3, font);
                pointsLabel = addLeftAlignedLabel("", x + xOffset, y + h * 3,
                        font);
            } catch (SlickException e) {
                System.err.println("Points Label could not be loaded.");
            }
        }
    }
    
    private void showEnemies() {
        if (enemyLabel != null) {
            if (startTimeEnemy > 0) {
                if (!aniEnemiesComplete) {
                    long shownEnemies = Math.round(killedEnemies - Math.exp(-(System.currentTimeMillis()-startTimeEnemy)/animationFactor) * killedEnemies);
                    enemyLabel.text(Long.toString(shownEnemies));
                    if (shownEnemies == killedEnemies) {
                        aniEnemiesComplete = true;
                    }
                } else {
                    enemyLabel.text(Long.toString(killedEnemies));
                }
            } else {
                startTimeEnemy = System.currentTimeMillis();
                enemyLabel.text("0");
            }
        } else {
            try {
                addLeftAlignedTextField("Enemies killed:", x, y + h * 1, font);
                enemyLabel = addLeftAlignedLabel("", x + xOffset, y + h * 1,
                        font);
            } catch (SlickException e) {
                System.err.println("Enemy Label could not be loaded.");
            }
        }
    }
    
    private void showTime() {
        if (timeLabel != null) {
            if (startTimeTime > 0) {
                if (!aniTimeComplete) {
                    long shownTime = Math.round(timeNeeded - Math.exp(-(System.currentTimeMillis()-startTimeTime)/animationFactor) * timeNeeded);
                    timeLabel.text(Long.toString(shownTime));
                    if (shownTime == timeNeeded) {
                        aniTimeComplete = true;
                    }
                } else {
                    timeLabel.text(Long.toString(timeNeeded));
                }
            } else {
                startTimeTime = System.currentTimeMillis();
                timeLabel.text("0");
            }
        } else {
            try {
                addLeftAlignedTextField("Time needed:", x, y + h * 2, font);
                timeLabel = addLeftAlignedLabel("", x + xOffset, y + h * 2,
                        font);
            } catch (SlickException e) {
                System.err.println("Time Label could not be loaded.");
            }
        }
    }

    public void updatePage() {
        showEnemies();
        if(aniEnemiesComplete) {
            showTime();
            if(aniTimeComplete) {
                showPoints();
            }
        }
    }

}
