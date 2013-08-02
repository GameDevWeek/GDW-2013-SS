package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.FixtureDef;

import de.fhtrier.gdw.ss2013.constants.MathConstants;

public class PhysixBoxPlayer extends PhysixBox {

    protected boolean isGrounded = false;

    PhysixBoxPlayer(PhysixShapeConfig config) {
        super(config);
        fixture.m_friction = 0;
        
        addFeet(0, config.halfHeight, (config.halfWidth - MathConstants.EPSILON_F)
                - PhysixUtil.toBox2D(1), config.friction);
    }

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
