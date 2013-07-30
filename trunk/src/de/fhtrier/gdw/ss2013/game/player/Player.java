/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;

/**
 * Player class
 */
public class Player extends Entity {

    Animation bewegungs_ani;
    private Animation animation;
    private String zustand = "animtest";
    private Vector2f velocity;
    AssetLoader assetloader = AssetLoader.getInstance();

    public Player(Vector2f position) {
        super(position);

        velocity = new Vector2f();
        // animation von assetloader beziehen

        bewegungs_ani = assetloader.getAnimation(zustand);

    }

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        /*
         * super.render(container, g); g.setColor(Color.green);
         * g.setLineWidth(2); g.drawRect(position.x - 5, position.y - 5, 10,
         * 10);
         */
        bewegungs_ani.draw(position.x - bewegungs_ani.getWidth() / 2,
                position.y - bewegungs_ani.getHeight() / 2);
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    }

    /*
     * public enum Bewegung{ forward, backward, jump, still, sprint; }
     */

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public String getZustand() {
        return zustand;
    }

    public void setZustand(String zustand) {
        this.zustand = zustand;
    }

    /*
     * public void ani() { switch(zustand) { case "forward": for_ani.draw();
     * break; case "backward": back_ani.draw(); break; case "jump":
     * jump_ani.draw(); break; default: still_ani.draw(); break;
     * 
     * } }
     */

}
