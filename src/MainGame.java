import view.NewGame;
import view.ScreenView;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static controller.Settings.*;

public class MainGame extends JFrame  {

    private JButton newGame;
    private JButton exitGame;
    private JPanel panelMenu;


    public MainGame() {
        ImageIcon icon = new ImageIcon(this.getClass().getResource("res/12.jpg"));
        ImageIcon iconExit = new ImageIcon(this.getClass().getResource("res/exit.jpg"));
        panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(2,1,50,0));
        newGame = new JButton();
        newGame.setIcon(icon);
      //  newGame.setSize(48,48);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new NewGame();
            }
        });
        panelMenu.add(newGame);
        exitGame = new JButton();
        exitGame.setIcon(iconExit);
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panelMenu.add(exitGame);
        add(panelMenu);
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
