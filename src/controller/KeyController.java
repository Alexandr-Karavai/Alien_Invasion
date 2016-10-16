package controller;

import view.ScreenView;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyController extends KeyAdapter {

    public ScreenView screenView;
    public KeyController(ScreenView screenView) {this.screenView=screenView;}

    public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {screenView.stopKey();}
            if (key == KeyEvent.VK_RIGHT) {screenView.stopKey();}
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {screenView.keyLeft();}
            if (key == KeyEvent.VK_RIGHT) {screenView.keyRight();}

            if (screenView.inGame)
            {
                if (key == KeyEvent.VK_UP) {screenView.keyPressShot();}
            }
            if (key == KeyEvent.VK_ESCAPE) { System.exit(0); }
        }
}
