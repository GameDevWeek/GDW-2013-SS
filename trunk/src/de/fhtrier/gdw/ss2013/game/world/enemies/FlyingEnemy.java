package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.game.world.World;

/**
 * Flying Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class FlyingEnemy extends AbstractEnemy {

    private float flytime, bolttime;
    private float flyintelligence, boltintelligence;
    private float factor;
    private EntityManager m;
    private Astronaut p;
    private World w = World.getInstance();
    final static float DEBUG_ENTITY_HALFEXTEND = 5;

    public FlyingEnemy(Animation animation) {
    	super(animation);
        this.m = w.getEntityManager();
        this.p = w.getAstronaut();
        // FIXME: Player will never be set!
        // Don't use Velocity in the constructor!
        // if(flyintelligence > 0.5f) {
        // this.getVelocity().y = 0.0f;
        // } else {
        // this.getVelocity().x = 0.0f;
        // }
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();
        flyintelligence = boltintelligence = (float) Math.random();
        if (boltintelligence >= 0.67) {
            factor = 100;
        }
        else if (boltintelligence <= 0.33) {
            factor = 0;
        }
        else {
            factor = 50;
        }
    }

    public void reduceHealth(float dmg) {
        setHealth(getHealth() - dmg);
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
    	super.update(container, delta);
    	
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

    public void setReferences(EntityManager m, Astronaut p) {
        this.m = m;
        this.p = p;
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut) {
            ((Astronaut) other).setOxygen(((Astronaut) other).getOxygen()
                    - this.getDamage());
        }
    }

    @Override
    public void endContact(Contact object) {
    }
}
