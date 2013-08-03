package de.fhtrier.gdw.ss2013.game.player;

import java.util.ArrayList;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.GameDataInfo;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.bullets.PlayerBullet;
import de.fhtrier.gdw.ss2013.game.world.objects.Box;
import de.fhtrier.gdw.ss2013.input.AlienController;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

public final class Alien extends Entity implements AlienController {

    private Astronaut astronaut;
    private Animation animation;
    private int selectedAbility;
    private float mana;
    private float maxMana;
    private final Vector2f cursor = new Vector2f();
    private GameContainer container;
    private EntityManager entityManager = World.getInstance()
            .getEntityManager();
    // telekinese values
    private Box currentSelectedBox;
    private float selectionRadius;
    private final Vector2f dragDirection = new Vector2f();
    private long lastShotTime;
    private boolean onPlayer;
    private boolean invertAnimation;
    private Vector2f dynamicTarget = new Vector2f();
    private float maxDistance;

    private SoundPlayer soundPlayer = SoundLocator.getPlayer();
    private Sound shootSound;

    public Alien() {
        animation = AssetLoader.getInstance().getAnimation("alien_standing");
        // Alien does NOT have different movestates! byRobin
        currentSelectedBox = null;
        selectionRadius = 50;
        lastShotTime = 0;
        selectedAbility = 1;
        maxMana = 0.0f;
        mana = maxMana;
    }

    public void setAstronaut(Astronaut astronaut) {
        this.astronaut = astronaut;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();
        currentSelectedBox = null;
        selectionRadius = 50;
        lastShotTime = 0;
        selectedAbility = 1;
        maxMana = 0.0f;
        mana = maxMana;
        GameDataInfo info = AssetLoader.getInstance().getGameData();
        maxDistance = info.alien.maxDistance;
        shootSound = SoundLocator.loadSound("alienschuss");
        World.getInstance().getTPCamera().addDynamicTarget(dynamicTarget);
    }

    public void setContainer(GameContainer container) {
        this.container = container;
    }

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public float getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public void shoot() {
        if (System.currentTimeMillis() > lastShotTime
                + PlayerConstants.SHOTDELAY) {
            soundPlayer.playSound(shootSound);
            Vector2f playerPos;
            if (astronaut.isCarryAlien()) {
                playerPos = astronaut.getPosition(); // notwendig, weil alienPos
                                                     // net stimmt
            } else {
                playerPos = getPosition();
            }
            Vector2f shootDirection = new Vector2f(World.getInstance()
                    .screenToWorldPosition(getCursor()));
            shootDirection.sub(playerPos);

            shootDirection = shootDirection.normalise().scale(
                    PlayerConstants.BULLET_SPEED);

            // Create a meteorite entity
            PlayerBullet entity = entityManager
                    .createEntity(PlayerBullet.class);
            entity.setOrigin(playerPos.x, playerPos.y);
            entity.setShootDirection(shootDirection);

            lastShotTime = System.currentTimeMillis();
        }
    }

    @Override
    public void nextAbility() {
        selectedAbility = (selectedAbility % 3) + 1;

        Log.debug("rotate ability");
        dropCurrentSelected(); // if current has selection
    }

    @Override
    public void previousAbility() {

        /*
         * if (selectedAbility > 0){ selectedAbility--; }else{ selectedAbility =
         * 3; }
         */

        selectedAbility = ((selectedAbility + 1) % 3) + 1;
        dropCurrentSelected();
    }

    public int getselectedAbility() {
        return selectedAbility;
    }

