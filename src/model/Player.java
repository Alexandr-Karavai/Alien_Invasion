package model;

import javax.swing.*;
import static controller.Settings.*;

public class Player  {

    private String player = "/res/deltoid.png";

    private final int START_Y = 530;
    private final int START_X = 360;
    public Sprite spritePlayer;
    private int width;


    public Player(Sprite spritePlayer) {
        this.spritePlayer = spritePlayer;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(player));

        width = ii.getImage().getWidth(null);

        spritePlayer.setX(START_X);
        spritePlayer.setY(START_Y);
        spritePlayer.setImage(ii.getImage());

    }

    public void act() {
        spritePlayer.x += spritePlayer.dx;
        if (spritePlayer.x <= 5)
            spritePlayer.x = 5;
        if (spritePlayer.x >= SCREEN_WIDTH - 2*width)
            spritePlayer.x = SCREEN_HEIGHT - 2*width;
    }

    public void moveLeft(){
        spritePlayer.dx = -5;
    }

    public void  moveRight(){
        spritePlayer.dx = 5;
    }

    public void stopPlayer(){spritePlayer.dx = 0;}

}
