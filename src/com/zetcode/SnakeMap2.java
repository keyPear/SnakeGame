package com.zetcode;

import javax.swing.*;
import java.awt.*;

/**
 * 地图2画板容器
 */
public class SnakeMap2 extends JFrame {

    public SnakeMap2() {
        initUI();
        new Sound("sound/bgm.wav");
    }

    private void initUI() {
        // 添加画板内容
        add(new Board2());
               
        setResizable(false);
        pack();
        
        setTitle("SnakeMap2");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new SnakeMap2();
            ex.setVisible(true);
        });
    }
}
