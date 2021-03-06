package de.fhtrier.gdw.ss2013.game.world.enemies;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.constants.EnemyConstants;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.bullets.EnemyBullet;
import de.fhtrier.gdw.ss2013.game.world.objects.FollowPath;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.physix.PhysixShape;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;

/**
 * Flying Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class FlyingEnemy extends AbstractEnemy implements EntityFilter {
    private Astronaut player;

    private ArrayList<Point> points;
    private float speed;
    private float rndX;
    private float rndY;
    private float interval;
    private boolean moveAround;
    private boolean pathEnabled;
    private boolean change;
    private Point currentPoint;
    private Point nextPoint;
    private int indexmod;
    private int index;
    private SafeProperties pathProperties;
    private float[][][] rangeField;
    private Vector2f startPosition;
    private Vector2f endPosition;
    Random rnd = new Random();
    private int oldIndexX;
    private int oldIndexY;
    private int newIndexX;
    private int newIndexY;
    private float newX;
    private float newY;
    float range;

    private Sound wingSound;
    private Sound shootSound;



    public FlyingEnemy(Animation animation) {
        super(animation);

        interval = 0f;
        speed = 40.0f;
        indexmod = 1;
        rndX = (float) Math.random();
        rndY = (float) Math.random();
        moveAround = false;
        pathEnabled = false;
        change = false;
        range = 300;
    }

    @Override
    protected void initialize() {
        super.initialize();

        player = World.getInstance().getAstronaut();
        wingSound = SoundLocator.loadSound("fluegelschlag");
        shootSound = SoundLocator.loadSound("alienschuss");

        if (getProperties() != null
                && getProperties().getBoolean("followPath", false)) {
            bindToPath();
        } else {
            rangeField = new float[3][3][2];
            initRangeField();
            startPosition = origin;
            oldIndexX = 1;
            oldIndexY = 1;
            for (;;) {
                newIndexX = rnd.nextInt(3);
                newIndexY = rnd.nextInt(3);
                if (newIndexX == oldIndexX && newIndexY == oldIndexY) {
                    newIndexX = rnd.nextInt(3);
                    newIndexY = rnd.nextInt(3);
                } else {
                    newX = rangeField[newIndexX][newIndexY][0];
                    newY = rangeField[newIndexX][newIndexY][1];
                    endPosition = new Vector2f((newX - range / 3) + (range / 3)
                            * (float) Math.random(), (newY - range / 3)
                            + (range / 3) * (float) Math.random());
                    break;
                }
            }
        }
        if (pathProperties != null) {
            speed = pathProperties.getFloat("speed", 20.0f);
            moveAround = pathProperties.getBoolean("moveAround", false);
            range = pathProperties.getFloat("range", 300);
        }
        if (points == null) {
            pathEnabled = false;
        } else {
            pathEnabled = true;
            index = getClosestPoint();
        }
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y).density(5000.0f)
                .friction(PhysixManager.FRICTION)
                .category(PhysixConst.ENEMY).mask(PhysixConst.MASK_ENEMY)
                .asBox(initialSize.x, initialSize.y);
        if (points != null) {
            getPhysicsObject().setPosition(points.get(index).x,
                    points.get(index).y);
        }
    }

    /**
     * Creates an enemy shot to attack the player
     * 
     * @param shootDirection
     *            The direction from the flying enemy towards the player
     */
    public void shoot(Astronaut player) {
        soundPlayer.playSoundAt(shootSound, this);
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

    public void bindToPath() {
        if (getProperties() != null) {
            String pathName = getProperties().getProperty("path");
            if (pathName != null) {
                FollowPath path = FollowPath.paths.get(pathName);
                if (path != null) {
                    pathProperties = path.getProperties();
                    points = path.getPoints();
                } else {
                    throw new NullPointerException(
                            "No path with this name found!" + getName());
                }
            } else {
                throw new NullPointerException("No path set!" + getName());
            }
        } else {
            throw new NullPointerException("No path option!" + getName());
        }
    }

    public void initRangeField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rangeField[i][j][0] = (origin.x - range / 2) + (i + 1)
                        * (range / 3);
                rangeField[i][j][1] = (origin.y + range / 2) - (j + 1)
                        * (range / 3);
            }
        }
    }

    public void move() {
        if (MainGame.currentState == 1) {
            soundPlayer.playSoundAt(wingSound, this);
        }

        float distToEnd = getPosition().sub(endPosition).length();
        float distToStart = getPosition().sub(startPosition).length();
        float margin = 30;
        float speed_mod;
        if (distToEnd > 1f) {
            if (distToEnd <= margin) {
                // speed_mod = Math.min((distToEnd / margin) + 0.1f, 1f);
                speed_mod = distToEnd / margin;
            } else if (distToStart <= margin && distToStart > 1f) {
                speed_mod = distToStart / margin;
            } else {
                speed_mod = 1f;
            }
            setVelocity(new Vector2f(endPosition.copy().sub(startPosition).x,
                    endPosition.copy().sub(startPosition).y).normalise().scale(
                    speed * speed_mod));
            change = false;
        } else if (!change) {
            newIndexX = rnd.nextInt(3);
            newIndexY = rnd.nextInt(3);
            if (newIndexX == oldIndexX && newIndexY == oldIndexY) {
                newIndexX = rnd.nextInt(3);
                newIndexY = rnd.nextInt(3);
            } else {
                newX = rangeField[newIndexX][newIndexY][0];
                newY = rangeField[newIndexX][newIndexY][1];
                startPosition = endPosition;
                endPosition = new Vector2f((newX - range / 3) + (range / 3)
                        * (float) Math.random(), (newY - range / 3)
                        + (range / 3) * (float) Math.random());
                change = true;
            }
        }
    }

    public int getClosestPoint() {
        float dist[] = new float[points.size() - 1];
        for (int i = 0; i < points.size() - 1; i++) {
            dist[i] = origin.distanceSquared(new Vector2f(points.get(i).x,
                    points.get(i).y));
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
    public void update(GameContainer container, int delta)
            throws SlickException {
        super.update(container, delta);
        interval += delta * Math.random();
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
        super.setPhysicsObject(physicsObject);
        this.physicsObject.setGravityScale(0);
        
        if (points != null) {
            getPhysicsObject().setPosition(points.get(index).x, points.get(index).y);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Entity other = getOtherEntity(contact);
        if (other instanceof Astronaut) {
            Astronaut astro = (Astronaut) other;
            Vector2f damageTakerPos = other.getPosition();
            Vector2f damageTakerDim = other.getPhysicsObject()
                    .getDimension();

            Vector2f damageDealerPos = this.getPosition();
            Vector2f damageDealerDim = this.getPhysicsObject()
                    .getDimension();
            
            boolean checkPlayerOnTopOfEnemy = (damageTakerPos.x + damageTakerDim.x > damageDealerPos.x
                    - damageDealerDim.x) //
                    && ((damageTakerPos.x - damageTakerDim.x < damageDealerPos.x
                            + damageDealerDim.x))
                    && (damageTakerPos.y + damageTakerDim.y < damageDealerPos.y);
            if (!checkPlayerOnTopOfEnemy) {

                astro.setOxygen(((Astronaut) other).getOxygen()
                        - this.getDamage());
                astro.gotBiten();
            }

        }
    }

    @Override
    public void endContact(Contact object) {
    }
}
