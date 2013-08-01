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

public class TooltipManager {

    private World worldinstance;
    private EntityManager entityManager;
    private Font font;
    private ArrayList<Tooltip> tooltipList;
    private static TooltipManager instance;
    
    public TooltipManager() {
        tooltipList = new ArrayList<>();
    }
    
    public void init(World worldinstance, Font font) {
        this.font = font;
        this.worldinstance = worldinstance;
        entityManager = worldinstance.getEntityManager();
        Tooltip.setWorld(worldinstance);
        
    }

    public void addTooltip(Vector2f imagePosition, Image image, Vector2f triggerPosition) {        
        this.addTooltip(imagePosition, image, triggerPosition, 50);
    }

    public void addTooltip(Vector2f imagePosition, Image image, Vector2f triggerPosition, float triggerRadius) {        
        tooltipList.add(new Tooltip(imagePosition, image, triggerPosition, triggerRadius));
    }
     
    public void update() {

    }

    public void render() {
        
        drawGeneralTooltip(Switch.class, "Setz Alien hier drauf \n zum Umlegen");
        drawGeneralTooltip(Door.class, "Drücke \"Aktivieren\" zum aktivieren.");
        
          for (Tooltip elem : tooltipList)
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
            Vector2f worldPosition = worldinstance.worldToScreenPosition(entities.get(i).getPosition());
            CenteredText.draw(worldPosition.x, worldPosition.y, string, font);
        }
    }

    
    public static TooltipManager getInstance(){
        
        if (instance == null){
            instance = new TooltipManager();
        }
        
        return instance;
    }
 

}
