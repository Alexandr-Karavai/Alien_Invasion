package model;

import javax.swing.*;

public class GameOver {

    private String stringLose = "/res/gameOver2.png";

    private final int START_Y = 200;
    private final int START_X = 160;
    public Sprite spriteLose;
    public int width;

    public GameOver(Sprite spriteLose) {
        this.spriteLose = spriteLose;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(stringLose));

        width = ii.getImage().getWidth(null);

        spriteLose.setX(START_X);
        spriteLose.setY(START_Y);
        spriteLose.setImage(ii.getImage());

    }
}

