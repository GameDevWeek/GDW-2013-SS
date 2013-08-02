package de.fhtrier.gdw.ss2013.input;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.ControlsInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */
public class Keyboard extends InputDevice {

    private HashMap<InputAction, Integer> keyMap = new HashMap<>();

    public Keyboard(GameContainer gc) {
        super(gc);
        loadSettings();
    }

    @Override
    public void update() {
        // für alle Actions
        Input input = container.getInput();
        Set<Map.Entry<InputAction, Integer>> entries = keyMap.entrySet();
        for (Map.Entry<InputAction, Integer> entry : entries) {
            boolean down;
            switch(entry.getKey()) {
                case JUMP:
                case ACTION:
                case TOGGLE_ALIEN:
                case PREV_ABILITY:
                case NEXT_ABILITY:
                case USE_ABILITY:
                    down = input.isKeyPressed(entry.getValue());
                    break;
                default:
                    down = input.isKeyDown(entry.getValue());
                    break;
            }
            if (down) {
                manager.doAction(entry.getKey());
            }
        }
    }

    private void loadSettings() {

        ControlsInfo controlsInfo = AssetLoader.getInstance().getControls();
        if (controlsInfo.keyboard != null) {
            if (controlsInfo.keyboard.astronaut != null) {
                setKey(InputAction.MOVE_LEFT, controlsInfo.keyboard.astronaut.MOVE_LEFT);
                setKey(InputAction.MOVE_RIGHT, controlsInfo.keyboard.astronaut.MOVE_RIGHT);
                setKey(InputAction.JUMP, controlsInfo.keyboard.astronaut.JUMP);
                setKey(InputAction.ACTION, controlsInfo.keyboard.astronaut.ACTION);
                setKey(InputAction.TOGGLE_ALIEN, controlsInfo.keyboard.astronaut.TOGGLE_ALIEN);
            }

            if (controlsInfo.keyboard.alien != null) {
                setKey(InputAction.SHOOT, controlsInfo.keyboard.alien.SHOOT);
                setKey(InputAction.USE_ABILITY, controlsInfo.keyboard.alien.USE_ABILITY);
                setKey(InputAction.NEXT_ABILITY, controlsInfo.keyboard.alien.NEXT_ABILITY);
                setKey(InputAction.PREV_ABILITY, controlsInfo.keyboard.alien.PREV_ABILITY);
                setKey(InputAction.CURSOR_UP, controlsInfo.keyboard.alien.CURSOR_UP);
                setKey(InputAction.CURSOR_DOWN, controlsInfo.keyboard.alien.CURSOR_DOWN);
                setKey(InputAction.CURSOR_LEFT, controlsInfo.keyboard.alien.CURSOR_LEFT);
                setKey(InputAction.CURSOR_RIGHT, controlsInfo.keyboard.alien.CURSOR_RIGHT);
            }
        }
    }

    private void setKey(InputAction action, String key) {
        keyMap.put(action, org.lwjgl.input.Keyboard.getKeyIndex(key));
    }
}
