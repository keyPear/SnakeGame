package com.zetcode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 800;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 22500;
    private final int RAND_POS = 149;
    private final int DELAY = 30;
    private final int GAP = 2; // 각 부분 사이의 여유 공간
    private final int INIT_SPEED = DOT_SIZE + GAP;
    private final int TIMER_DELAY = 30; // 사과 생성 주기

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private AppleEntity apple;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image head;

    private long lastAppleTime;
    private final int APPLE_INTERVAL = 1000; // 사과 생성 주기(ms, 1000ms = 1초)

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();

        AppleEntity.loadImage("src/resources/apple.png");
    }

    private void initGame() {
        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        apple = new AppleEntity(0, 0);
        lastAppleTime = System.currentTimeMillis();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple.getImage(), apple.getX(), apple.getY(), this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {
        int deltaX = Math.abs(x[0]- apple.getX());
        int deltaY = Math.abs(y[0] - apple.getY());

        if (deltaX < (DOT_SIZE + GAP) && deltaY < (DOT_SIZE + GAP)) {
            dots++;
            locateApple();
            lastAppleTime = System.currentTimeMillis();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            int distanceX = Math.abs(x[z] - x[(z - 1)]);
            int distanceY = Math.abs(y[z] - y[(z - 1)]);

            if (distanceX >= (DOT_SIZE + GAP) || distanceY >= (DOT_SIZE + GAP)) {
                x[z] = x[(z - 1)];
                y[z] = y[(z - 1)];
            }
        }

        if (leftDirection) {
            x[0] -= INIT_SPEED;
        }

        if (rightDirection) {
            x[0] += INIT_SPEED;
        }

        if (upDirection) {
            y[0] -= INIT_SPEED;
        }

        if (downDirection) {
            y[0] += INIT_SPEED;
        }
    }
    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        int x = r * DOT_SIZE;

        r = (int) (Math.random() * RAND_POS);
        int y = r * DOT_SIZE;

        apple.setX(x);
        apple.setY(y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();

            if (System.currentTimeMillis() - lastAppleTime > APPLE_INTERVAL) {
                locateApple();
                lastAppleTime = System.currentTimeMillis();
            }
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
