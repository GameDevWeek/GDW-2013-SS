package de.fhtrier.gdw.ss2013.game.world.enemies;

import java.awt.Point;
import java.util.ArrayList;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.constants.EnemyConstants;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.bullets.EnemyBullet;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.physix.PhysixShape;
import org.jbox2d.dynamics.BodyType;

/**
 * Flying Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class FlyingEnemy extends AbstractEnemy implements EntityFilter {
    private Astronaut player;
    private float interval;
    private ArrayList<Point> points;
    private float speed;
    private float rndX;
    private float rndY;
    private boolean moveAround;
    private boolean pathEnabled;
    private boolean change;
    private int index;
    private Point currentPoint;
    private Point nextPoint;
    private int indexmod;
    public static boolean burningWhip = false;

    public FlyingEnemy(Animation animation) {
    	super(animation);
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.player = World.getInstance().getAstronaut();
        this.interval = 0f;
        speed = 40.0f;
        indexmod = 1;
        rndX = (float)Math.random();
        rndY = (float)Math.random();
        moveAround = false;
        pathEnabled = false;
        change = false;
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.KINEMATIC, origin.x, origin.y)
                .density(PhysixManager.DENSITY).friction(PhysixManager.FRICTION)
                .asBox(initialSize.x, initialSize.y);
    }

    public void initLine(ArrayList<Point> points, SafeProperties properties) {
        if (points == null) {
            pathEnabled = false;
        } else {
            this.points = points;
            pathEnabled = true;
        }
        if (properties != null) {
            speed = properties.getFloat("speed", 20.0f);
            moveAround = properties.getBoolean("moveAround", false);
        }
        
        index = getClosestPoint();
        if(points != null) {
            getPhysicsObject().setPosition(points.get(index).x, points.get(index).y);
        }
    }
    
    public void reduceHealth(float dmg) {
        setHealth(getHealth() - dmg);
    }

    /**
     * Creates an enemy shot to attack the player
     * @param shootDirection The direction from the flying enemy towards the player
     */
    public void shoot(Astronaut player) {
    	Vector2f shootDirection = player.getPosition().sub(getPosition())
                .normalise().scale(PlayerConstants.BULLET_SPEED);

		EntityManager entityManager = World.getInstance().getEntityManager();

		// Create a meteorite entity
		EnemyBullet bullet = entityManager.createEntity(EnemyBullet.class);
		bullet.setOrigin(getPosition().x, getPosition().y);
		bullet.setShootDirection(shootDirection);
    }
    
    public boolean enterAttackZone(float value) {
        float distance = player.getPosition().sub(getPosition()).length();
        
        if (distance < value) {
            return true;
        }
        return false;
    }
    
    public void followPath() {
        currentPoint = points.get(index);
        nextPoint = points.get(index + indexmod);
        if (getPosition().distance(new Vector2f(nextPoint.x, nextPoint.y)) > speed / 2) {
            setVelocity(new Vector2f(nextPoint.x - currentPoint.x, nextPoint.y
                    - currentPoint.y).normalise().scale(speed));
        } else {
            index += indexmod;
            if (moveAround) {
                if (index == points.size() - 1) {
                    index = 0;
                }
            } else {
                if (index + indexmod < 0 || index + indexmod >= points.size()) {
                    indexmod *= -1;
                }
            }
        }
    }
    
    public void move() {
        float distToSpawn = getPosition().sub(origin).length();
        if (distToSpawn < 200) {
            setVelocity(new Vector2f(indexmod * rndX, indexmod * rndY).normalise().scale(speed));
            change = false;
        } else if (!change) {
            rndX = (float)Math.random();
            rndY = (float)Math.random();
            indexmod *= -1;
            setVelocity(origin.copy().sub(getPosition()).normalise().scale(speed));
            change = true;
        }
    }
    
    public int getClosestPoint() {
        float dist[] = new float[points.size() - 1];
        for (int i = 0; i < points.size() - 1; i++) {
            dist[i] = getPosition().distanceSquared(new Vector2f(points.get(i).x, points.get(i).y));
        }
        float closestDist = Float.MAX_VALUE;
        int closestPoint = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            if (dist[i] < closestDist) {
                closestDist = dist[i];
                closestPoint = i;
            }
        }
        return closestPoint;
    }
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    	super.update(container, delta);
    	interval += delta;
    	if (player == null || player.getOxygen() <= 0) {
    	    player = World.getInstance().getAstronaut();
    	}
    	
    	if (interval >= EnemyConstants.ENEMY_SHOOTING_INTERVAL) {
    	    if (enterAttackZone(EnemyConstants.ENEMY_SHOOTING_DISTANCE)) {
    	        shoot(player);
    	        interval %= EnemyConstants.ENEMY_SHOOTING_INTERVAL;
    	    }
    	}
    	
    	if (pathEnabled) {
    	    followPath();
    	} else {
    	    move();
    	}
    }
    
    @Override
    public void setPhysicsObject(PhysixShape physicsObject) {
        physicsObject.setOwner(this);
        this.physicsObject = physicsObject;
        this.physicsObject.setGravityScale(0);
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
