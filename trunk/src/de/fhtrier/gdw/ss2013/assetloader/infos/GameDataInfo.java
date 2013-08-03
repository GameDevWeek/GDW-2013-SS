package de.fhtrier.gdw.ss2013.assetloader.infos;

public class GameDataInfo {
    public static class AstronautInfo {
        public Integer width;
        public Integer height;
        public Float density;
        public Float friction;
        public Float speed;
        public Float jumpSpeed;
        public Integer jumpDelay;
        public Float oxygen;
        public Float superJumpSpeed;
        public Float superJumpGravityScale;
    }
    public static class AlienInfo {
        public Integer width;
        public Integer height;
        public Float density;
        public Float friction;
        public Float maxDistance;
    }
    public static class WorldInfo {
        public Float gravity;
        public Float density;
        public Float friction;
    }
    
    public AstronautInfo astronaut;
    public AstronautInfo combined;
    public AlienInfo alien;
    public WorldInfo world;
}
