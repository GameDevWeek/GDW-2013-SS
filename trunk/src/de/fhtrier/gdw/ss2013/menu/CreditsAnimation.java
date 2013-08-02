package de.fhtrier.gdw.ss2013.menu;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.CreditsInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CreditsAnimation extends Widget implements IActionListener {
	private List<Widget> widgets = new ArrayList<>();
	private boolean reset = false;
	private CreditsInfo credits;
	private HashMap<String, ArrayList<PathMover.Destination>> paths = new HashMap<>();
	private HashMap<String, Style> styles = new HashMap<>();
	private int numMovers = 0;

	
	private class Style {
		public ArrayList<PathMover.Destination> path;
		public Color color;
		public Font font;
		public Style(String path, String color, String font) throws SlickException {
			this.path = paths.get(path);
			this.color = new Color(Integer.parseInt(color, 16));
			this.font = AssetLoader.getInstance().getFont(font);
		}
	}
	
	public void addWidget(Widget w) {
		widgets.add(w);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		
	}
	
	@Override
	public void render(Graphics g) {
		for(Widget w: widgets)
			w.render(g);
		
		if(reset) {
			try {
				reset();
			} catch(Exception e) {
				widgets.clear();
			}
		}
	}
	
	public CreditsAnimation() throws SlickException {
        credits = AssetLoader.getInstance().getCredits();
        for(CreditsInfo.Path path: credits.paths) {
            ArrayList<PathMover.Destination> list = new ArrayList<>();
            paths.put(path.name, list);
            for(CreditsInfo.Path.Destination dest: path.destinations)
                list.add(new PathMover.Destination(dest.x, dest.y, dest.speed, dest.delay == null ? 0 : dest.delay));
        }

        for(CreditsInfo.Style style: credits.styles) {
            styles.put(style.name, new Style(style.path, style.color, style.font));
        }
		reset();
	}
	
	public final void reset() {
		reset = false;
		widgets.clear();
		numMovers = 0;
		
		PathMover mover;
		int delay = 0;
		for(CreditsInfo.Text text:  credits.texts) {
			Style style = styles.get(text.style);
			if(style != null) {
				delay += text.delay;
				mover = new PathMover(style.path, false).delay(delay);
				mover.done(this);
				numMovers++;
				
				String str = text.value;
				Label l = Label.create(str, 0, 0, style.font.getWidth(str), 20).font(style.font).color(style.color).align(Align.CENTER).follow(mover);

				addWidget(l);
			}
		}

		AssetLoader al = AssetLoader.getInstance();
		for(CreditsInfo.Animation anim:  credits.animations) {

			if(anim.animation) {
				Animation animation = al.getAnimation(anim.name);
				Animated w = Animated.create(animation, anim.x, anim.y, anim.width, anim.height).angle(anim.angle);
				ArrayList<PathMover.Destination> path = paths.get(anim.path);
				if(path != null) {
					mover = new PathMover(path, anim.oriented).delay(anim.delay);
					mover.done(this);
					numMovers++;
					w.follow(mover);
				}
				addWidget(w);
			} else {
				Image image = al.getImage(anim.name);
				Label w = Label.create("", anim.x, anim.y, anim.width, anim.height)
						.image(image).angle(anim.angle);

				ArrayList<PathMover.Destination> path = paths.get(anim.path);
				if(path != null) {
					mover = new PathMover(path, anim.oriented).delay(anim.delay);
					mover.done(this);
					numMovers++;
					w.follow(mover);
				}
				addWidget(w);
			}
		}
	}
	
	@Override
	public void onAction() {
		numMovers--;
		if(numMovers <= 0) {
			reset = true;
		}
	}
}
