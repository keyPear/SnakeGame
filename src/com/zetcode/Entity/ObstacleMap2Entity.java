package com.zetcode.Entity;

import java.util.ArrayList;
import java.util.Random;

public class ObstacleMap2Entity {
    private ArrayList<Integer> obstacleX = new ArrayList<>();
    private ArrayList<Integer> obstacleY = new ArrayList<>();
    private int numObstacles;
    private int dotSize;
    private int randPos;

    private int obstacleXLen = 31;

    private int obstacleYLen = 19;


    // 生成墙体点位
    public ObstacleMap2Entity(int startx,int starty) {
        this.obstacleX = new ArrayList<>();
        this.obstacleY = new ArrayList<>();

        int xNum = 7;
        int yNum = 10;

        for (int i = 0; i < xNum; i++) {
            obstacleX.add(startx+(obstacleXLen * i));
            obstacleY.add(starty);
        }
        for (int i = 0; i < yNum; i++) {
            obstacleX.add(startx);
            obstacleY.add(starty + (obstacleYLen * i));
        }


        for (int i = 0; i < xNum; i++) {
            obstacleX.add(startx+(obstacleXLen * i));
            obstacleY.add(starty * 7 );
        }
        for (int i = 0; i < yNum; i++) {
            obstacleX.add(startx);
            obstacleY.add(starty * 7  - (obstacleYLen * i));
        }



        for (int i = 0; i < xNum; i++) {
            obstacleX.add(startx * 7 - (obstacleXLen * i));
            obstacleY.add(starty);
        }
        for (int i = 0; i < yNum; i++) {
            obstacleX.add(startx * 7);
            obstacleY.add(starty + (obstacleYLen * i));
        }


        for (int i = 0; i < xNum; i++) {
            obstacleX.add(startx * 7 - (obstacleXLen * i));
            obstacleY.add(starty *7);
        }
        for (int i = 0; i < yNum; i++) {
            obstacleX.add(startx * 7);
            obstacleY.add(starty * 7 - (obstacleYLen * i));
        }


        /////////////////////////////

        startx = 400;
        starty = 400;
        xNum = 3;
        yNum = 6;
        for (int i = 0; i < xNum; i++) {
            obstacleX.add((startx-100)-(obstacleXLen * i));
            obstacleY.add(starty-100);
        }
        for (int i = 0; i < yNum; i++) {
            obstacleX.add(startx-100);
            obstacleY.add(starty-100 - (obstacleYLen * i));
        }

        for (int i = 0; i < xNum; i++) {
            obstacleX.add((startx+100)+(obstacleXLen * i));
            obstacleY.add((starty-100) );
        }

        for (int i = 0; i < yNum; i++) {
            obstacleX.add((startx+100));
            obstacleY.add((starty-100) - (obstacleYLen * i));
        }



        for (int i = 0; i < xNum; i++) {
            obstacleX.add((startx-100) - (obstacleXLen * i));
            obstacleY.add(starty+100);
        }
        for (int i = 0; i < yNum; i++) {
            obstacleX.add((startx-100));
            obstacleY.add((starty+100) + (obstacleYLen * i));
        }

        for (int i = 0; i < xNum; i++) {
            obstacleX.add((startx+100) + (obstacleXLen * i));
            obstacleY.add((starty+100));
        }
        for (int i = 0; i < yNum; i++) {
            obstacleX.add((startx+100));
            obstacleY.add((starty+100) + (obstacleYLen * i));
        }

    }


    public ArrayList<Integer> getObstacleX() {
        return obstacleX;
    }

    public ArrayList<Integer> getObstacleY() {
        return obstacleY;
    }

    public void locateObstacles() {
        for (int i = 0; i < numObstacles; i++) {
            Random random = new Random();
            int randomPos = random.nextInt(randPos);
            obstacleX.add(randomPos * dotSize);
            randomPos = random.nextInt(randPos);
            obstacleY.add(randomPos * dotSize);
        }
    }
}
