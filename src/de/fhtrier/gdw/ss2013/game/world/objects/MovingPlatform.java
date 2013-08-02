package de.fhtrier.gdw.ss2013.game.world.objects;

import java.awt.Point;
import java.io.PrintStream;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.physix.PhysixShape;
import org.jbox2d.dynamics.BodyType;

/**
 * Moving Platform class
 * 
 * @author Kevin, Georg
 * 
 */
public abstract class MovingPlatform extends Entity implements Interactable, EntityFilter {
    private ArrayList<Point> points;
    private Point nextPoint;
    private Point currentPoint;
    private SafeProperties pathProperties;
    private int index;
    private boolean moveAround;
    private float speed;
    private boolean isActive;
    private int indexmod;
    

    public MovingPlatform(Image img) {
        this.img = img;
        index = 0;
        speed = 20;
        setParticle(AssetLoader.getInstance().getParticle("plattform1"));
        isActive = true;
        moveAround = false;
        indexmod = 1;
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
                    throw new NullPointerException("No path with this name found!" + getName());
                }
            } else {
                throw new NullPointerException("No path set!" + getName());
            }
        } else {
            throw new NullPointerException("No path option!" + getName());
        }
    }
    
    @Override
    public void initialize() {
        super.initialize();
        bindToPath();
        if (pathProperties != null) {
            speed = pathProperties.getFloat("speed", 20.0f);
            isActive = pathProperties.getBoolean("isActive", true);
            moveAround = pathProperties.getBoolean("moveAround", false);
        }
        index = getClosestPoint();
        //getPhysicsObject().setPosition(points.get(index).x, points.get(index).y);
    }
    
    public int getClosestPoint() {
        float dist[] = new float[points.size() - 1];
        for (int i = 0; i < points.size() - 1; i++) {
            dist[i] = origin.distanceSquared(new Vector2f(points.get(i).x, points.get(i).y));
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
    public void initPhysics() {
//        System.out.println(origin);
//        System.out.println(points.get(index));
        createPhysics(BodyType.KINEMATIC, points.get(index).x, points.get(index).y)
                .density(PhysixManager.DENSITY).friction(PhysixManager.FRICTION)
                .asBox(initialSize.x, initialSize.y);
    }
    
    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        super.update(container, delta);
        if (isActive)
            move();
//        System.out.println(World.getInstance().getAstronaut().getPosition());
    }

    public void move() {
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

    @Override
    public void activate() {
        isActive = true;

    }

    @Override
    public void deactivate() {
        isActive = false;
        setVelocity(new Vector2f());
    }

    @Override
    public void setPhysicsObject(PhysixShape physicsObject) {
        physicsObject.setOwner(this);
        this.physicsObject = physicsObject;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
