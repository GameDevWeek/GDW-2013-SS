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
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Moving Platform class
 * 
 * @author Kevin, Georg
 * 
 */
public class MovingPlatform extends Entity {
    private ArrayList<Point> line;
    private AssetLoader asset = AssetLoader.getInstance();
    private Point nextPoint;
    private Point currentPoint;
    private int index;
    private boolean change;
    private float speed;
    
    public MovingPlatform() {
        img = asset.getImage("MovingPlatform");
        index = 0;
        change = false;
        speed = 20;
    }
    
    public void initLine(ArrayList<Point> line, SafeProperties prop) {
        this.line = line;
        if (prop != null) {
            speed = prop.getFloat("speed", 20);
        }
    }
    
    @Override
    public void update(GameContainer container, int delta)
                throws SlickException {
        move();
    }
    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        if (img != null) {
            g.drawImage(img, getPosition().x-(img.getWidth()/2), getPosition().y-(img.getHeight()/2));
        }
    }
    
    public void move() {
        currentPoint = line.get(index);
        if (!change) {
            if (index < line.size() - 1) {
                if (index + 1 < line.size()) {
                    nextPoint = line.get(index + 1);
                }
                if (getPosition().distance(new Vector2f(nextPoint.x, nextPoint.y)) > speed) {
                    setVelocity(new Vector2f(nextPoint.x - currentPoint.x, nextPoint.y - currentPoint.y).normalise().scale(speed));
                }
                else {
                    if (index < line.size() - 1) {
                        ++index;
                    }
                }
            }
            else {
                change = true;
            }
        }
        else {
            if (index > 0) {
                if (index - 1 >= 0) {
                    nextPoint = line.get(index - 1);
                }
                if (getPosition().distance(new Vector2f(nextPoint.x, nextPoint.y)) > speed) {
                    setVelocity(new Vector2f(nextPoint.x - currentPoint.x, nextPoint.y - currentPoint.y).normalise().scale(speed));
                }
                else {
                    if (index > 0) {
                        --index;
                    }
                }
            }
            else {
                change = false;
            }
        }
    }
    
    @Override
    public void setPhysicsObject(PhysixObject physicsObject) {
        physicsObject.setOwner(this);
        this.physicsObject = physicsObject;
    }
}
