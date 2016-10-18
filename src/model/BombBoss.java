package model;

import javax.swing.*;

public class BombBoss {
    private String bomb = "/res/plasma.png";
    private final int H_SPACE = 70;
    private final int V_SPACE = -40;

    public Sprite spriteBomb;

    public BombBoss(Sprite spriteBomb) {this.spriteBomb = spriteBomb;}

    public BombBoss(Sprite spriteBomb,int x, int y) {
        this.spriteBomb = spriteBomb;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(bomb));
        spriteBomb.setImage(ii.getImage());
        spriteBomb.setX(x + H_SPACE);
        spriteBomb.setY(y - V_SPACE);
    }
}
