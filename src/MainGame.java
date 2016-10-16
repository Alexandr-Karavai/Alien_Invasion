import javazoom.jl.player.Player;
import view.ScreenView;

import javax.swing.*;

import java.io.FileInputStream;

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

        try{
            FileInputStream fis = new FileInputStream("src/res/main.mp3");
            Player playMP3 = new Player(fis);
            new MainGame();
            playMP3.play();
        }catch(Exception e){System.out.println(e);}

    }

}
