package de.fhtrier.gdw.ss2013.physix;

public class PhysixConst {
    public static final short PLAYER = 1;
    public static final short ENEMY = 2;
    public static final short BULLET_PLAYER = 4;
    public static final short BULLET_ENEMY = 8;
    public static final short ITEM = 16;
    
    public static final short MASK_PLAYER = PLAYER | ENEMY | BULLET_ENEMY | ITEM;
    public static final short MASK_ENEMY = PLAYER | BULLET_PLAYER;
    public static final short MASK_BULLET_ENEMY = PLAYER;
    public static final short MASK_BULLET_PLAYER = ENEMY;
    public static final short MASK_ITEM = PLAYER;

}
