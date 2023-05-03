package com.zetcode;

import java.awt.Image;
import javax.swing.ImageIcon;

public class MonsterEntity {
    private int x, y;
    private static Image image;

    public MonsterEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public static void loadImage(String imagePath) {
        ImageIcon iia = new ImageIcon(imagePath);
        image = iia.getImage();
    }

}