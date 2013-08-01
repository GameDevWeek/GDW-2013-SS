package de.fhtrier.gdw.ss2013.game.world.objects;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Moving Platform class
 * 
 * @author Kevin, Georg
 * 
 */
public class MovingPlatform extends Entity implements Interactable {
    private ArrayList<Point> line;
    private AssetLoader asset = AssetLoader.getInstance();
    private Point nextPoint;
    private Point currentPoint;
    private int index;
    private boolean moveAround;
    private float speed;
    private boolean isActive;

    public MovingPlatform() {
        img = asset.getImage("MovingPlatform");
        index = 0;
        speed = 80;
        setParticle(AssetLoader.getInstance().getParticle("pollen2"));
        isActive = true;
        moveAround = false;

    }

    public void initLine(ArrayList<Point> line, SafeProperties prop) {
        this.line = line;
        if (prop != null) {
            speed = prop.getFloat("speed", 20);
            isActive = prop.getBoolean("isActive", true);
            moveAround = prop.getBoolean("moveAround", false);
        }
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        super.update(container, delta);
        if (isActive)
            move();
    }

    int indexmod = 1;

    public void move() {
        currentPoint = line.get(index);
        nextPoint = line.get(index + indexmod);
        if (getPosition().distance(new Vector2f(nextPoint.x, nextPoint.y)) > speed / 2) {
            setVelocity(new Vector2f(nextPoint.x - currentPoint.x, nextPoint.y
                    - currentPoint.y).normalise().scale(speed));
        } else {
            index += indexmod;
            if (moveAround) {
                if (index == line.size() - 1) {
                    index = 0;
                }
            } else {
                if (index + indexmod < 0 || index + indexmod >= line.size()) {
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
}
