package de.fhtrier.gdw.ss2013.game.world.objects;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Moving Platform class
 * 
 * @author Kevin, Georg
 * 
 */
public class MovingPlatform extends Entity implements Interactable, EntityFilter {
    private ArrayList<Point> points;
    private AssetLoader asset = AssetLoader.getInstance();
    private Point nextPoint;
    private Point currentPoint;
    private int index;
    private boolean moveAround;
    private float speed;
    private boolean isActive;
    private int indexmod;

    public MovingPlatform() {
        img = asset.getImage("MovingPlatform");
        index = 0;
        speed = 80;
        setParticle(AssetLoader.getInstance().getParticle("plattform1"));
        isActive = true;
        moveAround = false;
        indexmod = 1;

    }

    public void initLine(ArrayList<Point> points, SafeProperties properties) {
        this.points = points;
        if (properties != null) {
            speed = properties.getFloat("speed", 20);
            isActive = properties.getBoolean("isActive", true);
            moveAround = properties.getBoolean("moveAround", false);
        }
        index = getClosestPoint();
        getPhysicsObject().setPosition(points.get(index).x, points.get(index).y);
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
    public void update(GameContainer container, int delta)
            throws SlickException {
        super.update(container, delta);
        if (isActive)
            move();
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
    public void setPhysicsObject(PhysixObject physicsObject) {
        physicsObject.setOwner(this);
        this.physicsObject = physicsObject;
    }

    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        return false;
    }
}
