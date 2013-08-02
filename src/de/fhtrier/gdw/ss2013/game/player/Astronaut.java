package de.fhtrier.gdw.ss2013.game.player;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

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
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public class Astronaut extends EntityCollidable implements AstronautController, AlienController {

    private float oxygen;
    private float maxOxygen;
    private boolean carryAlien;
    private Animation bewegungs_ani;
    private float speed;
    private float jumpSpeed;
    private int jumpDelay = 0;
    private int jumpDelayTotal;
    // set of entities, which can currently be activated with the action button
    private InteractionManager interactionManager;

    private ArrayList<Switch> switches= new ArrayList<>();

    private PlayerState state;
    private boolean invertAnimation;
    private boolean walking;
    private Alien alien;
    private float pickupDistance;
    private GameDataInfo gameData;
    private Animation animation;
    private int groundContacts;
	private int superjumpDelay = 0;
	private boolean takeOff=true;
	private PlayerState oldState = PlayerState.standing;


    public Astronaut() {
        GameDataInfo info = AssetLoader.getInstance().getGameData();
        speed = info.combined.speed;
        jumpSpeed = info.combined.jumpSpeed;
        jumpDelayTotal = info.combined.jumpDelay;
        maxOxygen = 1000f;
        oxygen = maxOxygen;
        carryAlien = true;
        invertAnimation = false;
	    setState(PlayerState.standing);
    }

    public void setAlien(Alien alien) {
        this.alien = alien;
        carryAlien = true;
        alien.setOnPlayer(true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        super.initialize();
        gameData = AssetLoader.getInstance().getGameData();
        speed = gameData.combined.speed;
        jumpSpeed = gameData.combined.jumpSpeed;
        jumpDelayTotal = gameData.combined.jumpDelay;
        pickupDistance = gameData.astronaut.pickupDistance;
        maxOxygen = 1000f;
        oxygen = maxOxygen;
        carryAlien = true;
        invertAnimation = false;
        setState(PlayerState.standing);
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

	@Override
	public void update (GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		if (jumpDelay > 0) jumpDelay -= delta;
		if (oxygen > 0)
			this.oxygen -= (this.maxOxygen * PlayerConstants.OXYGEN_PERCENTAGE_LOST_PER_SECOND) * (delta / 1000f);
		else
			die();

// boolean grounded = isGrounded();
// if (!grounded) {
// if (getVelocity().y < 0)
// setState(PlayerState.jumping);
// else
// setState(PlayerState.falling);
// } else if (!walking) {
// setState(PlayerState.standing);
// }
		if (getVelocity().x == 0 && getVelocity().y == 0) {
			setState(PlayerState.standing);
		}

		switch (state) {
		case jumping:
			if (getVelocity().y > 0) setState(PlayerState.falling);
			break;
		case superjump:
			if(isGrounded() && takeOff){
				takeOff=false;
				animation = AssetLoader.getInstance().getAnimation("astronaut_superjump_start");
				
				Log.debug("superjump start");
			}else if (isGrounded() && !takeOff) {
				animation = AssetLoader.getInstance().getAnimation("astronaut_superjump_end");
				takeOff=true;
				Log.debug("superjump end");
			}else {
				animation = AssetLoader.getInstance().getAnimation("astronaut_superjump");
				Log.debug("superjump mid");
			}
			break;
		case falling:
			if(isGrounded()){
				setState(PlayerState.standing);
			}
			break;
		default:
			break;

		}
		if (!oldState.equals(state)) Log.debug(state.toString());
		oldState = state;
	}

    public void preInput() {
        walking = false;
    }

    @Override
    public void moveRight() {
        setVelocityX(speed);
        setState(PlayerState.walking);
        invertAnimation = false;
        walking = true;
    }

    @Override
    public void moveLeft() {
        setVelocityX(-speed);
		if (!(state.equals(PlayerState.superjump) || state.equals(PlayerState.jumping)|| state.equals(PlayerState.falling))) {
            setState(PlayerState.walking);
		}
        invertAnimation = true;
        walking = true;
    }

    @Override
    public void jump() {
        if (jumpDelay <= 0 && isGrounded()) {
            jumpDelay = 0;
            setVelocityY(-jumpSpeed);
            physicsObject.applyImpulse(new Vector2f(0, -jumpSpeed));
            setState(PlayerState.jumping);
            jumpDelay = jumpDelayTotal;
        }
    }

	public void superjump () {
		if (superjumpDelay <= 0 && isGrounded()) {
			jumpDelay = 0;
			setVelocityY(-jumpSpeed * 2);
			physicsObject.applyImpulse(new Vector2f(0, -jumpSpeed * 2));
			setState(PlayerState.superjump);
			jumpDelay = jumpDelayTotal;
		}
	}

    /**
     * activate all activatable Entities in range
     * 
     * @see InteractionManager
     */
    @Override
    public void action() {
        // getVelocity().y = 2;
        // physicsObject.applyImpulse(this.getVelocity());
        //
        // setState(PlayerState.action);
        // if (interactionManager != null) {
        // interactionManager.activateAll();
        // } else {
        // System.err
        // .println("No InteractionManager registered to Astronaut!");
        // }
   	 interactionManager.activateAll();
        for (Switch s : switches) {
            s.turnSwitch();
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
	public void render (GameContainer container, Graphics g) throws SlickException {
        Vector2f position = getPosition();

        if (invertAnimation) {
            animation.draw(position.x + animation.getWidth() / 2, position.y
                    - animation.getHeight() / 2, -animation.getWidth(),
                    animation.getHeight());
        } else {
            animation.draw(position.x - animation.getWidth() / 2, position.y
                    - animation.getHeight() / 2);
        }
    }

    public void setState(PlayerState state) {
        if (this.state == null || !this.state.equals(state)) {
            this.state = state;
            updateStateAnimation();
        }
    }
    
    private void updateStateAnimation() {
        AssetLoader al = AssetLoader.getInstance();
        if (isCarryAlien()) {
            setAnimation(al.getAnimation("player_couple_"
                    + state.toString()));
        } else {
            setAnimation(al.getAnimation("astronaut_" + state.toString()));
        }
    }

    // overriding because the InteractionManager has to be added to the
    // PhysixObject
    // and the Astronaut needs to know its InteractionManager, so it is done
    // here
    @Override
    public void setPhysicsObject(PhysixObject physicsObject) {
        interactionManager = new InteractionManager();
        physicsObject.addCollisionListener(interactionManager);
        super.setPhysicsObject(physicsObject);
    }

    public Animation getAnimation(){
        return animation;
    }
    
    public void die() {
        if (!Cheats.isGodmode)
            World.getInstance().shallBeReseted(true);
    }
    
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public boolean isGrounded() {
        return groundContacts > 0; 
    }
    
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
        PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
        
        if (physicsObject == objectA && a.m_shape.getType() == ShapeType.CIRCLE && !b.m_isSensor) {
            groundContacts++;
        }
        else if (physicsObject == objectB && b.m_shape.getType() == ShapeType.CIRCLE && !a.m_isSensor) {
            groundContacts++;
        }
        
        Entity other = getOtherEntity(contact);
        if (other instanceof AbstractEnemy) {
            AbstractEnemy damageDealer = (AbstractEnemy) other;
            // is above TODO(check for astronaut later..)
            Vector2f damageTakerPos = getPosition();
            Vector2f damageTakerDim = getPhysicsObject().getDimension();

            Vector2f damageDealerPos = damageDealer.getPosition();
            Vector2f damageDealerDim = damageDealer.getPhysicsObject().getDimension();

            if( (damageTakerPos.x + damageTakerDim.x > damageDealerPos.x - damageDealerDim.x  ) //  
                    && ((damageTakerPos.x - damageTakerDim.x < damageDealerPos.x + damageDealerDim.x  ))
                    && (damageTakerPos.y + damageTakerDim.y < damageDealerPos.y)) { // player deals damage
                System.out.println(this+" hit by "+damageDealer);
            }
            else {
            	// Wird in Bullet-Klassen geregelt
//                if (damageTaker instanceof Astronaut && !(damageDealer instanceof PlayerBullet))
//                    ((Astronaut) damageTaker).setOxygen(0);
                
            }
        }

        if (objectA.getOwner() instanceof Switch) {
            switches.add((Switch) objectA.getOwner());
        }
        if (objectB.getOwner() instanceof Switch) {
            switches.add((Switch) objectA.getOwner());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
        PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
        if (physicsObject == objectA && a.m_shape.getType() == ShapeType.CIRCLE && !b.m_isSensor) {
            groundContacts--;
        }
        else if (physicsObject == objectB && b.m_shape.getType() == ShapeType.CIRCLE && !a.m_isSensor) {
            groundContacts--;
        }
        assert(groundContacts >= 0);
        
        if (objectA.getOwner() instanceof Switch) {
            switches.remove((Switch) objectA.getOwner());
        }
        if (objectB.getOwner() instanceof Switch) {
            switches.remove((Switch) objectA.getOwner());
        }
    }

    @Override
    public void shoot() {
        assert(alien != null);
        if(carryAlien) {
            //
        } else {
            alien.shoot();
        }
    }

    @Override
    public void nextAbility() {
        assert(alien != null);
        alien.nextAbility();
    }

    @Override
    public void previousAbility() {
        assert(alien != null);
        alien.previousAbility();
    }

    @Override
    public void useAbility() {
        assert(alien != null);
        if(carryAlien) {
            alien.useAbility();
        } else {
            alien.useAbility();
        }
    }

    @Override
    public void setCursor(int x, int y) {
        assert(alien != null);
        alien.setCursor(x, y);
    }

    @Override
    public void cursorRight(float scale) {
        assert(alien != null);
        alien.cursorRight(scale);
    }

    @Override
    public void cursorLeft(float scale) {
        assert(alien != null);
        alien.cursorLeft(scale);
    }

    @Override
    public void cursorUp(float scale) {
        assert(alien != null);
        alien.cursorUp(scale);
    }

    @Override
    public void cursorDown(float scale) {
        assert(alien != null);
        alien.cursorDown(scale);
    }
    
    @Override
    public void toggleAlien() {
        if(carryAlien) {
            carryAlien = false;
            alien.setOnPlayer(false);

            speed = gameData.astronaut.speed;
            jumpSpeed = gameData.astronaut.jumpSpeed;
            jumpDelayTotal = gameData.astronaut.jumpDelay;
            updateStateAnimation();
        } else if(getPosition().distance(alien.getPosition()) <= pickupDistance) {
            carryAlien = true;
            alien.setOnPlayer(true);
            updateStateAnimation();
            
            speed = gameData.combined.speed;
            jumpSpeed = gameData.combined.jumpSpeed;
            jumpDelayTotal = gameData.combined.jumpDelay;
        }
    }
}
