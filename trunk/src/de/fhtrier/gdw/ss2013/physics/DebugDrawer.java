package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.pooling.arrays.IntArray;
import org.jbox2d.pooling.arrays.Vec2Array;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import de.fhtrier.gdw.ss2013.game.camera.Camera;

public class DebugDrawer extends DebugDraw {
	GameContainer gameContainer;
	private Camera cam;
	// Total render points for each circle
	public static int CIRCLE_POINTS = 20;
	private final Vec2Array vec2Array = new Vec2Array();
	private final Vec2 sp1 = new Vec2();
	private final Vec2 sp2 = new Vec2();
	private final Vec2 saxis = new Vec2();
	// TODO change IntegerArray to a specific class for int[] arrays
	private final Vec2 temp = new Vec2();
	private final static IntArray xIntsPool = new IntArray();
	private final static IntArray yIntsPool = new IntArray();
	private final Vec2 temp2 = new Vec2();

	public DebugDrawer(final GameContainer gameContainer, Camera c) {
		super(new OBBViewportTransform());

		// WIP
		cam = c;
		this.viewportTransform.setYFlip(true);
		this.viewportTransform.setExtents(gameContainer.getWidth() / 2,
				gameContainer.getHeight() / 2);
		this.gameContainer = gameContainer;
		this.setFlags(DebugDraw.e_aabbBit + DebugDraw.e_centerOfMassBit
				+ DebugDraw.e_dynamicTreeBit + DebugDraw.e_jointBit
				+ DebugDraw.e_pairBit + DebugDraw.e_shapeBit);
	}

