package model;
import javax.swing.ImageIcon;


public class Alien {

    public Sprite spriteAlien;

    private final String shot = "/res/alien2.png";

    public Alien(int x, int y, Sprite spriteAlien) {
        this.spriteAlien = spriteAlien;
        this.spriteAlien.x = x;
        this.spriteAlien.y = y;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        spriteAlien.setImage(ii.getImage());

    }

    public void act(int direction) {
        this.spriteAlien.x += direction;
    }

}