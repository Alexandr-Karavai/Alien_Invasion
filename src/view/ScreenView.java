package view;

import controller.KeyController;
import model.*;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static controller.Settings.*;


public class ScreenView extends JPanel implements Runnable {

    private Player player;
    private Shot shot;
    private ArrayList aliens;
    private BossAlien bossAlien;

    private Background background;


    public Dimension dimension;

    public boolean inGame = true;

    private Thread animationThread;

    private int countShot = -1;
    private int countScores = 0;
    private String scoresString;
    private String shotString;

    private int direction = -3;
    private int deaths = 0;
    public int bossCountLife = 3;

    private final String alienpix = "/res/alien2.png";
    private final String boom = "/res/blood.png";
    private final String boomBoss = "/res/bloodBoss.png";


    public ScreenView()
    {
        addKeyListener(new KeyController(this));
      //  addKeyListener(new KeyManager());
        setFocusable(true);
        dimension = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
        //setBackground(Color.black);
        gameCreateObject();

    }


    public void gameCreateObject() {

        background = new Background(new Sprite());
        aliens = new ArrayList();
        bossAlien = new BossAlien(new Sprite());
        bossAlien.bossSprite.setVisible(false);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));

        for (int i=0; i < 1; i++) {
            for (int j=0; j < 1; j++) {
                Alien alien = new Alien(ALIEN_X + 45*j, ALIEN_Y + 40*i, new Sprite());
                alien.spriteAlien.setImage(ii.getImage());
                aliens.add(alien);
            }
        }

        shot = new Shot(new Sprite());
        player = new Player(new Sprite());


        if (animationThread == null || !inGame) {
            animationThread = new Thread(this);
            animationThread.start();
        }
    }

    public void drawBackground(Graphics g) {

        if (background.spriteBack.isVisible()) {
            g.drawImage(background.spriteBack.getImage(), background.spriteBack.getX(),
                    background.spriteBack.getY(), this);
        }
    }

    public void drawPlayer(Graphics g) {

        if (player.spritePlayer.isVisible()) {
            g.drawImage(player.spritePlayer.getImage(), player.spritePlayer.getX(), player.spritePlayer.getY(), this);
        }

        if (player.spritePlayer.isDying()) {
            player.spritePlayer.die();
            inGame = false;
        }
    }

    public void drawBoss(Graphics g){
        if (bossAlien.bossSprite.isVisible()) {
            g.drawImage(bossAlien.bossSprite.getImage(),
                    bossAlien.bossSprite.getX(), bossAlien.bossSprite.getY(), this);
        }

        if (bossAlien.bossSprite.isDying()) {
            bossAlien.bossSprite.die();
        }

    }

    public void drawAliens(Graphics g)
    {
        Iterator it = aliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();

            if (alien.spriteAlien.isVisible()) {
                g.drawImage(alien.spriteAlien.getImage(), alien.spriteAlien.getX(), alien.spriteAlien.getY(), this);
            }

            if (alien.spriteAlien.isDying()) {
                alien.spriteAlien.die();
            }
        }
    }


    public void drawShot(Graphics g) {
        if (shot.spriteShot.isVisible())
            g.drawImage(shot.spriteShot.getImage(), shot.spriteShot.getX(), shot.spriteShot.getY(), this);
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        shotString = "Shots: " + countShot;
        scoresString = "Scores: " + 100*countScores;

       // g.setColor(Color.black);
       // g.fillRect(0, 0, dimension.width, dimension.height);
        g.setColor(Color.green);
        drawBackground(g);
        if (inGame) {
            Font font = new Font("Helvetica", Font.BOLD, 28);
            g.setFont(font);
            g.drawString(shotString,40,840);
            g.drawString(scoresString,650,840);
            g.drawLine(0, EARTH, SCREEN_WIDTH, EARTH);
            drawAliens(g);
            drawBoss(g);
            drawPlayer(g);
            drawShot(g);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void animationCycle() {

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            bossAlien.bossSprite.setVisible(true);
            if (bossAlien.bossSprite.isVisible()) {
                int x = bossAlien.bossSprite.getX();
                if (x  >= SCREEN_WIDTH - SCREEN_RIGHT_BOSS && direction != -3) {direction = -3;}
                if (x <= SCREEN_LEFT && direction != 3) {direction = 3;}
/*
              //  int y = bossAlien.bossSprite.getY();

                if (y > EARTH - ALIEN_HEIGHT) {
                    inGame = false;
                    //void gameOver;
                }*/
                bossAlien.act(direction);
            }
        }

        if (shot.spriteShot.isVisible()) {

            Iterator alienDeathShot = aliens.iterator();
            int shotX = shot.spriteShot.getX();
            int shotY = shot.spriteShot.getY();

            while (alienDeathShot.hasNext()) {
                Alien alien = (Alien) alienDeathShot.next();
                int alienX = alien.spriteAlien.getX();
                int alienY = alien.spriteAlien.getY();

                if (alien.spriteAlien.isVisible() && shot.spriteShot.isVisible()) {
                    if ( shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) &&
                            shotY >= (alienY) && shotY <= (alienY+ALIEN_HEIGHT) ) {
                        ImageIcon ii = new ImageIcon(getClass().getResource(boom));
                        alien.spriteAlien.setImage(ii.getImage());
                        alien.spriteAlien.setDying(true);
                        deaths++;
                        countShot++;
                        countScores++;
                        shot.spriteShot.die();
                    }
                }
            }

            if (bossAlien.bossSprite.isVisible() && shot.spriteShot.isVisible()) {
                int bossX = bossAlien.bossSprite.getX();
                int bossY = bossAlien.bossSprite.getY();
                if ( shotX >= (bossX) && shotX <= (bossX + BOSS_WIDTH) &&
                        shotY >= (bossY) && shotY <= (bossY + BOSS_HEIGHT) ) {
                    bossCountLife--;
                    shot.spriteShot.die();
                    countShot++;
                    if(bossCountLife == 0) {
                        ImageIcon ii = new ImageIcon(getClass().getResource(boomBoss));
                        bossAlien.bossSprite.setImage(ii.getImage());
                        bossAlien.bossSprite.setDying(true);
                        countShot++;
                        deaths++;
                        countScores++;
                    }
                }
            }

            int y = shot.spriteShot.getY();
            y -= 10;
            if (y < 0) {
                shot.spriteShot.die();
                countShot++;
            }
            else shot.spriteShot.setY(y);
        }

        Iterator enterDirection = aliens.iterator();

        while (enterDirection.hasNext()) {
            Alien selectionDirection = (Alien) enterDirection.next();
            int x = selectionDirection.spriteAlien.getX();

            if (x  >= SCREEN_WIDTH - SCREEN_RIGHT && direction != -3) {
                direction = -3;

                Iterator alienSelectionDirection = aliens.iterator();
                while (alienSelectionDirection.hasNext()) {
                    Alien a2 = (Alien) alienSelectionDirection.next();
                    a2.spriteAlien.setY(a2.spriteAlien.getY() + GO_DOWN);
                }
            }

            if (x <= SCREEN_LEFT && direction != 3) {
                direction = 3;

                Iterator i2 = aliens.iterator();
                while (i2.hasNext()) {
                    Alien a = (Alien)i2.next();
                    a.spriteAlien.setY(a.spriteAlien.getY() + GO_DOWN);
                }
            }
        }

        Iterator it = aliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            if (alien.spriteAlien.isVisible()) {

                int y = alien.spriteAlien.getY();

                if (y > EARTH - ALIEN_HEIGHT) {
                    inGame = false;
                   //void gameOver;
                }
                alien.act(direction);
            }
        }
        player.act();
    }


    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (inGame) {
            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = SPEED_GAME_ANIMATION - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Поток прерван");
            }
            beforeTime = System.currentTimeMillis();
        }

    }

    public void keyPressShot(){
        int x = player.spritePlayer.getX();
        int y = player.spritePlayer.getY();
        if (!shot.spriteShot.isVisible())
            shot = new Shot(new Sprite(),x, y);
    }
    public void  keyLeft(){player.moveLeft();}
    public void keyRight(){player.moveRight();}
    public void stopKey(){player.stopPlayer();}
/*
    public class KeyManager extends KeyAdapter {


        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {player.stopPlayer();}
            if (key == KeyEvent.VK_RIGHT) {player.stopPlayer();}
        }


        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {player.moveLeft();}
            if (key == KeyEvent.VK_RIGHT) {player.moveRight();}

            int x = player.spritePlayer.getX();
            int y = player.spritePlayer.getY();

            if (inGame)
            {

                if (key == KeyEvent.VK_UP)
                {
                    if (!shot.spriteShot.isVisible())
                    shot = new Shot(new Sprite(),x, y);
                }

                if (key == KeyEvent.VK_ESCAPE) { System.exit(0); }
            }


        }
    }

*/
}
