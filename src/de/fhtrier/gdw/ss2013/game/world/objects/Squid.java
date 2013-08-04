
package de.fhtrier.gdw.ss2013.game.world.objects;

import java.util.Random;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.world.World;

public class Squid extends Entity {

	private double currentRotation = 0f;;
	private Vector2f velocity = new Vector2f();
	private Vector2f accel;
	private float wanderAngle = 0f;
	private float wanderAngularVelocity = (float)(0.1f * ((2f * Math.random()) / ((Math.random() * RAND_MAX)) - 1));

	
	private static final float MAX_VELOCITY = 10;
	private static int RAND_MAX = 0x7fff;
	private static float CENTER_OFFSET = 0f;
	private static float WANDER_RADIUS = 200f;
	private static float MAX_WANDER_VELO = 0.1f;
	private static Random random = new Random(System.currentTimeMillis());

	public Squid () {
		super(getRandomImage());
	}

	private static Image getRandomImage() {
		int rand = (int) (Math.random()*2);//can be 1 or 0
		return AssetLoader.getInstance().getImage("squid_"+(rand+1));
	}

	@Override
	protected void initialize() {
	    super.initialize();
	    renderLayer = -1;
	}
	
	@Override
	public void update (GameContainer container, int delta) throws SlickException {
		super.update(container, delta);

		Vector2f desiredPosition = calculateTargetPosition();
		Vector2f desiredVelocity = desiredPosition.sub(getPosition());
		accel = desiredVelocity.sub(velocity);
		velocity = accel.scale(delta);
		if (velocity.length() > MAX_VELOCITY) {
			velocity.normalise();
			velocity.scale(MAX_VELOCITY);
		}
		setVelocity(velocity);

		double wanderAngleAccel =(0.5f - (random.nextFloat()));// (0.2 * Math.random() / (Math.random() * RAND_MAX) - 0.1);
		wanderAngularVelocity += 0.05f* wanderAngleAccel;

		// clamp
		wanderAngularVelocity=Math.max(-MAX_WANDER_VELO, Math.min(wanderAngularVelocity, MAX_WANDER_VELO));
		wanderAngle += 0.1f * wanderAngularVelocity;
		currentRotation *= wanderAngularVelocity * delta;
	}

	@Override
	public void render (GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		super.render(container, g);
		if (World.getInstance().debugDraw) {
			Vector2f circleCenter = calculateCirlceCenter();
			g.setColor(Color.red);
			g.drawOval(circleCenter.x - WANDER_RADIUS, circleCenter.y - WANDER_RADIUS, WANDER_RADIUS * 2, WANDER_RADIUS * 2);
			Vector2f desiredPosition = calculateTargetPosition();
			g.fillOval(desiredPosition.x, desiredPosition.y, 10, 10);
		}
	}

	@Override
	public void initPhysics () {
		createPhysics(BodyType.KINEMATIC, origin.x, origin.y).sensor(true).asBox(initialSize.x, initialSize.y);
	}

	private Vector2f calculateCirlceCenter () {

		double rotation = Math.toRadians(currentRotation);
		Vector2f faceDirection = new Vector2f((float)Math.cos(rotation), (float)Math.sin(rotation));

		// Log.debug(""+ getPosition().add(faceDirection.scale(CENTER_OFFSET)));
		return getPosition().add(faceDirection.scale(CENTER_OFFSET));
	}

	private Vector2f calculateTargetPosition () {
		Vector2f circleCenter = calculateCirlceCenter();
		double rotation = Math.toRadians(currentRotation);
		float angle = (float)(wanderAngle + rotation);
		Vector2f circlePoint = new Vector2f((float)Math.cos(angle), (float)Math.sin(angle)).scale(WANDER_RADIUS);
		// Log.debug(""+circleCenter.add(circlePoint) );
		return circleCenter.add(circlePoint);
	}

}
