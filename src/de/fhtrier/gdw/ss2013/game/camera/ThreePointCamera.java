package de.fhtrier.gdw.ss2013.game.camera;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.constants.MathConstants;

public class ThreePointCamera {
	private final static float MIN_ZOOM = -0.5f;
	private final static float MAX_ZOOM = 1.f;

	private final Vector2f cameraPosition;
	private final ArrayList<Vector2f> targets;
	private final float cameraSpeed;

	private int screenWidth;
	private int screenHeight;
	private float zoomFactor;
	private boolean initialized;

	public ThreePointCamera(CameraInfo info) {
		cameraPosition = new Vector2f();
		targets = new ArrayList<>();
		targets.add(new Vector2f());
		cameraSpeed = info.cameraSpeed;
		zoomFactor = 1f;
	}

	public void update(int deltaTime, int width, int height) {
		screenHeight = height;
		screenWidth = width;
		float dt = deltaTime / 1000.f;

		Vector2f finalTarget = new Vector2f();
		int count = 1;
		for (Vector2f target : targets) {
			Vector2f newTarget = new Vector2f(target.x - finalTarget.x,
					target.y - finalTarget.y);
			finalTarget.add(newTarget.scale(1f / count));
			count += 1;
		}

		finalTarget.sub(cameraPosition).scale(dt * cameraSpeed);
		cameraPosition.add(finalTarget);

	}

	public void addDynamicTarget(Vector2f target) {
		if (!initialized) {
			targets.clear();
			initialized = true;
		}
		targets.add(target);
	}

	public void zoomOut(float factor) {
		this.zoomFactor /= factor;
	}

	public void zoomIn(float factor) {
		this.zoomFactor *= factor;
	}

	public void debugdraw(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(cameraPosition.x - 5, cameraPosition.y - 5, 10, 10);
		g.drawString("camPos", cameraPosition.x, cameraPosition.y);

		g.setColor(Color.green);
		for (Vector2f target : targets) {
			g.drawRect(target.x - 5, target.y - 5, 10, 10);
			g.drawString("target", target.x, target.y);
		}
	}

	public Vector2f screenToWorldCoordinates(Vector2f screenPosition) {
		Vector2f position = new Vector2f();
		position.set(screenPosition);
		position.scale(1.0f/zoomFactor);
		position.add(cameraPosition);
		position.x -= screenWidth * (1.0f/zoomFactor) / 2.0f;
		position.y -= screenHeight * (1.0f/zoomFactor) / 2.0f;
		return position;
	}

	public void pushViewMatrix(Graphics g) {
		g.pushTransform();
		g.translate(screenWidth * (1 - zoomFactor) / 2.0f, screenHeight
				* (1 - zoomFactor) / 2.0f);
		g.scale(zoomFactor, zoomFactor);
		g.translate(-cameraPosition.x + screenWidth / 2.0f, -cameraPosition.y
				+ screenHeight / 2.0f);
		// g.translate(cameraPosition.x * 1/zoomFactor, cameraPosition.y *
		// 1/zoomFactor);
		// g.translate(cameraPosition.x - screenWidth / 2.0f, cameraPosition.y -
		// screenHeight / 2.0f);
	}

	public void popViewMatrix(Graphics g) {
		g.popTransform();
	}

	public Vector2f worldToScreenCoordinates(Vector2f worldPosition) {
		Vector2f position = new Vector2f();
		position.set(worldPosition);
		position.sub(cameraPosition);
		position.scale(zoomFactor);
		position.x += screenWidth / 2.0f;
		position.y += screenHeight / 2.0f;
		return position;
	}

	public void setZoom(float zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public float getZoom() {
		return this.zoomFactor;
	}

}
