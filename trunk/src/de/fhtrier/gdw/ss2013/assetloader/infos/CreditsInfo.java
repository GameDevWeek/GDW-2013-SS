
package de.fhtrier.gdw.ss2013.assetloader.infos;

import de.fhtrier.gdw.commons.jackson.JacksonList;
import java.util.List;

public class CreditsInfo {
    @JacksonList(Path.class)
    public List<Path> paths;
    @JacksonList(Style.class)
    public List<Style> styles;
    @JacksonList(Text.class)
    public List<Text> texts;
    @JacksonList(Animation.class)
    public List<Animation> animations;

    public static class Path {
        public String name;
        @JacksonList(Destination.class)
        public List<Destination> destinations;
        
        public static class Destination {
            public Integer x;
            public Integer y;
            public Integer speed;
            public Integer delay;
        }
    }
    
    public static class Style {
        public String name;
        public String path;
        public String color;
        public String font;
    }

    public static class Text {
        public String style;
        public String title;
        public Integer delay;
        public String value;
    }

    public static class Animation {
        public String name;
        public Integer x;
        public Integer y;
        public Integer width;
        public Integer height;
        public String path;
        public Integer angle;
        public Integer delay;
        public Boolean oriented;
        public Boolean animation;
    }
}