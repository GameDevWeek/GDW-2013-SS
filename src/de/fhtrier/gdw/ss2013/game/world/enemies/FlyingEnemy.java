package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physics.ICollidable;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Flying Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public class FlyingEnemy extends AbstractEnemy implements ICollidable {

    private float health, flytime, bolttime;
    private float flyintelligence, boltintelligence;
    private float factor;
    private EntityManager m;
    private Astronaut p;
    private World w = World.getInstance();
    final static float DEBUG_ENTITY_HALFEXTEND = 5;
    private String rechts = "animtest", links = "animtest", current = "animtest";

    public FlyingEnemy() {
        this.health = 100f; // FIXME: dummy value!
        this.m = w.getEntityManager();
        this.p = w.getAstronaut();
        // FIXME: Player will never be set!
        flyintelligence = boltintelligence = (float) Math.random();
        // Don't use Velocity in the constructor!
        // if(flyintelligence > 0.5f) {
        // this.getVelocity().y = 0.0f;
        // } else {
        // this.getVelocity().x = 0.0f;
        // }
        if (boltintelligence >= 0.67) {
            factor = 100;
        }
        else if (boltintelligence <= 0.33) {
            factor = 0;
        }
        else {
            factor = 50;
        }
        setLeft_animation(links);
        setRight_animation(rechts);
        setCurrent(current);
    }

    public void setHealth(float hp) {
        health = hp;
    }

    @Override
    public void onCollision(Entity e) {
        if (e instanceof Astronaut) {
            ((Astronaut) e).setOxygen(((Astronaut) e).getOxygen() - this.getDamage());
        }
    }

    public void reduceHealth(float dmg) {
        health -= dmg;
    }

    public void shoot(Player player, EntityManager m) {
        // EnemyBullet b = (EnemyBullet) m.createEntity(EnemyBullet.class);
        // childPhysics = this.getPosition();
        // b.setPhysicsObject(childPhysics);
        // b.getVelocity().x = 5 * calcPlayerDirection(player).x;
        // b.getVelocity().y = 5 * calcPlayerDirection(player).y;
        // b.setDamage(this.getDamage());
        // b.setReferences(m);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        // g.setColor(Color.blue);
        // g.drawRect(position.x - DEBUG_ENTITY_HALFEXTEND, position.y
        // - DEBUG_ENTITY_HALFEXTEND, DEBUG_ENTITY_HALFEXTEND * 2,
        // DEBUG_ENTITY_HALFEXTEND * 2);
        super.render(container, g);

        // g.drawString(this.hashCode(), position.x, position.y);
    }

    public void update(GameContainer container, int delta) throws SlickException {
        // float dt = delta / 1000.f;
        flytime += delta;
        bolttime += delta;
        // TODO clamp dt if dt > 1/60.f ?
        this.getPosition().x += this.getVelocity().x;
        this.getPosition().y += this.getVelocity().y;
        if (flytime >= 3000) {
            this.getVelocity().x = -this.getVelocity().x;
            this.getVelocity().y = -this.getVelocity().y;
            flytime = flytime % 3000;
        }
        if (bolttime >= 1000 && calcPlayerDistance(p) < 500) {
            this.shoot(p, m);
            bolttime = bolttime % 1000;
        }
        if (health <= 0) {
            m.removeEntity(this);
        }
    }

    private Vector2f calcPlayerDirection(Player player) {
        Vector2f direction = new Vector2f();
        direction = calcPlayerPosition(player);
        direction.normalise();
        return direction;
    }

    private float calcPlayerDistance(Player player) {
        Vector2f direction = new Vector2f();
        direction = calcPlayerPosition(player);
        if (direction == null) {
            return Float.MAX_VALUE;
        }
        return (float) Math.sqrt((direction.x * direction.x) + (direction.y * direction.y));
    }

    private Vector2f calcPlayerPosition(Player player) {
        Vector2f direction = new Vector2f();
        Vector2f position = getPosition();
        if (player != null && player.getPosition() != null) {
            direction.x = player.getPosition().x - position.x + ((float) Math.random() * factor);
            direction.y = player.getPosition().y - position.y;
            return direction;
        }
        return null;
    }

    @Override
    public Fixture getFixture() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setReferences(EntityManager m, Astronaut p) {
        this.m = m;
        this.p = p;
    }

    @Override
    public void beginContact(PhysixObject object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endContact(PhysixObject object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void beginContact(Contact object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endContact(Contact object) {
        // TODO Auto-generated method stub
        
    }
}
