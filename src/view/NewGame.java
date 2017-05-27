package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static controller.Settings.SCREEN_HEIGHT;
import static controller.Settings.SCREEN_WIDTH;

/**
 * Created by karavai on 24.11.16.
 */
public class NewGame extends JFrame {

public NewGame()
    {
        add(new ScreenView());
        setTitle("Kill Alien");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

    }
}
