package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Bullet extends AbstractEnemy {

    final static float DEBUG_ENTITY_HALFEXTEND = 5;
    
    public Bullet(Vector2f pos, Vector2f velo, float dmg) {
        super(pos.copy(), velo.copy(), dmg);
    }
    public Bullet() {
        this(new Vector2f(), new Vector2f(), 0);
    }
    public Bullet(Vector2f pos) {
        this(pos.copy(), new Vector2f(), 0);
    }
    @Override
    public void onCollide() {
        // TODO Auto-generated method stub

    }
    
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        g.setColor(Color.red);
        g.drawRect(position.x - DEBUG_ENTITY_HALFEXTEND, position.y
                - DEBUG_ENTITY_HALFEXTEND, DEBUG_ENTITY_HALFEXTEND * 2,
                DEBUG_ENTITY_HALFEXTEND * 2);

        // g.drawString(this.hashCode(), position.x, position.y);
    }
    
    public void update(GameContainer container, int delta)
            throws SlickException {

        float dt = delta / 1000.f;
        // TODO clamp dt if dt > 1/60.f ?
        this.position.x += this.getVelocity().x;
        this.position.y += this.getVelocity().y;

    }
}
