package de.fhtrier.gdw.ss2013.physix;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

class PhysixDebugDraw extends DebugDraw {
    private final Graphics graphics;

    PhysixDebugDraw(IViewportTransform viewportTransform, Graphics graphics) {
        super(viewportTransform);
        this.graphics = graphics;
        this.setFlags(DebugDraw.e_aabbBit | DebugDraw.e_centerOfMassBit
                | DebugDraw.e_dynamicTreeBit | DebugDraw.e_jointBit
                | DebugDraw.e_pairBit | DebugDraw.e_shapeBit);
        }

    @Override
    public void drawPoint(Vec2 p, float radiusOnScreen, Color3f color) {
        graphics.setColor(new Color(color.x, color.y, color.z));
        graphics.drawRect(
                (int) PhysixUtil.toWorld(p.x)-1,
                (int) PhysixUtil.toWorld(p.y)-1, 2, 2);
    }

    @Override
    public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
        drawPolygon(vertices, vertexCount, color);
    }

    @Override
    public void drawCircle(Vec2 center, float radius, Color3f color) {
    }

    @Override
    public void drawSolidCircle(Vec2 p1, float radius, Vec2 axis, Color3f color) {
        graphics.setColor(new Color(color.x, color.y, color.z));

        radius = PhysixUtil.toWorld(radius);
        float px = PhysixUtil.toWorld(p1.x);
        float py = PhysixUtil.toWorld(p1.y);
        
        int stepSize = 20;
        int count = 0;
        int lastX = (int)px;
        int lastY = (int)py;
        for(int theta = 0; theta <= 360; theta+=stepSize)
        {
            int x = (int)(px + radius * Math.cos(Math.toRadians(theta)));
            int y = (int)(py + radius * Math.sin(Math.toRadians(theta)));
            graphics.drawLine(lastX, lastY, x,  y);
            lastX = x;
            lastY = y;
        }
    }

    @Override
    public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
        graphics.setColor(new Color(color.x, color.y, color.z));

        graphics.drawLine(
                (int) PhysixUtil.toWorld(p1.x),
                (int) PhysixUtil.toWorld(p1.y), 
                (int) PhysixUtil.toWorld(p2.x),
                (int) PhysixUtil.toWorld(p2.y));
    }

    @Override
    public void drawTransform(Transform xf) {
    }

    @Override
    public void drawString(float x, float y, String s, Color3f color) {
    }
    
}
