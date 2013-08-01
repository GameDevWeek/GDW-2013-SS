package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import de.fhtrier.gdw.ss2013.math.MathConstants;

public class PhysixBoxPlayer extends PhysixBox {

    protected boolean isGrounded = false;

    public PhysixBoxPlayer(PhysixManager physicsManager, float x, float y,
            float width, float height, float density, float friction) {
        super(physicsManager, x, y, width, height, BodyType.DYNAMIC, density,
                0.0f, false);

        float halfWidth = PhysixUtil.toBox2D(width) * 0.5f;
        float halfHeight = PhysixUtil.toBox2D(height) * 0.5f;
        // float onePx = PhysixUtil.toBox2D(1);
        //
        // // Do not stick at walls
        // addSlideShape(onePx, halfHeight, halfWidth, onePx - halfWidth);
        // addSlideShape(onePx, halfHeight, halfWidth, halfWidth - onePx);

        addFeet(0, halfHeight, (halfWidth - MathConstants.EPSILON_F)
                - PhysixUtil.toBox2D(1), friction);
    }

    // private void addSlideShape(float onePx, float halfHeight, float
    // halfWidth, float x) {
    // PolygonShape slideShape = new PolygonShape();
    // slideShape.setAsBox(onePx, halfHeight - onePx, new Vec2(x, -onePx), 0);
    // FixtureDef slideFixtureDef = new FixtureDef();
    // slideFixtureDef.density = 0;
    // slideFixtureDef.friction = 0.0f;
    // slideFixtureDef.shape = slideShape;
    //
    // body.createFixture(slideFixtureDef);
    // }

    private void addFeet(float posx, float posy, float radius, float friction) {
        // TODO: sensor flag useless in physixsbox constructor
        CircleShape feetShape = new CircleShape();
        feetShape.m_radius = radius;
        feetShape.m_p.set(posx, posy);

        FixtureDef feetFixtureDef = new FixtureDef();
        feetFixtureDef.isSensor = false;
        feetFixtureDef.density = 0;
        feetFixtureDef.friction = friction;
        feetFixtureDef.shape = feetShape;

        body.createFixture(feetFixtureDef);
    }

    public boolean isGrounded() {
        return isGrounded;
    }
}
