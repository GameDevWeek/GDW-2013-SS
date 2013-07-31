package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class PhysixBoxPlayer extends PhysixBox {
    
    protected boolean isGrounded = false;
    
    
    public PhysixBoxPlayer(PhysixManager physicsManager, float x, float y, float width, float height) {
        super(physicsManager, x, y, width, height, BodyType.DYNAMIC, 1, 0.5f, false);
        
        float halfWidth = PhysixUtil.toBox2D(width) * 0.5f;
        float halfHeight = PhysixUtil.toBox2D(height) * 0.5f;
        float onePx =PhysixUtil.toBox2D(1);
        
        // Do not stick at walls
        addSlideShape(onePx, halfHeight, halfWidth, onePx - halfWidth);
        addSlideShape(onePx, halfHeight, halfWidth, halfWidth - onePx);
        
        addSensor(0, halfHeight, PhysixUtil.toBox2D(5), 1);
    }

    private void addSlideShape(float onePx, float halfHeight, float halfWidth, float x) {
        PolygonShape slideShape = new PolygonShape();
        slideShape.setAsBox(onePx, halfHeight - onePx, new Vec2(x, -onePx), 0);
        FixtureDef feetFixtureDef = new FixtureDef();
        feetFixtureDef.density = 0;
        feetFixtureDef.friction = 0.0f;
        feetFixtureDef.shape = slideShape;
        
        body.createFixture(feetFixtureDef);
    }
    
    private void addSensor(float posx, float posy, float radius, int collisionMask) {
        // TODO: sensor flag useless in physixsbox constructor
        CircleShape frontSensor = new CircleShape();
        frontSensor.m_radius = radius;
        frontSensor.m_p.set(posx,posy);
        
        
        FixtureDef sensorDef = new FixtureDef();
        sensorDef.isSensor = true;
        sensorDef.density = 0;
        sensorDef.shape = frontSensor;
        
        
        body.createFixture(sensorDef);
    }
    
    public boolean isGrounded() {
        return isGrounded;
    }
}
