package de.fhtrier.gdw.ss2013.game.player;

import org.newdawn.slick.Animation;

/**
 * Contains all movement states from the astronaut.
 *
 * @author Robin
 */
public enum PlayerState {

    standing, walking, jumping, falling, action, superjump_start, superjump, superjump_end;
    private Animation combinedAnimation;
    private Animation astronautAnimation;

    public void setAnimations(Animation combinedAnimation, Animation astronautAnimation) {
        this.combinedAnimation = combinedAnimation;
        this.astronautAnimation = astronautAnimation;
    }

    public Animation getCombinedAnimation() {
        return combinedAnimation;
    }

    public Animation getAstronautAnimation() {
        return astronautAnimation;
    }
}