	/**
	 * @see org.jbox2d.callbacks.DebugDraw#drawCircle1(org.jbox2d.common.Vec2,
	 *      float, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawCircle(final Vec2 center, final float radius,
			final Color3f color) {
		final Vec2[] vecs = vec2Array.get(CIRCLE_POINTS);
		generateCirle(center, radius, vecs, CIRCLE_POINTS);
		drawPolygon(vecs, CIRCLE_POINTS, color);
	}

	/**
	 * @see org.jbox2d.callbacks.DebugDraw#drawPoint(org.jbox2d.common.Vec2,
	 *      float, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawPoint(final Vec2 argPoint, final float argRadiusOnScreen,
			final Color3f argColor) {
		this.viewportTransform.setCenter(
				gameContainer.getWidth() / 2 - cam.getTileX(),
				gameContainer.getHeight() / 2 - cam.getTileY());

		Graphics g = this.gameContainer.getGraphics();
		getWorldToScreenToOut(argPoint, sp1);
		g.setColor(new Color(argColor.x, argColor.y, argColor.z));

		sp1.x -= argRadiusOnScreen;
		sp1.y -= argRadiusOnScreen;
		g.fillOval((int) sp1.x, (int) sp1.y, (int) argRadiusOnScreen * 2,
				(int) argRadiusOnScreen * 2);
		g.setColor(Color.white);
	}

	/**
	 * @see org.jbox2d.callbacks.DebugDraw#drawSegment(org.jbox2d.common.Vec2,
	 *      org.jbox2d.common.Vec2, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawSegment(final Vec2 p1, final Vec2 p2, final Color3f color) {
		this.viewportTransform.setCenter(
				gameContainer.getWidth() / 2 + cam.getOffsetX(),
				gameContainer.getHeight() / 2 + cam.getOffsetY());

		Graphics g = this.gameContainer.getGraphics();
		getWorldToScreenToOut(p1, sp1);
		getWorldToScreenToOut(p2, sp2);
		g.setColor(new Color(color.x, color.y, color.z));

		g.drawLine((int) sp1.x, (int) sp1.y, (int) sp2.x, (int) sp2.y);
		g.setColor(Color.white);
	}

	public void drawAABB(final AABB argAABB, final Color3f color) {
		Graphics g = this.gameContainer.getGraphics();
		g.setColor(new Color(color.x, color.y, color.z));
		final Vec2 vecs[] = vec2Array.get(4);
		argAABB.getVertices(vecs);
		drawPolygon(vecs, 4, color);
		g.setColor(Color.white);
	}

	/**
	 * @see org.jbox2d.callbacks.DebugDraw#drawSolidCircle(org.jbox2d.common.Vec2,
	 *      float, org.jbox2d.common.Vec2, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawSolidCircle(final Vec2 center, final float radius,
			final Vec2 axis, final Color3f color) {
		Graphics g = this.gameContainer.getGraphics();
		final Vec2[] vecs = vec2Array.get(CIRCLE_POINTS);
		generateCirle(center, radius, vecs, CIRCLE_POINTS);
		drawSolidPolygon(vecs, CIRCLE_POINTS, color);
		if (axis != null) {
			saxis.set(axis).mulLocal(radius).addLocal(center);
			drawSegment(center, saxis, color);
		}
		g.setColor(Color.white);
	}

	/**
	 * @see org.jbox2d.callbacks.DebugDraw#drawSolidPolygon(org.jbox2d.common.Vec2[],
	 *      int, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawSolidPolygon(final Vec2[] vertices, final int vertexCount,
			final Color3f color) {
		this.viewportTransform.setCenter(
				gameContainer.getWidth() / 2 + cam.getOffsetX(),
				gameContainer.getHeight() / 2 + cam.getOffsetY());

		Graphics g = this.gameContainer.getGraphics();
		final int[] xInts = xIntsPool.get(vertexCount);
		final int[] yInts = yIntsPool.get(vertexCount);
		final Polygon p = new Polygon();

		for (int i = 0; i < vertexCount; i++) {
			getWorldToScreenToOut(vertices[i], temp);
			xInts[i] = (int) temp.x;
			yInts[i] = (int) temp.y;
			p.addPoint(xInts[i], yInts[i]);
		}

		g.setColor(new Color(color.x, color.y, color.z));
		g.fill(p); // Draws shape filled with colour
		g.setColor(Color.white);
	}

	/**
	 * @see org.jbox2d.callbacks.DebugDraw#drawString(float, float,
	 *      java.lang.String, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawString(final float x, final float y, final String s,
			final Color3f color) {
		this.viewportTransform.setCenter(
				gameContainer.getWidth() / 2 + cam.getOffsetX(),
				gameContainer.getHeight() / 2 + cam.getOffsetY());

		Graphics g = this.gameContainer.getGraphics();
		g.setColor(new Color(color.x, color.y, color.z));
		g.drawString(s, x, y);
		g.setColor(Color.white);
	}

	/**
	 * @see org.jbox2d.callbacks.DebugDraw#drawTransform(org.jbox2d.common.Transform)
	 */
	@Override
	public void drawTransform(final Transform xf) {
		this.viewportTransform.setCenter(
				gameContainer.getWidth() / 2 + cam.getOffsetX(),
				gameContainer.getHeight() / 2 + cam.getOffsetY());

		Graphics g = this.gameContainer.getGraphics();
		getWorldToScreenToOut(xf.p, temp);
		temp2.setZero();
		final float k_axisScale = 0.4f;

		g.setColor(new Color(1, 0, 0));
		Vec2 xAxis = new Vec2();
		xf.q.getXAxis(xAxis);
		temp2.x = xf.p.x + k_axisScale * xAxis.x;
		temp2.y = xf.p.y + k_axisScale * xAxis.y;
		getWorldToScreenToOut(temp2, temp2);
		g.drawLine((int) temp.x, (int) temp.y, (int) temp2.x, (int) temp2.y);

		g.setColor(new Color(0, 1, 0));
		Vec2 yAxis = new Vec2();
		xf.q.getYAxis(yAxis);
		temp2.x = xf.p.x + k_axisScale * yAxis.x;
		temp2.y = xf.p.y + k_axisScale * yAxis.y;
		getWorldToScreenToOut(temp2, temp2);
		g.drawLine((int) temp.x, (int) temp.y, (int) temp2.x, (int) temp2.y);
		g.setColor(Color.white);
	}

	// Circle Generator
	private void generateCirle(final Vec2 argCenter, final float argRadius,
			final Vec2[] argPoints, final int argNumPoints) {
		Graphics g = this.gameContainer.getGraphics();
		final float inc = MathUtils.TWOPI / argNumPoints;

		for (int i = 0; i < argNumPoints; i++) {
			argPoints[i].x = (argCenter.x + MathUtils.cos(i * inc) * argRadius);
			argPoints[i].y = (argCenter.y + MathUtils.sin(i * inc) * argRadius);
		}
		g.setColor(Color.white);
	}
}
