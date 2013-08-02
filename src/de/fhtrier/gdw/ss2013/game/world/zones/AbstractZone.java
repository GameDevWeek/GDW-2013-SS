package de.fhtrier.gdw.ss2013.game.world.zones;

import de.fhtrier.gdw.ss2013.game.EntityCollidable;
import java.awt.Point;
import java.util.List;
import org.jbox2d.dynamics.BodyType;

public abstract class AbstractZone extends EntityCollidable {
    List<Point> points;
	
	public AbstractZone() {
		super();
	}
    
    public void setPoints(List<Point> points) {
        this.points = points;
    }

    @Override
    public void initPhysics() {
        if(points == null) {
            createPhysics(BodyType.STATIC, origin.x, origin.y)
                    .sensor(true).asBox(initialSize.x, initialSize.y);
        } else {
            createPhysics(BodyType.STATIC, 0, 0)
                    .sensor(true).asPolygon(points);
        }
    }
}
