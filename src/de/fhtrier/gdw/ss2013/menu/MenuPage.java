package de.fhtrier.gdw.ss2013.menu;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.menu.Widget.Align;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu page
 *
 * @author Lusito
 */
public class MenuPage {

    protected Widget focus;
    protected List<Widget> widgets = new ArrayList<>();
    protected MenuPage parent;
    protected GameContainer container;
    private String type;
    MenuManager menuManager;
    protected Image bgImage;
    Color hoverColor = new Color(Integer.parseInt("F8BB08", 16));

    public MenuPage(GameContainer container, StateBasedGame game, MenuManager menuManager, MenuPage parent, String bgImage, String type) throws SlickException {
        this.container = container;

        this.menuManager = menuManager;
        this.parent = parent;
        if (bgImage != null) {
            this.bgImage = AssetLoader.getInstance().getImage(bgImage);
        }
        this.setType(type);
    }

    public void addWidget(Widget w) {
        widgets.add(w);
    }

    public void render(Graphics g) {
        if (bgImage != null) {
            bgImage.draw(0, 0);
        }
        for (Widget w : widgets) {
            if (w.isVisible()) {
                w.render(g);
            }
        }
    }

    public void close() {
        menuManager.setPage(parent);
    }

    public Button addCenteredButton(final String text, float x, float y, Font font, IActionListener listener) throws SlickException {
        float w = font.getWidth(text);
        float h = font.getLineHeight();

        Button button = Button.create(text, x - w / 2, y - h / 2, w, h)
                .font(font)
                .color(Color.gray)
                .hoverColor(hoverColor)
                .pressedColor(Color.cyan)
                .action(listener)
                .useTextSize(true);
        addWidget(button);
        button.init(container);

        return button;
    }

    public Button addLeftAlignedButton(final String text, float x, float y, Font font, IActionListener listener) throws SlickException {
        float w = font.getWidth(text);
        float h = font.getLineHeight();

        Button button = Button.create(text, x, y, w, h)
                .font(font)
                .color(Color.gray)
                .hoverColor(hoverColor)
                .pressedColor(Color.cyan)
                .action(listener)
                .useTextSize(true);
        addWidget(button);
        button.init(container);

        return button;
    }
    
    public Label addCenteredLabel(final Image img, float x, float y) throws SlickException {
        float w = img.getWidth();
        float h = img.getHeight();

        Label label = Label.create("", x - w / 2, y - h / 2, w, h)
                .color(Color.gray);
        label.image(img);
        label.init(container);
        addWidget(label);

        return label;
    }
    
    public Label addCenteredLabel(final String text, float x, float y, Font font) throws SlickException {
        float w = font.getWidth(text);
        float h = font.getLineHeight();

        Label label = Label.create(text, x - w / 2, y - h / 2, w, h)
                .color(Color.gray);
        label.font(font);
        label.init(container);
        addWidget(label);

        return label;
    }
    
    public ToggleButton addLeftAlignedToggleButton(final String[] text, float x, float y, Font font, Align textAlign) throws SlickException {
        float maxW = 0;
        float h = font.getLineHeight();
        
        for (String t : text) {
            if (font.getWidth(t) > maxW)
                maxW = font.getWidth(t);
        }
        
        ToggleButton tb = ToggleButton.create(text, Color.gray, x, y, maxW, h);
        tb.font(font);
        tb.align(textAlign);
        tb.useTextSize(true);
        tb.init(container);
        addWidget(tb);

        return tb;
    }
    
    public ToggleButton addCenteredToggleButton(final String[] text, float x, float y, Font font, Align textAlign) throws SlickException {
        float maxW = 0;
        float h = font.getLineHeight();
        
        for (String t : text) {
            if (font.getWidth(t) > maxW)
                maxW = font.getWidth(t);
        }
        
        ToggleButton tb = ToggleButton.create(text, Color.gray, x - maxW / 2, y - h / 2, maxW, h);
        tb.font(font);
        tb.align(textAlign);
        tb.useTextSize(true);
        tb.init(container);
        addWidget(tb);

        return tb;
    }
    
    public TextField addLeftAlignedTextField(final String text, float x, float y, Font font) throws SlickException {
        float w = font.getWidth(text);
        float h = font.getLineHeight();

        TextField textField = TextField.create(text, x, y, w, h)
                .font(font)
                .color(Color.gray);
        addWidget(textField);
        textField.init(container);

        return textField;
    }
    
    public TextField addCenteredTextField(final String text, float x, float y, Font font) throws SlickException {
        float w = font.getWidth(text);
        float h = font.getLineHeight();

        TextField textField = TextField.create(text, x - w / 2, y - h / 2, w, h)
                .font(font)
                .color(Color.gray);
        addWidget(textField);
        textField.init(container);

        return textField;
    }
    
    public Label addLeftAlignedLabel(final String text, float x, float y, Font font) throws SlickException {
        float w = font.getWidth(text);
        float h = font.getLineHeight();
        
        Label label = Label.create(text, x, y, w, h)
                .font(font)
                .color(Color.gray);
        addWidget(label);
        
        return label;
    }

    public void keyReleased(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            close();
        }

        if (focus != null) {
            focus.keyReleased(key, c);
        }
    }

    public void keyPressed(int key, char c, boolean repeated) {
        if (focus != null) {
            focus.keyPressed(key, c, repeated);
        }
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        for (Widget w : widgets) {
            if (w.isVisible()) {
                w.mouseMoved(oldx, oldy, newx, newy);
            }
        }
    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        for (Widget w : widgets) {
            if (w.isVisible()) {
                w.mouseDragged(oldx, oldy, newx, newy);
            }
        }
    }

    public void mouseReleased(int button, int x, int y) {
        for (Widget w : widgets) {
            if (w.isVisible()) {
                w.mouseReleased(button, x, y);
            }
        }
    }

    public void mousePressed(int button, int x, int y) {
        for (Widget w : widgets) {
            if (w.isVisible()) {
                w.mousePressed(button, x, y);
                if (w.contains(x, y)) {
                    if (focus != null) {
                        focus.setFocus(false);
                    }
                    focus = w;
                    focus.setFocus(true);
                }
            }
        }
    }

    public void activate() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void mouseWheelMoved(int newValue) {
    }
}
