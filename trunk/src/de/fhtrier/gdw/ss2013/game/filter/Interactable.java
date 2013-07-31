package de.fhtrier.gdw.ss2013.game.filter;

/**
 * marks entities as being able to be activated and deactivated in any way<br>
 * <br>
 * Forced methods:<br>
 * - activate()<br>
 * - deactivate()<br>
 * 
 * @author BreakingTheHobbit
 *
 */
public interface Interactable extends EntityFilter {
    public void activate();
    public void deactivate();
}
