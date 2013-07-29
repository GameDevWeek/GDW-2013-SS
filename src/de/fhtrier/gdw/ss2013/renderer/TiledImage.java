package de.fhtrier.gdw.ss2013.renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Extending Slick Image to draw tiled images more memory efficient than the
 * Slick way (which creates a lot of sub-images).
 * 
 * @author Santo Pfingsten
 */
public class TiledImage extends Image {

    private float textureFactorX;
    private float textureFactorY;

    public TiledImage(String ref, Color trans) throws SlickException {
        super(ref, trans);
    }

    @Override
    protected void initImpl() {
        textureFactorX = textureWidth / width;
        textureFactorY = textureHeight / height;
    }

    public void drawTile(float x, float y, int w, int h, int srcX, int srcY) {
        init();

        float tx = srcX * textureFactorX;
        float ty = srcY * textureFactorY;
        float tx2 = tx + w * textureFactorX;
        float ty2 = ty + h * textureFactorY;

        GL.glTexCoord2f(tx, ty);
        GL.glVertex3f(x, y, 0);
        GL.glTexCoord2f(tx, ty2);
        GL.glVertex3f(x, y + h, 0);
        GL.glTexCoord2f(tx2, ty2);
        GL.glVertex3f(x + w, y + h, 0);
        GL.glTexCoord2f(tx2, ty);
        GL.glVertex3f(x + w, y, 0);
    }
}
