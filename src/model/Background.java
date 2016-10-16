package model;

import javax.swing.*;

public class Background {

    private String player = "/res/fon.png";

    private final int START_Y = 0;
    private final int START_X = 0;
    public Sprite spriteBack;
    private int width;


    public Background(Sprite spriteBack) {
        this.spriteBack = spriteBack;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(player));

        width = ii.getImage().getWidth(null);

        spriteBack.setX(START_X);
        spriteBack.setY(START_Y);
        spriteBack.setImage(ii.getImage());

    }
}
