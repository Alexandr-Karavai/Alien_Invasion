package model;

import javax.swing.*;

public class Shot  {


    private String shot = "/res/missile.png";
    //private String shot = "/res/alien-shot.png";
    private final int H_SPACE = 20;
    private final int V_SPACE = 5;

    public Sprite spriteShot;

    public Shot(Sprite spriteShot) {
        this.spriteShot = spriteShot;

    }

    public Shot(Sprite spriteShot,int x, int y) {
        this.spriteShot = spriteShot;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        spriteShot.setImage(ii.getImage());
        spriteShot.setX(x + H_SPACE);
        spriteShot.setY(y - V_SPACE);
    }
}