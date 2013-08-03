package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;
import java.util.Random;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

public class DecorationAnimation extends Entity {

    private final static Random random = new Random();
    private Animation animation;
    private int minWaitTime;
    private int maxWaitTime;
    private int nextAnimation;
    private boolean isPlaying;

    public DecorationAnimation() {
        super();
    }

    @Override
    protected void initialize() {
        super.initialize();

        String anim = properties.getProperty("animation");
        if (anim == null) {
            throw new RuntimeException("animation not set for decoration");
        }
        minWaitTime = properties.getInt("min_wait", 4000);
        maxWaitTime = properties.getInt("min_wait", 8000);
        nextAnimation = minWaitTime + random.nextInt(maxWaitTime - minWaitTime);
        animation = AssetLoader.getInstance().getAnimation(anim);
        animation.stop();
        animation.setCurrentFrame(0);
        animation.setLooping(false);
        setInitialSize(animation.getWidth(), animation.getHeight());
    }

    @Override
    public void initPhysics() {
        createPhysics(BodyType.STATIC, origin.x, origin.y)
                .category(PhysixConst.DECORATION)
                .sensor(true).asCircle(0);
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        nextAnimation -= delta;
        if (!isPlaying && nextAnimation <= 0) {
            animation.restart();
            isPlaying = true;
        } else if (isPlaying && animation.isStopped()) {
            animation.stop();
            animation.setCurrentFrame(0);
            isPlaying = false;
            nextAnimation = minWaitTime + random.nextInt(maxWaitTime - minWaitTime);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        animation.draw(origin.x - initialSize.x / 2.0f,
                origin.y - initialSize.y / 2.0f);
    }
}
