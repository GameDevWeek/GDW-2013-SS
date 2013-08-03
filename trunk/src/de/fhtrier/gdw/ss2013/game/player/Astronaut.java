package de.fhtrier.gdw.ss2013.game.player;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.GameDataInfo;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.cheats.Cheats;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.enemies.AbstractEnemy;
import de.fhtrier.gdw.ss2013.game.world.objects.Switch;
import de.fhtrier.gdw.ss2013.input.AlienController;
import de.fhtrier.gdw.ss2013.input.AstronautController;
import de.fhtrier.gdw.ss2013.physix.InteractionManager;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;
import de.fhtrier.gdw.ss2013.physix.PhysixShape;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

public final class Astronaut extends EntityCollidable implements
        AstronautController, AlienController {

    private float oxygen;
    private float maxOxygen;
    private boolean carryAlien;
    private float speed;
    private float jumpSpeed;
    private int jumpDelay = 0;
    private int jumpDelayTotal;
    // set of entities, which can currently be activated with the action button
    private InteractionManager interactionManager;

    private Set<Switch> switches = new HashSet<>();

    private PlayerState state;
    private boolean invertAnimation;
    private boolean walking;
    private Alien alien;
    private GameDataInfo gameData;
    private Animation animation;
    private int groundContacts;
    private int superJumpDelay = 0;
    private float superJumpSpeed;
    private float superJumpGravityScale;

    private int restTimeBitenFilter;
    private SoundPlayer soundPlayer = SoundLocator.getPlayer();
    private Sound stepSound1;
    private Sound stepSound2;
    private Sound jumpSound;
    private Sound wingSound;
    private Sound dropAlienSound;
    private Sound dieSound;
    private Sound hitSound;
    private int stepFrame1 = 1;
    private int stepFrame2 = 4;

    // private long time = 0;
    private int deadtime = 5000;

    public Astronaut() {
        AssetLoader al = AssetLoader.getInstance();
        for (PlayerState s : PlayerState.values()) {
            s.setAnimations(al.getAnimation("player_couple_" + s.toString()),
                    al.getAnimation("astronaut_" + s.toString()));
        }

        gameData = AssetLoader.getInstance().getGameData();
    }

    @Override
    public boolean isBottomPositioned() {
        return true;
    }

    public void setAlien(Alien alien) {
        this.alien = alien;
        carryAlien = true;
        alien.setOnPlayer(true);
        alien.setAstronaut(this);
    }

    private Vector2f dynamicTarget = new Vector2f();

    /** {@inheritDoc} */
    @Override
    protected void initialize() {
        super.initialize();
        renderLayer = Integer.MAX_VALUE;
        speed = gameData.combined.speed;
        jumpSpeed = gameData.combined.jumpSpeed;
        jumpDelayTotal = gameData.combined.jumpDelay;
        maxOxygen = gameData.astronaut.oxygen;
        superJumpGravityScale = gameData.combined.superJumpGravityScale;
        superJumpSpeed = gameData.combined.superJumpSpeed;
        oxygen = maxOxygen;
        carryAlien = true;
        invertAnimation = false;

        jumpSound = SoundLocator.loadSound("absprung");
        wingSound = SoundLocator.loadSound("fluegelschlag");
        dropAlienSound = SoundLocator.loadSound("alienabsetzen");
        dieSound = SoundLocator.loadSound("spielertot");
        hitSound = SoundLocator.loadSound("spielergetroffen");

        setState(PlayerState.standing);
        World.getInstance().getTPCamera().addDynamicTarget(dynamicTarget);
    }

    public void setFootSteepSound(int type) {
        switch (type) {
        case 0:
            stepSound1 = SoundLocator.loadSound("schritt1");
            stepSound2 = SoundLocator.loadSound("schritt2");
            break;
        case 1:
            stepSound1 = SoundLocator.loadSound("kristallschrittfuss1");
            stepSound2 = SoundLocator.loadSound("kristallschrittfuss2");
            break;
        case 2:
            stepSound1 = SoundLocator.loadSound("metallschrittfuss1");
            stepSound2 = SoundLocator.loadSound("metallschrittfuss2");
            break;
        default:
            stepSound1 = SoundLocator.loadSound("schritt1");
            stepSound2 = SoundLocator.loadSound("schritt2");
            break;

        }
    }

    public float getOxygen() {
        return oxygen;
    }

    public void setOxygen(float oxygen) {
        this.oxygen = oxygen;
    }

    public float getMaxOxygen() {
        return maxOxygen;
    }

    public void setMaxOxygen(float maxOxygen) {
        this.maxOxygen = maxOxygen;
    }

    public void setJumpSpeed(float newJumpSpeed) {
        jumpSpeed = newJumpSpeed;
    }

    public void setDamage(float damage) {
        oxygen -= damage;
        soundPlayer.playSound(hitSound);
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        super.update(container, delta);

        if (!state.equals(PlayerState.dead)) {
            if (jumpDelay > 0)
                jumpDelay -= delta;
            if (superJumpDelay > 0)
                superJumpDelay -= delta;
            if (oxygen > 0)
                this.oxygen -= (this.maxOxygen * PlayerConstants.OXYGEN_PERCENTAGE_LOST_PER_SECOND)
                        * (delta / 1000f);
            else
                die();
            
            Vector2f pos = this.getPosition();
            dynamicTarget.set(pos.x, pos.y);
        }

        switch (state) {
        case jumping:
            if (getVelocity().y > 0) {
                setState(PlayerState.falling);
            }
            break;
        case superjump:
            soundPlayer.playSound(wingSound);
            if (isGrounded()) {
                setState(PlayerState.superjump_end);
                physicsObject.setGravityScale(1);
            }
            break;
        case superjump_start:
            if (animation.isStopped()) {
                setState(PlayerState.superjump);
            }
            break;
        case superjump_end:
            if (animation.isStopped()) {
                setState(PlayerState.standing);
            }
            break;
        case falling:
            if (isGrounded()) {
                setState(PlayerState.standing);
            }
            break;
        case dead:
            deadtime -= delta;
            if (deadtime <= 0) {
                World.getInstance().shallBeReseted(true);
            }
            break;
        default:
            if (getVelocity().x == 0 && getVelocity().y == 0 || !(walking)) {
                setState(PlayerState.standing);
            }
            break;

        }
        if (restTimeBitenFilter > 0) {
            restTimeBitenFilter -= delta;
            if (restTimeBitenFilter < 0) {
                restTimeBitenFilter = 0;
            }
        }
        // if (!oldState.equals(state)) Log.debug(state.toString());
        // oldState = state;
    }

    public void preInput() {
        walking = false;
    }

    @Override
    public void moveRight() {
        if (!state.equals(PlayerState.dead)) {
            setVelocityX(speed);
            if (!(state.equals(PlayerState.superjump)
                    || state.equals(PlayerState.superjump_start)
                    || state.equals(PlayerState.superjump_end)
                    || state.equals(PlayerState.jumping) || state
                        .equals(PlayerState.falling))) {
                setState(PlayerState.walking);

                playStepSound();
            }
            invertAnimation = false;
            walking = true;
        }
    }

    @Override
    public void moveLeft() {
        if (!state.equals(PlayerState.dead)) {
            setVelocityX(-speed);
            if (!(state.equals(PlayerState.superjump)
                    || state.equals(PlayerState.superjump_start)
                    || state.equals(PlayerState.superjump_end)
                    || state.equals(PlayerState.jumping) || state
                        .equals(PlayerState.falling))) {
                setState(PlayerState.walking);
                playStepSound();
            }
            invertAnimation = true;
            walking = true;
        }
    }

    @Override
    public void jump() {
        if (!state.equals(PlayerState.dead)) {
            if (jumpDelay <= 0 && isGrounded()) {
                jumpDelay = 0;
                setVelocityY(-jumpSpeed);
                physicsObject.applyImpulse(new Vector2f(0, -jumpSpeed));
                setState(PlayerState.jumping);
                jumpDelay = jumpDelayTotal;
                soundPlayer.playSound(jumpSound);
                // SoundLocator.getPlayer().playSound("absprung");
            }
        }
    }

    public void superjump() {
        if (!state.equals(PlayerState.dead)) {
            if (superJumpDelay <= 0 && isGrounded() && isCarryAlien()) {
                jumpDelay = 0;
                setVelocityY(-superJumpSpeed);
                physicsObject.applyImpulse(new Vector2f(0, -superJumpSpeed));
                setState(PlayerState.superjump_start);
                physicsObject.setGravityScale(superJumpGravityScale);
                superJumpDelay = jumpDelayTotal;
                soundPlayer.playSound(jumpSound);

            }
        }
    }

    @Override
    public void action() {
        for (Switch s : switches) {
            s.turnSwitch();
        }
    }

    private void playStepSound() {

        if (animation.getFrame() == stepFrame1) {
            soundPlayer.playSound(stepSound1);
        } else if (animation.getFrame() == stepFrame2) {
            soundPlayer.playSound(stepSound2);
        }
    }

    public boolean isCarryAlien() {
        return carryAlien;
    }

    public float getJumpSpeed() {
        return jumpSpeed;
    }

    public PlayerState getState() {
        return state;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float newSpeed) {
        speed = newSpeed;
    }

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        Vector2f position = getPosition();

        Color filter = Color.white;
        if (restTimeBitenFilter > 0) {
            float filterStrength = (float) (Math.abs(Math
                    .sin(restTimeBitenFilter / 200f))); // 200 ist smooth
            filter = new Color(filterStrength, filterStrength, filterStrength);
        }

        if (invertAnimation) {
            animation.draw(position.x + animation.getWidth() / 2, position.y
                    - animation.getHeight() / 2, -animation.getWidth(),
                    animation.getHeight(), filter);
        } else {
            animation.draw(position.x - animation.getWidth() / 2, position.y
                    - animation.getHeight() / 2, filter);
        }

        // if (state.equals(PlayerState.dead)) {
        //
        // String msg = "LOL DU NOOB";
        // Font font = AssetLoader.getInstance().getFont("quartz_40");
        // g.drawString(msg,
        // container.getScreenWidth() / 2 - font.getWidth(msg) / 2,
        // container.getScreenHeight() / 2 - font.getHeight(msg) / 2);
        //
        // }

        // Log.debug(animation.getFrame()+1 +"/" + animation.getFrameCount());
        // if (!oldState.equals(state)) Log.debug(String.valueOf(state));
        // oldState = state;
        super.render(container, g);
    }
		
		
    public void setState(PlayerState state) {
        if (this.state != PlayerState.dead
                && (this.state == null || !this.state.equals(state))) {
            this.state = state;
            updateStateAnimation();
        }
    }

    private void updateStateAnimation() {
        if (isCarryAlien()) {
            setAnimation(state.getCombinedAnimation());
        } else {
            setAnimation(state.getAstronautAnimation());
        }
    }

    // overriding because the InteractionManager has to be added to the
    // PhysixObject
    // and the Astronaut needs to know its InteractionManager, so it is done
    // here
    @Override
    public void setPhysicsObject(PhysixShape physicsObject) {
        interactionManager = new InteractionManager();
        physicsObject.addCollisionListener(interactionManager);
        super.setPhysicsObject(physicsObject);
    }

    public Animation getAnimation() {
        return animation;
    }

    public void die() {
        if (!Cheats.isGodmode) {
            setState(PlayerState.dead);
            soundPlayer.playSound(dieSound);
        }
    }

    public void setAnimation(Animation animation) {
        if (this.animation != animation) {
            this.animation = animation;
            animation.restart();
        }
    }

    public boolean isGrounded() {
        return groundContacts > 0;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixShape objectA = (PhysixShape) a.getBody().getUserData();
        PhysixShape objectB = (PhysixShape) b.getBody().getUserData();

        AbstractEnemy damageDealer = null;
        Astronaut damageTaker = null;
        if (objectA.getOwner() instanceof AbstractEnemy
                && objectB.getOwner() instanceof Astronaut) {
            damageTaker = (Astronaut) objectB.getOwner();
            damageDealer = (AbstractEnemy) objectA.getOwner();
        } else if (objectB.getOwner() instanceof AbstractEnemy
                && objectA.getOwner() instanceof Astronaut) {
            damageTaker = (Astronaut) objectA.getOwner();
            damageDealer = (AbstractEnemy) objectB.getOwner();
        }

        if (physicsObject == objectA && a.m_shape.getType() == ShapeType.CIRCLE
                && !b.m_isSensor) {
            groundContacts++;
        } else if (physicsObject == objectB
                && b.m_shape.getType() == ShapeType.CIRCLE && !a.m_isSensor) {
            groundContacts++;
        }

        if (damageDealer != null && damageTaker != null) {

            Vector2f damageTakerPos = damageTaker.getPosition();
            Vector2f damageTakerDim = damageTaker.getPhysicsObject()
                    .getDimension();

            Vector2f damageDealerPos = damageDealer.getPosition();
            Vector2f damageDealerDim = damageDealer.getPhysicsObject()
                    .getDimension();

            if ((damageTakerPos.x + damageTakerDim.x > damageDealerPos.x
                    - damageDealerDim.x) //
                    && ((damageTakerPos.x - damageTakerDim.x < damageDealerPos.x
                            + damageDealerDim.x))
                    && (damageTakerPos.y + damageTakerDim.y < damageDealerPos.y)) { // player
                                                                                    // deals
                                                                                    // damage
                World.getInstance().getScoreCounter().addScore(5);
                World.getInstance().getEntityManager()
                        .removeEntity(damageDealer);
                if (damageTaker instanceof Astronaut)
                    damageTaker.setVelocityY(-.50f
                            * ((Astronaut) damageTaker).getJumpSpeed());

            } else {

            }
        }
        Entity other = getOtherEntity(contact);
        if (other instanceof Switch) {
            switches.add((Switch) other);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixShape objectA = (PhysixShape) a.getBody().getUserData();
        PhysixShape objectB = (PhysixShape) b.getBody().getUserData();
        if (physicsObject == objectA && a.m_shape.getType() == ShapeType.CIRCLE
                && !b.m_isSensor) {
            groundContacts--;

        } else if (physicsObject == objectB
                && b.m_shape.getType() == ShapeType.CIRCLE && !a.m_isSensor) {
            groundContacts--;

        }
        assert (groundContacts >= 0);

        Entity other = getOtherEntity(contact);
        if (other instanceof Switch) {
            switches.remove((Switch) other);
        }
    }


    @Override
    public void shoot() {
        if (!state.equals(PlayerState.dead)) {
            assert (alien != null);
            if (carryAlien) {
                alien.shoot();
            } else {
                alien.shoot();
            }
        }
    }

    @Override
    public void nextAbility() {
        assert (alien != null);
        alien.nextAbility();
    }

    @Override
    public void previousAbility() {
        assert (alien != null);
        alien.previousAbility();
    }

    @Override
    public void useAbility() {
        assert (alien != null);
        if (carryAlien) {
            alien.useAbility();
        } else {
            alien.useAbility();
        }
    }

    @Override
    public void setCursor(int x, int y) {
        assert (alien != null);
        alien.setCursor(x, y);
    }

    @Override
    public void cursorRight(float scale) {
        assert (alien != null);
        alien.cursorRight(scale);
    }

    @Override
    public void cursorLeft(float scale) {
        assert (alien != null);
        alien.cursorLeft(scale);
    }

    @Override
    public void cursorUp(float scale) {
        assert (alien != null);
        alien.cursorUp(scale);
    }

    @Override
    public void cursorDown(float scale) {
        assert (alien != null);
        alien.cursorDown(scale);
    }

    public void teleportAlienback() {
        if (!carryAlien) {
		    
           setParticle(AssetLoader.getInstance().getParticle("teleporter_alien"));
           toggleAlien();
        }
    }

    @Override
    public void toggleAlien() {
        if (!state.equals(PlayerState.dead)) {
            if (carryAlien) {
                if (state == PlayerState.superjump
                        || state == PlayerState.superjump_start
                        || state == PlayerState.superjump_end) {
                    return;
                }
                soundPlayer.playSound(dropAlienSound);
                carryAlien = false;
                alien.setOnPlayer(false);
			
			speed = gameData.astronaut.speed;
			jumpSpeed = gameData.astronaut.jumpSpeed;
			jumpDelayTotal = gameData.astronaut.jumpDelay;
			updateStateAnimation();
		} else {
		    
			carryAlien = true;
			alien.setOnPlayer(true);
			updateStateAnimation();
			speed = gameData.combined.speed;
			jumpSpeed = gameData.combined.jumpSpeed;
			jumpDelayTotal = gameData.combined.jumpDelay;
		    }
		}
	}

    @Override
    public void initPhysics() {
        GameDataInfo info = AssetLoader.getInstance().getGameData();
        createPhysics(BodyType.DYNAMIC, origin.x, origin.y)
                .density(info.combined.density)
                .friction(info.combined.friction).category(PhysixConst.PLAYER)
                .mask(PhysixConst.MASK_PLAYER)
                .asPlayer(info.combined.width, info.combined.height);
    }

    public void gotBiten() {
        restTimeBitenFilter = 1000;
    }

}
