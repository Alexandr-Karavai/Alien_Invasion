package model;

import javax.swing.*;

/**
 * Created by karavai on 24.11.16.
 */
public class MedKit {

    private String medkit = "/res/medkit.png";

    private final int START_Y = 540;
    private final int START_X = 450;
    public Sprite spriteMedKit;
    public int width;

    public MedKit(Sprite spriteMedKit) {
        this.spriteMedKit = spriteMedKit;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(medkit));

        width = ii.getImage().getWidth(null);

        spriteMedKit.setX(START_X);
        spriteMedKit.setY(START_Y);
        spriteMedKit.setImage(ii.getImage());

    }
}
