package de.fhtrier.gdw.ss2013.menu;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;


public class PathMover {
	boolean oriented = true;
	List<Destination> path;
	int destination = 0;
	Vector2f position;
	float angle;
	private int delay;
	protected IActionListener doneAction;
	
	public PathMover(List<Destination> path, boolean oriented) {
		this.oriented = oriented;
		this.position = new Vector2f(path.get(0));
		this.path = path;
		destination = 1;
		if(oriented) {
			Vector2f dir = new Vector2f(path.get(1)).sub(position).normalise();
			angle = (float)Math.toDegrees(Math.atan2(dir.y, dir.x)) - 90;
		}
	}
	
	public static class Destination extends Vector2f {
		private float speed;
		private int delay;
		public Destination(float x, float y, float speed, int delay) {
			super(x,y);
			this.speed = speed;
			this.delay = delay;
		}
		
		public float getSpeed() {
			return speed;
		}
		
		public int getDelay() {
			return delay;
		}
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public PathMover delay(int value) {
		delay = value;
		return this;
	}
	
	public void update(int delta) {
		if(delta <= 0 || destination == -1)
			return;
		
		if(delta > 100)
			delta = 100;

		delay -= delta;
		if(delay >= 0)
			return;
		
		delay = 0;
		
		Destination next = path.get(destination);
		float speed = next.getSpeed();
		Vector2f dir = new Vector2f(next).sub(position).normalise();
		
		boolean arrived = speed == -1;
		if(!arrived) {
			float step = speed * delta / 1000.0f;
			dir.scale(step);
			arrived = position.distance(next) <= step;
		}
		
		if(arrived) {
			position.set(next);
			// move done
			destination++;
			if(destination == path.size()) {
				if(doneAction != null)
					doneAction.onAction();
				destination = -1;
			} else {
				next = path.get(destination);
				delay = next.getDelay();
			}
		} else {
			position.add(dir);
		}
		
		dir.normalise();
		if(oriented)
			angle = (float)Math.toDegrees(Math.atan2(dir.y, dir.x)) - 90;
	}
	
	public void done(IActionListener value) {
		doneAction = value;
	}
}
