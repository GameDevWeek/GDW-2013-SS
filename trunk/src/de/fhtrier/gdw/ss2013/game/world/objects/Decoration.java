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

public class Decoration extends Entity {

    private final static Random random = new Random();
    private Animation animation;
    private int minWaitTime;
    private int maxWaitTime;
    private int nextAnimation;
    private boolean isPlaying;

    public Decoration() {
    }

    @Override
    protected void initialize() {
        super.initialize();

        String anim = properties.getProperty("animation", null);
        if (anim != null) {
            minWaitTime = properties.getInt("min_wait", 4000);
            maxWaitTime = properties.getInt("min_wait", 8000);
            nextAnimation = minWaitTime + random.nextInt(maxWaitTime - minWaitTime);
            animation = AssetLoader.getInstance().getAnimation(anim);
            animation.stop();
            animation.setCurrentFrame(0);
            animation.setLooping(false);
            setInitialSize(animation.getWidth(), animation.getHeight());
        } else {
            String image = properties.getProperty("image", null);
            if (image == null) {
                throw new RuntimeException("neither image nor animation set for decoration");
            }
            setImage(AssetLoader.getInstance().getImage(image));
            setInitialSize(img.getWidth(), img.getHeight());
        }
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
        if (animation == null) {
            super.update(container, delta);
        }
        else {
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
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (animation == null) {
            super.render(gc, g);
        } else {
            animation.draw(origin.x - initialSize.x / 2.0f,
                    origin.y - initialSize.y / 2.0f);
        }
    }
}
