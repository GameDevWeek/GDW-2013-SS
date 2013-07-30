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



public class Annotation {
    
    private World worldinstance;
    private EntityManager entityManager;
    private Font font;
    
	public Annotation() {

	}

	public void init(World worldinstance, Font font)
	{
	    this.font=font;
	    this.worldinstance = worldinstance;
	    entityManager = worldinstance.getEntityManager();
	  
	    //dev
	    entityManager.createEntityAt(Switch.class, new Vector2f(100.f,100.f));
	    //dev
	}
	
	public void update() {

	    
	}

	public void render() {
	    drawTooltip(Switch.class,"Setz Alien hier drauf \n zum Umlegen");
	    drawTooltip(Door.class,"Drücke \"Aktivieren\" zum aktivieren.");	    
	}

    private void drawTooltip(Class<? extends EntityFilter> filter, String string) {
        
        ArrayList<Entity> entities = entityManager.getClosestEntitiesByFilter(worldinstance.getAstronaut().getPosition(), 100, filter);
                                
	    //annotation rendern
	    for (int i = 0; i < entities.size(); i++){
	        CenteredText.draw(entities.get(i).getPosition().x, entities.get(i).getPosition().y, string, font);
	    }
    }

}
