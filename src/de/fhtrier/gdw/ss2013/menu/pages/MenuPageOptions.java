package de.fhtrier.gdw.ss2013.menu.pages;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.IUpdateListener;
import de.fhtrier.gdw.ss2013.menu.Label;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.Slider;
import de.fhtrier.gdw.ss2013.menu.ToggleButton;
import de.fhtrier.gdw.ss2013.menu.Widget.Align;

public class MenuPageOptions extends MenuPage {

    public MenuPageOptions(final GameContainer container, StateBasedGame game,
            MenuManager menuManager, MenuPage parent, String bgImage) throws SlickException {
        super(container, game, menuManager, parent, bgImage, "options");
        
        Font font = AssetLoader.getInstance().getFont("verdana_46");
        
        float xCenter = MenuManager.MENU_WIDTH / 2.0f;
        float yCenter = MenuManager.MENU_HEIGHT / 2.0f;
        
        float hText = font.getLineHeight() * 1.5f;
        float textHeight = font.getLineHeight();
                
        Label volumeLabel = addLeftAlignedLabel("Lautstärke", 25, yCenter * 0.25f , font);
        
        float volumeLabelLength = font.getWidth(volumeLabel.text);
        Slider volumeSlider = Slider.create(0.5f, true, volumeLabelLength + 50, yCenter * 0.25f + 7, 200, textHeight);
        volumeSlider.thumbImage(AssetLoader.getInstance().getImage("slider_thumb"));
        
        Rectangle vsRect = volumeSlider.getRect();
        int valueSound = Math.round(volumeSlider.value * 100);
        final Label volumeValue = addLeftAlignedLabel(valueSound + "%", vsRect.getX() + vsRect.getWidth() + 50, yCenter * 0.25f, font);
        
        volumeSlider.update(new IUpdateListener() {
            
            @Override
            public void onUpdate(Object value) {
                float soundValue = ((Float)value).floatValue();
                int valueShow = Math.round(soundValue * 100);
                volumeValue.text(valueShow + " %");
                
                // ToDo: force soundValue to Sound-Object
            }
        });
        
        addWidget(volumeSlider);
        
        String []fsTexts = {"Zum Vollbildmodus wechseln", "Zum Fenstermodus Wechseln"};
        ToggleButton fullscreenToggle = ToggleButton.create(fsTexts, Color.gray, 25, yCenter *0.25f + (hText * 1), 510, textHeight).align(Align.LEFT)
                        .font(font).state(container.isFullscreen() ? 1 : 0).hoverColor(Color.white);
       
        fullscreenToggle.update(new IUpdateListener() {
               
                @Override
                public void onUpdate(Object value) {
                        boolean fullscreen = ((Integer)value).intValue() == 1;
                if(fullscreen != container.isFullscreen()) {
                        try {
                                if(container.isFullscreen())
                                        ((AppGameContainer)container).setDisplayMode(MainGame.WINDOW_WIDTH, MainGame.WINDOW_HEIGHT, false);
                                else
                                        ((AppGameContainer)container).setDisplayMode(container.getScreenWidth(), container.getScreenHeight(), true);
                        } catch(SlickException e) {
                                e.printStackTrace();
                        }
                }
                }
        });

        addWidget(fullscreenToggle);
        
        addLeftAlignedButton("Steuerung", 25, yCenter * 0.25f + (hText * 3), font, 
                new IActionListener() {
                
                @Override
                public void onAction() {
                    // TODO Auto-generated method stub
                    
                }
        });
        
        addCenteredButton("zurück", xCenter, MenuManager.MENU_HEIGHT - 1.5f * textHeight, font, 
                new IActionListener() { 
                    @Override
                    public void onAction() {
                        close();
                    }
                }
        );
    }

}
