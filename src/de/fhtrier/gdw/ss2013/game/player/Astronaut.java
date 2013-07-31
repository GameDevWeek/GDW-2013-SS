
package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.GameDataInfo;
import de.fhtrier.gdw.ss2013.constants.PlayerConstants;
import de.fhtrier.gdw.ss2013.input.AstronautController;
import de.fhtrier.gdw.ss2013.physix.InteractionManager;
import de.fhtrier.gdw.ss2013.physix.PhysixBoxPlayer;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

public class Astronaut extends Player implements AstronautController {

	private float oxygen;
	private float maxOxygen;
	private boolean carryAlien = true;
	Animation bewegungs_ani;
	float speed;
	float jumpSpeed;
	int jumpDelay = 0;
	int jumpDelayTotal;
	// set of entities, which can currently be activated with the action button
	private InteractionManager interactionManager;

	protected PlayerState state;
    private boolean invertAnimation = false;
    private boolean walking;

	public Astronaut () {
		setState(PlayerState.standing);
		maxOxygen = 1000f;
		oxygen = maxOxygen;
        
        
        GameDataInfo info = AssetLoader.getInstance().getGameData();
        speed = info.combined.speed;
        jumpSpeed = info.combined.jumpSpeed;
        jumpDelayTotal = info.combined.jumpDelay;
	}

	public float getOxygen () {
		return oxygen;
	}

	public void setOxygen (float oxygen) {
		this.oxygen = oxygen;
	}

	public float getMaxOxygen () {
		return maxOxygen;
	}

	public void setMaxOxygen (float maxOxygen) {
		this.maxOxygen = maxOxygen;
	}

	public void setJumpSpeed (float newJumpSpeed) {
		jumpSpeed = newJumpSpeed;
	}

	@Override
	public void update (GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
        if(jumpDelay > 0)
            jumpDelay -= delta;
		if (oxygen > 0) this.oxygen -= (this.maxOxygen * PlayerConstants.OXYGEN_PERCENTAGE_LOST_PER_SECOND) * (delta / 1000f);

        boolean grounded = isGrounded();
        if(!grounded) {
            if(getVelocity().y < 0)
                setState(PlayerState.jumping);
            else
                setState(PlayerState.falling);
        }
        else if (!walking) {
			setState(PlayerState.standing);
		}
	}

    public void preInput() {
        walking = false;
    }

	@Override
	public void moveRight () {
		setVelocityX(speed);
		setState(PlayerState.walking);
        invertAnimation = false;
        walking = true;
	}

	@Override
	public void moveLeft () {
		setVelocityX(-speed);
		setState(PlayerState.walking);
        invertAnimation = true;
        walking = true;
	}

	@Override
	public void jump () {
		if (jumpDelay <= 0 && isGrounded()) {
			jumpDelay = 0;
			setVelocityY(-jumpSpeed);
			physicsObject.applyImpulse(new Vector2f(0, -jumpSpeed));
			setState(PlayerState.jumping);
			jumpDelay = jumpDelayTotal;
		}
	}

	@Override
	public void action () {
		getVelocity().y = 2;
		physicsObject.applyImpulse(this.getVelocity());
		setState(PlayerState.action);
		interactionManager.activateAll();

	}

	public boolean isCarryAlien () {
		return carryAlien;
	}

	public void setCarryAlien (boolean carryAlien) {
		this.carryAlien = carryAlien;
	}

	public float getJumpSpeed () {
		return jumpSpeed;
	}

	public PlayerState getState () {
		return state;
	}

	public float getSpeed () {
		return speed;
	}

	public void setSpeed (float newSpeed) {
		speed = newSpeed;
	}

	@Override
	public void render (GameContainer container, Graphics g) throws SlickException {
		Vector2f position = getPosition();

        if (invertAnimation) {
            animation.draw(position.x + animation.getWidth() / 2, position.y - animation.getHeight() / 2, -animation.getWidth(), animation.getHeight());
        } else {
            animation.draw(position.x - animation.getWidth() / 2, position.y - animation.getHeight() / 2);
        }
	}

	public void setState (PlayerState state) {
		if (this.state == null || !this.state.equals(state)) {
			this.state = state;

			AssetLoader al = AssetLoader.getInstance();
			if (isCarryAlien()) {
				setAnimation(al.getAnimation("astronaut_" + state.toString()));
			} else {
				setAnimation(al.getAnimation("player_couple_" + state.toString()));
			}
		}
	}
	
	@Override
    public void setPhysicsObject(PhysixObject physicsObject) {
	    interactionManager = new InteractionManager();
	    physicsObject.addCollisionListener(interactionManager);
	    super.setPhysicsObject(physicsObject);
    }
}
