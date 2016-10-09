import view.ScreenView;

import javax.swing.*;

import static controller.Settings.*;



public class MainGame extends JFrame  {

    public MainGame() {

        add(new ScreenView());
        setTitle("Kill Alien");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        new MainGame();
    }

}