    @Override
    public void useAbility() {
        switch (selectedAbility) {
        case 1:
            // telekinese
            // System.out.println("lsdidioga");
            if (currentSelectedBox == null) {
                Vector2f cursorPos = World.getInstance().screenToWorldPosition(
                        cursor);
                ArrayList<Entity> closestEntitiesAtPosition = entityManager
                        .getClosestEntitiesAtPosition(World.getInstance()
                                .screenToWorldPosition(cursor), selectionRadius);
                for (Entity e : closestEntitiesAtPosition) {
                    if (e instanceof Box) {
                        currentSelectedBox = (Box) e;
                        currentSelectedBox.getPhysicsObject()
                                .setLinearVelocity(
                                        currentSelectedBox.getPosition().sub(
                                                cursorPos));
                        return;
                    }
                }

            } else {
                dropCurrentSelected();
            }
            break;
        case 2:
            // großer sprung
            astronaut.superjump();
            break;
        }
    }

    Vector2f screenCursor = new Vector2f();

    @Override
    public void setCursor(int x, int y) {
        cursor.set(Math.min(container.getWidth(), Math.max(0, x)),
                Math.min(container.getHeight(), Math.max(0, y)));
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        super.update(container, delta);

        if (onPlayer) {
            dynamicTarget.set(astronaut.getPosition());
        } else {
            dynamicTarget.set(this.getPosition());

            if (astronaut.getPosition().distance(getPosition()) >= maxDistance) {
                astronaut.toggleAlien();
            }
        }

        switch (selectedAbility) {
        case 1:
            // telekinese
            if (currentSelectedBox != null
                    && currentSelectedBox.getPhysicsObject() != null) {
                Vector2f screenToWorldPosition = World.getInstance()
                        .screenToWorldPosition(cursor);
                dragDirection.x = screenToWorldPosition.x
                        - currentSelectedBox.getPosition().x;
                dragDirection.y = screenToWorldPosition.y
                        - currentSelectedBox.getPosition().y;
                currentSelectedBox.setVelocity(dragDirection);
                if (currentSelectedBox.isPlayerOnBox()) {
                    dropCurrentSelected();
                }
            }
        }
    }

    public void dropCurrentSelected() {
        if (currentSelectedBox != null) {
            currentSelectedBox.getPhysicsObject().setGravityScale(1.f);

            currentSelectedBox = null;
        }
    }

    public Box getCurrentSelectedBox() {
        return currentSelectedBox;
    }

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        if (!onPlayer) {
            Vector2f position = getPosition();

            if (invertAnimation) {
                animation.draw(position.x + animation.getWidth() / 2,
                        position.y - animation.getHeight() / 2,
                        -animation.getWidth(), animation.getHeight());
            } else {
                animation.draw(position.x - animation.getWidth() / 2,
                        position.y - animation.getHeight() / 2);
            }
        }
    }

    @Override
    public void cursorLeft(float scale) {
        cursor.x = Math.min(container.getWidth(),
                Math.max(0, cursor.x - 10.0f * scale));
    }

    @Override
    public void cursorRight(float scale) {
        cursor.x = Math.min(container.getWidth(),
                Math.max(0, cursor.x + 10.0f * scale));
    }

    @Override
    public void cursorUp(float scale) {
        cursor.y = Math.min(container.getHeight(),
                Math.max(0, cursor.y - 10.0f * scale));
    }

    @Override
    public void cursorDown(float scale) {
        cursor.y = Math.min(container.getHeight(),
                Math.max(0, cursor.y + 10.0f * scale));
    }

    public Vector2f getCursor() {
        return cursor.copy();
    }

    void setOnPlayer(boolean onPlayer) {
        this.onPlayer = onPlayer;
        if (physicsObject != null) {
            if (!onPlayer) {
                physicsObject.setPosition(astronaut.getPosition());
            }
            physicsObject.setActive(!onPlayer);
        }
    }

    @Override
    public void initPhysics() {
        GameDataInfo info = AssetLoader.getInstance().getGameData();
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(info.alien.density).friction(info.alien.friction)
                .category(PhysixConst.PLAYER).mask(PhysixConst.MASK_PLAYER)
                .active(false).asPlayer(info.alien.width, info.alien.height);

        // World.getInstance().getPhysicsManager().ropeConnect(astronaut.getPhysicsObject(),
        // getPhysicsObject(), info.alien.maxDistance);
        physicsObject.setActive(!onPlayer);
    }
}
