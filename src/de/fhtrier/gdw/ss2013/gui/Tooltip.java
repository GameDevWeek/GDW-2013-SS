/**
 * Boris, David (UI-Team)
 */
package de.fhtrier.gdw.ss2013.gui;

import java.util.ArrayList;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.filter.EntityFilter;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.game.world.objects.Door;
import de.fhtrier.gdw.ss2013.game.world.objects.Switch;
import de.fhtrier.gdw.ss2013.gui.utils.CenteredText;
import de.fhtrier.gdw.ss2013.math.MathConstants;

public class Tooltip {

    private World worldinstance;
    private EntityManager entityManager;
    private Font font;
    private ArrayList<Specifictooltip> specificTooltipList;
    
    public Tooltip() {

    }

    public void init(World worldinstance, Font font) {
        this.font = font;
        this.worldinstance = worldinstance;
        entityManager = worldinstance.getEntityManager();
        Specifictooltip.setWorld(worldinstance);
        specificTooltipList = new ArrayList<>();
        
  
    }

    public void addSpecificTooltip(Vector2f position, Image img, Vector2f trigger, float triggerRadius)
    {
        
        specificTooltipList.add(new Specifictooltip(img, position, trigger, triggerRadius));
    }
     
    public void update() {

    }

    public void render() {
        
        drawGeneralTooltip(Switch.class, "Setz Alien hier drauf \n zum Umlegen");
        drawGeneralTooltip(Door.class, "Drücke \"Aktivieren\" zum aktivieren.");
        
          for (Specifictooltip elem : specificTooltipList)
          {
              if (elem.getTrigger().distance(worldinstance.getAstronaut().getPosition()) - elem.getRadius() < MathConstants.EPSILON_F){ 
                  elem.render();
              }
          }
         
    }

    private void drawGeneralTooltip(Class<? extends EntityFilter> filter, String string) {

        ArrayList<Entity> entities = entityManager.getClosestEntitiesByFilter(worldinstance.getAstronaut()
                .getPosition(), 100, filter);

        // annotation rendern
        for (int i = 0; i < entities.size(); i++) {
            CenteredText.draw(entities.get(i).getPosition().x, entities.get(i).getPosition().y, string, font);
        }
    }
 

}
