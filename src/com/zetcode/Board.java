package com.zetcode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

import com.zetcode.InGamePannel;
import com.zetcode.GameFrame;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 800;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private InGamePannel timepannel;

    //B_WIDTH,B_HEIGHT에 따라 랜덤값 생성
    private final int RAND_POS = (int) Math.ceil((double) Math.min(B_WIDTH, B_HEIGHT - DOT_SIZE) / DOT_SIZE);

    public static int DELAY = 100; ///게임 속도 (지렁이, 메테오 등)

    public static int NUM = 10;  ///메테오, 장애물, 몬스터의 개수

    private SnakeEntity snake;
    private AppleEntity appleEntity;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;


    private final int max_apple = 5; // 최대 생성 가능한 사과 개수
    private int current_apple = 0; // 현재 생성된 사과 개수

    private final int meteorTime = 1; // 원하는 메테오 시간
    private final int meteorSpeed = 5; // 원하는 메테오 시간

    private long lastMeteorTime;
    private MeteorEntity meteorEntity;

    private ShootEntity shootEntity;
    private final int shootSpeed = 5;

    //지렁이가 무적 상태인지를 저장
    private boolean invincible = false;
    //변수와 무적 상태가 시작된 시간을 저장
    private long invincible_start_time;

    private Image invincible_head;

    private Image shoot;


    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    private Image obstacle;
    private Image meteor;
    private Image monster;

    private ObstacleEntity obstacleEntity;
    private MonsterEntity monsterEntity;

    public Board() {
        initBoard();
    }

    public static int getDelay() {
        return DELAY;
    }

    public static void setDelay(int delay) {
        DELAY = delay;
    }

    public static int getNum() {
        return NUM;
    }

    public static void setNum(int num) {
        NUM = num;
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
        timepannel = new InGamePannel();
        timepannel.setBounds(0, 0, 150, 30);
        add(timepannel);
    }

    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();

        ImageIcon iio = new ImageIcon("src/resources/obstacle.png");
        obstacle = iio.getImage();

        ImageIcon iim = new ImageIcon(("src/resources/meteor.png"));
        meteor = iim.getImage();

        ImageIcon iimn = new ImageIcon("src/resources/monster.png");
        monster = iimn.getImage();
        ImageIcon iis = new ImageIcon("src/resources/shoot.png");
        shoot = iis.getImage();

        ImageIcon ii_invincible_head = new ImageIcon("src/resources/dot.png");
        invincible_head = ii_invincible_head.getImage();

    }

    private void initGame() {
        snake = new SnakeEntity(ALL_DOTS);
        appleEntity = new AppleEntity(RAND_POS, DOT_SIZE);

        appleEntity.locateApple(); // Pass RAND_POS to locateApple method


        //장애물 개수 조절
        obstacleEntity = new ObstacleEntity(NUM, DOT_SIZE, RAND_POS); // Pass RAND_POS to ObstacleEntity constructor

        // 메테오 개수를 원하는 값으로 설정 (예: 10)
        meteorEntity = new MeteorEntity(NUM);
        lastMeteorTime = System.currentTimeMillis();

        //몬수터 수 조절
        monsterEntity = new MonsterEntity(NUM, DOT_SIZE, RAND_POS);

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
            g.drawImage(apple, appleEntity.getAppleX(), appleEntity.getAppleY(), this);

            for (int z = 0; z < snake.getDots(); z++) {
                if (z == 0) {
                    if (invincible) {
                        g.drawImage(invincible_head, snake.getX()[z], snake.getY()[z], this);
                    } else {
                        g.drawImage(head, snake.getX()[z], snake.getY()[z], this);
                    }
                } else {
                    g.drawImage(ball, snake.getX()[z], snake.getY()[z], this);
                }
            }

            for (int i = 0; i < obstacleEntity.getObstacleX().size(); i++) {
                g.drawImage(obstacle, obstacleEntity.getObstacleX().get(i), obstacleEntity.getObstacleY().get(i), this);
            }

            // 지렁이의 길이가 4의 배수일 때 메테오를 그립니다.
            if (snake.getDots() % 4 == 0) {
                for (int i = 0; i < meteorEntity.getMeteorX().length; i++) {
                    g.drawImage(meteorEntity.getMeteorImage(), meteorEntity.getMeteorX()[i], meteorEntity.getMeteorY(), this);
                }
            }

            //몬스터
            for (int i = 0; i < monsterEntity.getMonsterX().size(); i++) {
                g.drawImage(monster, monsterEntity.getMonsterX().get(i), monsterEntity.getMonsterY().get(i), this);
            }


            //총알
            for (int i = 0; i < monsterEntity.getShootEntity().getShootX().size(); i++) {
                g.drawImage(shoot, monsterEntity.getShootEntity().getShootX().get(i), monsterEntity.getShootEntity().getShootY().get(i), this);
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
        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(true);
    }

    private void checkApple() {
        if ((snake.getX()[0] == appleEntity.getAppleX()) && (snake.getY()[0] == appleEntity.getAppleY())) {
            snake.grow();
            appleEntity.locateApple();
            current_apple++;

            if (current_apple == max_apple) {
                locateApples();
            }

            // 지렁이의 길이가 4의 배수일 때 메테오 생성
            if (snake.getDots() % 4 == 0) {
                meteorEntity = new MeteorEntity(10);
                lastMeteorTime = System.currentTimeMillis();
            }
        }
    }


    private void locateApples() {
        int newApples = (int) (Math.random() * (max_apple + 1));
        for (int i = 0; i < newApples; i++) {
            appleEntity.locateApple();
        }
        current_apple = 0;
    }


    private void move() {
        snake.move(DOT_SIZE, leftDirection, rightDirection, upDirection, downDirection);
    }

    private void checkCollision() {

        if(!invincible) {
            if (!snake.checkCollision(DOT_SIZE, B_WIDTH, B_HEIGHT)) {
                inGame = false;
            }

            //장애물과 충돌 확인
            for (int i = 0; i < obstacleEntity.getObstacleX().size(); i++) {
                if (snake.getX()[0] == obstacleEntity.getObstacleX().get(i) && snake.getY()[0] == obstacleEntity.getObstacleY().get(i)) {
                    inGame = false;
                }
            }

            // 지렁이의 길이가 4의 배수인 경우에만 메테오와 충돌을 검사합니다.
            if (snake.getDots() % 4 == 0) {
                //메테오와 충돌 확인
                for (int i = 0; i < meteorEntity.getMeteorX().length; i++) {
                    if (Math.abs(snake.getX()[0] - meteorEntity.getMeteorX()[i]) < meteorEntity.getMeteorImage().getWidth(null)
                            && Math.abs(snake.getY()[0] - meteorEntity.getMeteorY()) < meteorEntity.getMeteorImage().getHeight(null)) {
                        inGame = false;
                    }
                }
            }

            if (!inGame) {
                timer.stop();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
            updateMeteor();
            monsterEntity.updateMonsterAndShootPositions(snake, shootSpeed);
            updateInvincibility();
        }

        repaint();
    }
    private void updateInvincibility() {
        if (invincible) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - invincible_start_time > 3000) {
                invincible = false;
            }
        }
    }

    private void updateMeteor() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMeteorTime > meteorTime) {
            meteorEntity.updatePosition(meteorSpeed); // 원하는 메테오 이동 속도
            lastMeteorTime = currentTime;
        }
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
            if (key == KeyEvent.VK_SPACE) {
                invincible = true;
                invincible_start_time = System.currentTimeMillis();
            }
        }
    }
}
