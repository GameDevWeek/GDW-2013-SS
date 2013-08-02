package de.fhtrier.gdw.ss2013.menu.pages;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.menu.CreditsAnimation;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
;

/**
 * Menu page: Credits
 */
public class MenuPageCredits extends MenuPage {
	CreditsAnimation animation;
	
	public MenuPageCredits(final GameContainer container, final StateBasedGame game, final MenuManager menuManager, MenuPage parent)
			throws SlickException {
		super(container, game, menuManager, parent, "bg_credits", "credits");

		Font font = AssetLoader.getInstance().getFont("jabjai_heavy");

		animation = new CreditsAnimation();
		addWidget(animation);
		
		addCenteredButton("Zurueck", 943, 680, font,
				new IActionListener() {
                    @Override
					public void onAction() {
						close();
					}
				});
	}
	
	@Override
	public void activate() {
		animation.reset();
	}
}
