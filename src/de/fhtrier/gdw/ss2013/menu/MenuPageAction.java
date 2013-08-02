package de.fhtrier.gdw.ss2013.menu;


/**
 * Menu page action (switch to another page on an action)
 * 
 * @author Lusito
 */
public class MenuPageAction implements IActionListener {
	MenuManager menuManager;
	private MenuPage page;
	
	public MenuPageAction(MenuManager menuManager, MenuPage page) {
		this.menuManager = menuManager;
		this.page = page;
	}
	
    @Override
	public void onAction() {
		menuManager.setPage(page);
	}
}