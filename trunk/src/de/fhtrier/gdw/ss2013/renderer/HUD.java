/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class HUD {

    private Progressbar healthbar;
    private Crosshair crosshair;
    private Annotation notation;
    private AbilitySelection abilityWheel;
    private Image test;
    private Quickselect quickselect;
   


    public HUD(GameContainer container) throws SlickException {
        // Init healthbar

        healthbar = new Progressbar();

        final Vector2f position = new Vector2f(10, 10);
        final Vector2f size = new Vector2f(240, 40);
        final int cornerradius = 5;

        final String imagePath = "/res/Dummy_GUIs_Images/";

        try {
            final Image frame = new Image(imagePath + "frame.png");
            final Image background = new Image(imagePath + "background.png");
            final Image bar = new Image(imagePath + "bar.png");
            healthbar
                    .init(position, size, cornerradius, frame, background, bar);
        } catch (SlickException e) {

            e.printStackTrace();
        }

        // Init abilityWheel

        abilityWheel = new AbilitySelection();

        position.set(20, 20);

        try {
            final Image ability1 = new Image(imagePath + "ability1.png");
            final Image ability2 = new Image(imagePath + "ability2.png");
            final Image ability3 = new Image(imagePath + "ability3.png");
            abilityWheel.init(ability1, ability2, ability3, position);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        final Image ability1 = new Image(imagePath + "ability1.png");
        final Image ability2 = new Image(imagePath + "ability2.png");
        final Image ability3 = new Image(imagePath + "ability3.png");
        abilityWheel.init(ability1, ability2, ability3, position);

        
        //Init quickselect
        quickselect = new Quickselect();
        final int selected = 1;
        final int countdown_start = 500;
        quickselect.init(ability1, ability2, ability3,selected,countdown_start);

        
        
        //DEV
        quickselect.setSelected(2);
        

    }

    public void update(GameContainer container, StateBasedGame game, int delta) {

        healthbar.update(container, game, delta);
        quickselect.update(container, game, delta);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        healthbar.render(container, game, g);
        abilityWheel.render(container, game, g);
        quickselect.render(container, game, g);
    }
}
