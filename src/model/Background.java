package model;

import javax.swing.*;

/**
 * Created by Александр on 09.10.2016.
 */
public class Background {

    private String player = "/res/space.png";

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
