package de.fhtrier.gdw.ss2013.assetloader.infos;

public class GameDataInfo {
    public static class PlayerInfo {
        public Integer width;
        public Integer height;
        public Float speed;
        public Float jumpSpeed;
        public Integer jumpDelay;
        public Float density;
        public Float friction;
        public Float pickupDistance;
        public Float oxygen;
    }
    public static class WorldInfo {
        public Float gravity;
        public Float density;
        public Float friction;
    }
    
    public PlayerInfo astronaut;
    public PlayerInfo alien;
    public PlayerInfo combined;
    public WorldInfo world;
}
