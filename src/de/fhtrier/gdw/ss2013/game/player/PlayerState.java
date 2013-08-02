
package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;

/** Contains all movement states from the astronaut.
 * @author Robin */
public enum PlayerState {
	standing, walking, jumping, falling, action,superjump_start,superjump,superjump_end;
    
    private Animation animation;
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
    public Animation getAnimation() {
        return animation;
    }
}
