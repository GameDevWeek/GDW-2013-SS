package de.fhtrier.gdw.ss2013.game.world.objects;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
    private boolean change;
    private float speed;
    private boolean isActiv;

    public MovingPlatform() {
        img = asset.getImage("MovingPlatform");
        index = 0;
        change = false;
        speed = 20;
        setParticle(AssetLoader.getInstance().getParticle("pollen1"));
        isActiv = true;
       
    }

    public void initLine(ArrayList<Point> line, SafeProperties prop) {
        this.line = line;
        if (prop != null) {
            speed = prop.getFloat("speed", 20);
            isActiv = prop.getBoolean("isActiv", true);
        }
    }

    @Override
    public void update(GameContainer container, int delta)
                throws SlickException {
        super.update(container, delta);
        System.out.println(this.particle);
        if (isActiv)
        move();
        //System.out.println(getPosition());
        
    }


    public void move() {
        currentPoint = line.get(index);
        if (!change) {
            if (index < line.size() - 1) {
                if (index + 1 < line.size()) {
                    nextPoint = line.get(index + 1);
                }
                if (getPosition().distance(
                        new Vector2f(nextPoint.x, nextPoint.y)) > speed) {
                    setVelocity(new Vector2f(nextPoint.x - currentPoint.x,
                            nextPoint.y - currentPoint.y).normalise().scale(
                            speed));
                } else {
                    if (index < line.size() - 1) {
                        ++index;
                    }
                }
            } else {
                change = true;
            }
        } else {
            if (index > 0) {
                if (index - 1 >= 0) {
                    nextPoint = line.get(index - 1);
                }
                if (getPosition().distance(
                        new Vector2f(nextPoint.x, nextPoint.y)) > speed) {
                    setVelocity(new Vector2f(nextPoint.x - currentPoint.x,
                            nextPoint.y - currentPoint.y).normalise().scale(
                            speed));
                } else {
                    if (index > 0) {
                        --index;
                    }
                }
            } else {
                change = false;
            }
        }
    }

    @Override
    public void activate() {
        isActiv = true;

    }

    @Override
    public void deactivate() {
        isActiv = false;
    }

    @Override
    public void setPhysicsObject(PhysixObject physicsObject) {
        physicsObject.setOwner(this);
        this.physicsObject = physicsObject;
    }
}
