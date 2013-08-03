package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;
import de.fhtrier.gdw.ss2013.renderer.TiledImage;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Graphics;

public class DecorationTile extends Entity {
    private int srcX;
    private int srcY;
    private TiledImage image;

    public DecorationTile() {
        super();
    }

    public void setTiledImage(TiledImage image) {
        this.image = image;
    }
    
    public void setSrcPos(int x, int y) {
        srcX = x;
        srcY = y;
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y)
                .category(PhysixConst.DECORATION)
                .sensor(true).asCircle(0);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        image.startUse();
        image.drawTile(origin.x - initialSize.x * 0.5f, origin.y - initialSize.y * 0.5f,
                (int)initialSize.x, (int)initialSize.y, srcX, srcY);
        image.endUse();
    }
}
