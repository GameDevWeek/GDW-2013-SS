package de.fhtrier.gdw.ss2013.game.entities;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.*;

public abstract class AbstractEnemy extends Entity{
    private Vector2f velocity;
    private float damage;
    
    public AbstractEnemy() {
        this(new Vector2f(), new Vector2f(), 0);
    }
    public AbstractEnemy(Vector2f pos, Vector2f velo, float dmg) {
        super(pos);
        this.velocity = velo;
        this.damage = dmg;
    }
    public abstract float onCollide();
}
