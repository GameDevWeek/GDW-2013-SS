/**
 * @author Sebastian, Arnold
 */

package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.enemies.AbstractEnemy;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.geom.Vector2f;

/**
 * Player class
 */
public abstract class Player extends EntityCollidable {

	protected Animation animation;
    protected int groundContacts;

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
//        Vector2f position = getPosition();
//		animation.draw(position.x - animation.getWidth() / 2,
//				position.y - animation.getHeight() / 2);
	}
	
	public Animation getAnimation(){
	    return animation;
	}
	
	public void die() {
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
        
        if (physicsObject == objectA && a.m_shape.getType() == ShapeType.CIRCLE) {
            groundContacts++;
        }
        else if (physicsObject == objectB && b.m_shape.getType() == ShapeType.CIRCLE) {
            groundContacts++;
        }
        
        AbstractEnemy damageDealer = null;
        Player damageTaker = null;
        if (objectA.getOwner() instanceof AbstractEnemy && objectB.getOwner() instanceof Player) {
            damageTaker = (Player) objectB.getOwner();
            damageDealer = (AbstractEnemy) objectA.getOwner();
        }
        else if (objectB.getOwner() instanceof AbstractEnemy && objectA.getOwner() instanceof Player) {
            damageTaker = (Player) objectA.getOwner();
            damageDealer = (AbstractEnemy) objectB.getOwner();
        }
        
        if(damageDealer != null && damageTaker != null) {
            // is above TODO(check for astronaut later..)
            Vector2f damageTakerPos = damageTaker.getPosition();
            Vector2f damageTakerDim = damageTaker.getPhysicsObject().getDimension();

            Vector2f damageDealerPos = damageDealer.getPosition();
            Vector2f damageDealerDim = damageDealer.getPhysicsObject().getDimension();

            if( (damageTakerPos.x + damageTakerDim.x > damageDealerPos.x - damageDealerDim.x  ) //  
                    && ((damageTakerPos.x - damageTakerDim.x < damageDealerPos.x + damageDealerDim.x  ))
                    && (damageTakerPos.y + damageTakerDim.y < damageDealerPos.y)) { // player deals damage
                System.out.println(damageDealer+" hit by "+damageTaker);
            }
            else {
            	if (damageTaker instanceof Astronaut)
            		((Astronaut) damageTaker).setOxygen(0);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        PhysixObject objectA = (PhysixObject) a.getBody().getUserData();
        PhysixObject objectB = (PhysixObject) b.getBody().getUserData();
        if (physicsObject == objectA && a.m_shape.getType() == ShapeType.CIRCLE) {
            groundContacts--;
        }
        else if (physicsObject == objectB && b.m_shape.getType() == ShapeType.CIRCLE) {
            groundContacts--;
        }
        assert(groundContacts >= 0);
    }
}
