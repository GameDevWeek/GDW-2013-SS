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
import de.fhtrier.gdw.ss2013.assetloader.infos.SettingsInfo;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.IUpdateListener;
import de.fhtrier.gdw.ss2013.menu.Label;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.Slider;
import de.fhtrier.gdw.ss2013.menu.ToggleButton;
import de.fhtrier.gdw.ss2013.menu.Widget.Align;

public class MenuPageOptions extends MenuPage {
    
    private ToggleButton fullscreenToggle;
    
    public MenuPageOptions(final GameContainer container, StateBasedGame game,
            MenuManager menuManager, MenuPage parent, String bgImage) throws SlickException {
        super(container, game, menuManager, parent, bgImage, "options");
        
        SettingsInfo settings = AssetLoader.getInstance().getSettings();
        Font font = AssetLoader.getInstance().getFont("jabjai_heavy");
        
        float xCenter = MenuManager.MENU_WIDTH / 2.0f;
        float yCenter = MenuManager.MENU_HEIGHT / 2.0f;
        
        float hText = font.getLineHeight() * 1.5f;
                
        Label volumeLabel = addLeftAlignedLabel("Lautstaerke", 25, yCenter * 0.25f , font);
        
        float volumeLabelLength = font.getWidth(volumeLabel.text);
        Slider volumeSlider = Slider.create(0.5f, true, volumeLabelLength + 50, yCenter * 0.25f - 5, 200, font.getLineHeight());
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
        this.fullscreenToggle = ToggleButton.create(fsTexts, this.standardColor, 25, yCenter *0.25f + (hText * 1), 510, font.getLineHeight()).align(Align.LEFT)
                        .font(font).state(container.isFullscreen() ? 1 : 0).hoverColor(this.hoverColor);
       
        fullscreenToggle.update(new IUpdateListener() {
               
                @Override
                public void onUpdate(Object value) {
                    boolean fullscreen = ((Integer)value).intValue() == 1;
                    if(fullscreen != container.isFullscreen()) {
                        MainGame.toggleFullscreen();
                    }
                }
        });

        addWidget(fullscreenToggle);
        
        addLeftAlignedButton("Steuerung", 25, yCenter * 0.25f + (hText * 3), font, 
                new IActionListener() {
                
                @Override
                public void onAction() {
                    
                }
        });
        
        addCenteredButton("zurueck", xCenter, MenuManager.MENU_HEIGHT - 1.5f * font.getLineHeight(), font, 
                new IActionListener() { 
                    @Override
                    public void onAction() {
                        close();
                    }
                }
        );
    }
    
    @Override
    public void activate() {
        super.activate();
        this.fullscreenToggle.state(this.container.isFullscreen() ? 1 : 0);
    }

}
