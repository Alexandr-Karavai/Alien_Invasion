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
    private BombBoss bombBoss;
    private ArrayList aliens;
    private BossAlien bossAlien;
    private Sprite status;
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
    private final String gameOver= "/res/gameOver2.png";


    public ScreenView()
    {
        addKeyListener(new KeyController(this));
        setFocusable(true);
        dimension = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameCreateObject();
    }


    public void gameCreateObject() {

        background = new Background(new Sprite());
        aliens = new ArrayList();

        bossAlien = new BossAlien(new Sprite());
        bossAlien.bossSprite.setVisible(false);

        status = new Sprite();
        status.setVisible(false);

        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));

        for (int i=0; i < 4; i++) {
            for (int j=0; j < 5; j++) {
                Alien alien = new Alien(ALIEN_X + 45*j, ALIEN_Y + 40*i, new Sprite());
                alien.spriteAlien.setImage(ii.getImage());
                aliens.add(alien);
            }
        }

        shot = new Shot(new Sprite());
        bombBoss = new BombBoss(new Sprite());
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

    public void drawStatus(Graphics g){

        if(status.isVisible()){
             ImageIcon ii = new ImageIcon(getClass().getResource(gameOver));
                status.setX(160);
                status.setY(200);
                status.setImage(ii.getImage());
                g.drawImage(status.getImage(),status.getX(),status.getY(),this);
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

    public void drawBombing(Graphics g) {
        if (bombBoss.spriteBomb.isVisible())
            g.drawImage(bombBoss.spriteBomb.getImage(), bombBoss.spriteBomb.getX(), bombBoss.spriteBomb.getY(), this);
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
        g.setColor(Color.green);
        drawBackground(g);
        drawStatus(g);
        if (inGame) {
            Font font = new Font("Helvetica", Font.BOLD, 28);
            g.setFont(font);
            g.drawString(shotString,40,640);
            g.drawString(scoresString,520,640);
            g.drawLine(0, EARTH, SCREEN_WIDTH, EARTH);
            drawAliens(g);
            drawBoss(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void animationCycle() {
        player.act();

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {

            bossAlien.bossSprite.setVisible(true);
            if (bossAlien.bossSprite.isVisible()) {

                int xB = bossAlien.bossSprite.getX();
                int yB = bossAlien.bossSprite.getY();
                if (!bombBoss.spriteBomb.isVisible())
                    bombBoss = new BombBoss(new Sprite(),xB, yB);

                int x = bossAlien.bossSprite.getX();
                if (x  >= SCREEN_WIDTH - SCREEN_RIGHT_BOSS && direction != -3) {direction = -3;}
                if (x <= SCREEN_LEFT && direction != 3) {direction = 3;}
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

        if (bombBoss.spriteBomb.isVisible()){

            int bombX = bombBoss.spriteBomb.getX();
            int bombY = bombBoss.spriteBomb.getY();
            int playerX = player.spritePlayer.getX();
            int playerY = player.spritePlayer.getY();

            if (player.spritePlayer.isVisible() && !bombBoss.spriteBomb.isDying()) {
                if ( bombX >= (playerX) &&
                        bombX <= (playerX+PLAYER_WIDTH) &&
                        bombY >= (playerY) &&
                        bombY <= (playerY+PLAYER_HEIGHT) ) {
                    ImageIcon ii =
                            new ImageIcon(this.getClass().getResource(boom));
                    player.spritePlayer.setImage(ii.getImage());
                    player.spritePlayer.setDying(true);
                    status.setVisible(true);
                    bombBoss.spriteBomb.die();
                    player.spritePlayer.setVisible(false);

                }
            }


            int y = bombBoss.spriteBomb.getY();
            y += 10;
            if (bombBoss.spriteBomb.getY() >= EARTH - BOMB_HEIGHT) {
                bombBoss.spriteBomb.die();
            }
            else bombBoss.spriteBomb.setY(y);

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
                    status.setVisible(true);
                    inGame = false;

                }
                alien.act(direction);
            }
        }

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
}
