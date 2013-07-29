package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Astronaut;
import de.fhtrier.gdw.ss2013.game.Entity;

/**
 * Meteroid class
 * 
 * @author Kevin, Georg
 * 
 */
public class Meteroid extends AbstractEnemy {

    private float health;
    final static float DEBUG_ENTITY_HALFEXTEND = 5;

    public Meteroid(Vector2f pos, Vector2f velo, float dmg, float hp) {
        super(pos.copy(), velo.copy(), dmg);
        health = hp;
    }

    public Meteroid() {
        this(new Vector2f(), new Vector2f((float) (Math.random() * 2.5), 5), 0,
                0);
    }

    public Meteroid(Vector2f pos) {
        this(pos.copy(), new Vector2f((float) (Math.random() * 2.5), 5), 0, 0);
    }

    @Override
    public void onCollision(Entity e) {
        if (e instanceof Astronaut) {
            ((Astronaut) e).setOxygen(((Astronaut) e).getOxygen()
                    - this.getDamage());
        }
    }

    public void reduceHealth(float dmg) {
        health -= dmg;
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        g.setColor(Color.magenta);
        g.drawRect(position.x - DEBUG_ENTITY_HALFEXTEND, position.y
                - DEBUG_ENTITY_HALFEXTEND, DEBUG_ENTITY_HALFEXTEND * 2,
                DEBUG_ENTITY_HALFEXTEND * 2);
        // g.drawString(this.hashCode(), position.x, position.y);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
        float dt = delta / 1000.f;
        // TODO clamp dt if dt > 1/60.f ?
        this.getPosition().x += this.getVelocity().x;
        this.getPosition().y += this.getVelocity().y;
    }
}
