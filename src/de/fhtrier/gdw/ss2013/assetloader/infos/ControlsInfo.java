package de.fhtrier.gdw.ss2013.assetloader.infos;

import de.fhtrier.gdw.commons.jackson.JacksonList;
import java.util.List;

public class ControlsInfo {

    public KeyboardInfo keyboard;
    public MouseInfo mouse;
    @JacksonList(GamepadInfo.class)
    public List<GamepadInfo> gamepads;

    public static class KeyboardInfo {

        public AstronautInfo astronaut;
        public AlienInfo alien;

        public static class AstronautInfo {

            public String MOVE_LEFT;
            public String MOVE_RIGHT;
            public String JUMP;
            public String ACTION;
            public String TOGGLE_ALIEN;
        }

        public static class AlienInfo {

            public String CURSOR_UP;
            public String CURSOR_DOWN;
            public String CURSOR_LEFT;
            public String CURSOR_RIGHT;
            public String SHOOT;
            public String NEXT_ABILITY;
            public String PREV_ABILITY;
            public String USE_ABILITY;
        }
    }

    public static class MouseInfo {

        public AlienInfo alien;

        public static class AlienInfo {

            public String SHOOT;
            public String USE_ABILITY;
            public String NEXT_ABILITY;
            public String PREV_ABILITY;
        }
    }

    public static class GamepadInfo {

        public String name;
        public AstronautInfo astronaut;
        public AlienInfo alien;

        public static class AstronautInfo {

            public String MOVE_LEFT;
            public String MOVE_RIGHT;
            public String JUMP;
            public String ACTION;
            public String TOGGLE_ALIEN;
        }

        public static class AlienInfo {

            public String CURSOR_LEFT;
            public String CURSOR_RIGHT;
            public String CURSOR_UP;
            public String CURSOR_DOWN;
            public String SHOOT;
            public String USE_ABILITY;
            public String NEXT_ABILITY;
            public String PREV_ABILITY;
        }
    }
}
