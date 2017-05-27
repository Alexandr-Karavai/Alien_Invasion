package model;

import javax.swing.*;

/**
 * Created by karavai on 24.11.16.
 */
public class GameWin {
    private String player = "/res/win.png";

    private final int START_Y = 200;
    private final int START_X = 220;
    public Sprite spriteWin;
    public int width;

    public GameWin(Sprite spriteWin) {
        this.spriteWin = spriteWin;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(player));

        width = ii.getImage().getWidth(null);

        spriteWin.setX(START_X);
        spriteWin.setY(START_Y);
        spriteWin.setImage(ii.getImage());

    }
}
