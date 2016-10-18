package model;

import javax.swing.*;

public class BossAlien {

    public Sprite bossSprite;
    public static final int BOSS_X = 50;
    public static final int BOSS_Y = 10;

    private final String shot = "/res/boss.png";

    public BossAlien(Sprite bossSprite) {
        this.bossSprite = bossSprite;
        bossSprite.setX(BOSS_X);
        bossSprite.setY(BOSS_Y);

        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        bossSprite.setImage(ii.getImage());
    }

    public void act(int direction) {
        this.bossSprite.x += direction;
    }
}
