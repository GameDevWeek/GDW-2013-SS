package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.*;
/**
 * Flying Enemy Class
 * @author Kevin, Georg
 *
 */
public class FlyingEnemy extends AbstractEnemy {

    private float health, change;
    final static float DEBUG_ENTITY_HALFEXTEND = 5;
    
    public FlyingEnemy(Vector2f pos, Vector2f velo, float dmg, float hp) {
        super(pos.copy(), velo.copy(), dmg);
        health = hp;
    }
    
    public FlyingEnemy() {
        this(new Vector2f(), new Vector2f(), 0, 0);
    }
    
    public FlyingEnemy(Vector2f pos) {
        this(pos.copy(), new Vector2f(2.5f,0.0f), 0, 0);
    }
    
    @Override
    public void onCollision(Entity e) {
        if(e instanceof Astronaut) {
            ((Astronaut)e).setOxygen(((Astronaut)e).getOxygen()-this.getDamage());
        }
    }
    
    public void reduceHealth(float dmg) {  
        health -= dmg;
    }
    public void shoot(Player player, EntityManager m) {
        Bullet b = (Bullet) m.createEntityAt(Bullet.class, this.position.copy());
        b.getVelocity().x = 5 * calcPlayerDirection(player).x;
        b.getVelocity().y = 5 * calcPlayerDirection(player).y;
        b.setDamage(this.getDamage());
    }
    
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        g.setColor(Color.blue);
        g.drawRect(position.x - DEBUG_ENTITY_HALFEXTEND, position.y
                - DEBUG_ENTITY_HALFEXTEND, DEBUG_ENTITY_HALFEXTEND * 2,
                DEBUG_ENTITY_HALFEXTEND * 2);   
        // g.drawString(this.hashCode(), position.x, position.y);
    }
    
    public void update(GameContainer container, int delta)
            throws SlickException {
        float dt = delta / 1000.f;
        change += delta;
        // TODO clamp dt if dt > 1/60.f ?
        this.getPosition().x += this.getVelocity().x;
        if (change >= 3000) {
            this.getVelocity().x = -this.getVelocity().x;
            change = change % 3000;
        }
    }
    
    private Vector2f calcPlayerDirection(Player player) {
        
        Vector2f direction = new Vector2f();
        
        direction.x = player.getPosition().x - this.position.x;
        direction.y = player.getPosition().y - this.position.y;
        
        direction.normalise();
        
        return direction;
    }
}
