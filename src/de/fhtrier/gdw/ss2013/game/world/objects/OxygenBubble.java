/**
 * OxygenBubble class
 * @author Justin, Sandra
 * 
 * erzeugt Sauerstoffblasen und soll Sauerstoffvorrat erhöhen
 * 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixConst;
import de.fhtrier.gdw.ss2013.physix.PhysixShape;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.SoundPlayer;

//import org.newdawn.slick.Image;

public class OxygenBubble extends EntityCollidable implements Interactable {

	private float oxygenLevel = 50;
	private OxygenFlower flower;
	// private AssetLoader a = AssetLoader.getInstance();
	// private Image img = a.getImage("bubble");
	private float speed = 100;
	private int timer = 0;
	private boolean isUsed = false;
	private final Vector2f initalDirection = new Vector2f();

	public OxygenBubble () {
		super(AssetLoader.getInstance().getImage("bubble"));
		this.oxygenLevel = getOxygenLevel();

	}

	@Override
	protected void initialize () {
		super.initialize();
		timer = (int)(Math.random() * 100);
      soundPlayer = SoundLocator.getPlayer();
//    pickUp = SoundLocator.loadSound("")
	}

	@Override
	public void initPhysics () {
		createPhysics(BodyType.KINEMATIC, origin.x, origin.y).sensor(true).category(PhysixConst.ITEM).mask(PhysixConst.MASK_ITEM)
			.asCircle((img.getWidth() / 2 + img.getHeight() / 2) / 4);
	}

	@Override
	public void beginContact (Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		PhysixShape objectA = (PhysixShape)a.getBody().getUserData();
		PhysixShape objectB = (PhysixShape)b.getBody().getUserData();
		Entity e = objectA.getOwner();
		if (!(e instanceof Astronaut)) {
			e = objectB.getOwner();
		}

		if (e instanceof Astronaut) {
			Astronaut astro = (Astronaut)e;
			if (astro.getOxygen() + oxygenLevel < astro.getMaxOxygen()) {
				useBubble();
			}

		}
	}
    private SoundPlayer soundPlayer;
    private Sound pickUp;

	@Override
	public void render (GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		super.render(container, g);
		if (World.getInstance().debugDraw) {
			float directPickupDistance = 150;
			g.setColor(Color.red);
			g.drawOval(getPosition().x - directPickupDistance, getPosition().y - directPickupDistance, directPickupDistance * 2, directPickupDistance * 2);

			Astronaut astro = World.getInstance().getAstronaut();
			if (this.getPosition().distance(astro.getPosition()) <= directPickupDistance) {
				g.setColor(Color.yellow);
			} else{
				g.setColor(Color.red);
			}
			g.drawLine(getPosition().x, getPosition().y, astro.getPosition().x, astro.getPosition().y);
		}
	}

	public void useBubble () {
		Astronaut astro = World.getInstance().getAstronaut();
		astro.setOxygen((astro.getOxygen() + oxygenLevel));
		isUsed = true;
		flower.decreaseBubbleCount();
	}

	@Override
	public void endContact (Contact object) {

	}

	public void setInitalDirection (float x, float y) {
		this.initalDirection.set(x, y);
	}

	public float getOxygenLevel () {
		return oxygenLevel;
	}

	private int deltamod = 1;

	@Override
	public void update (GameContainer container, int delta) throws SlickException {
		super.update(container, delta);

		if (!isUsed) {
			timer += delta;
			if (timer >= 1000) {
				speed *= -1;
				timer -= 1000;
			}
			setVelocity(0, -speed * (delta / 1000.f));
		}else {
			World.getInstance().getEntityManager().removeEntity(this);
		}
	}

	public OxygenFlower getFlower () {
		return flower;
	}

	public void setFlower (OxygenFlower flower) {
		this.flower = flower;
	}

	public void setOxygenLevel (float oxygenLevel) {
		this.oxygenLevel = oxygenLevel;
	}

	public void setReferences (OxygenFlower flower) {
		this.flower = flower;
	}

	@Override
	public boolean isActive () {
		return false;
	}

	@Override
	public void activate () {

	}

	@Override
	public void deactivate () {

	}
}
