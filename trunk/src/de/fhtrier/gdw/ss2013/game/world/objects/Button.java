package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;

/**
 * Switch class
 * 
 * @author Kevin, Georg
 * 
 */
public class Button extends Switch {
    private boolean collision, lastframecollision;

    public Button(Vector2f pos) {
        super(pos.copy());
        collision = lastframecollision = false;
    }

    public Button() {
        this(new Vector2f());
    }

    @Override
    public void onCollision(Entity e) {
        if (e instanceof Astronaut || e instanceof Box) {
            this.setActivated(true);
            collision = true;
        }
    }

    public void update(GameContainer container, int delta)
            throws SlickException {

        float dt = delta / 1000.f;
        // TODO clamp dt if dt > 1/60.f ?
        if (lastframecollision) {
            this.setActivated(true);
        } else {
            this.setActivated(false);
        }
        lastframecollision = collision;
        collision = false;
    }
}
