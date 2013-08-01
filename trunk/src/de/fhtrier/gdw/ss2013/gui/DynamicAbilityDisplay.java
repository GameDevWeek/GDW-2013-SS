package de.fhtrier.gdw.ss2013.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class DynamicAbilityDisplay extends AbilityDisplay {
    /*gegeben durch AbilityDisplay ist schon:
     * AbilityDisplay
     * setActivated(boolean activated)
     * render(GameContainer container, StateBasedGame game, Graphics g)
     */
    
    public DynamicAbilityDisplay() {
        // TODO Auto-generated constructor stub
    }
        private int selected = 1;
        private int countdown_start = 500;
        private int countdown_timer = 0;
        private World worldinstance;
                        
        public void init(Image image,
                int selected, int countdown_start, World worldinstance) {

            this.selected = selected;
            this.countdown_start = countdown_start;
            
            this.worldinstance = worldinstance;

        }

       /* public void render(GameContainer container, StateBasedGame game, Graphics g) {
            
            
            if (countdown_timer > 0) {
                Vector2f p = worldinstance.worldToScreenPosition(worldinstance.getAstronaut().getPosition());
                ability[selected - 1].draw(p.x   - ability[selected-1].getWidth()/2
                                           ,p.y - worldinstance.getAstronaut().getAnimation().getHeight()/2);
            }
        }*/

        public void update(GameContainer container, StateBasedGame game, int delta) {
            if (countdown_timer > 0) {
                countdown_timer -= delta;
            }
        
            setSelected(worldinstance.getAlien().getselectedAbility());
        }

        public void setSelected(int selected) {
            if (this.selected != selected) {
                this.selected = selected;
                countdown_timer = countdown_start; // counter zurücksetzen

            }

        }

    

}
