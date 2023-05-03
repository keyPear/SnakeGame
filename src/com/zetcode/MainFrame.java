package com.zetcode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        JButton openMarketButton = new JButton("Open Market");
        openMarketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMarketFrame();
            }
        });

        JButton openMypageButton = new JButton("Open Mypage");
        openMypageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMypageFrame();
            }
        });
        JButton closeMainButton = new JButton("Close Main");
        closeMainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeMainFrame();
            }
        });
        JButton gotoBoardButton = new JButton("go to game");
        gotoBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBoard();
            }
        });

        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        openMarketButton.setBounds(0, 100, 100, 100);
        openMypageButton.setBounds(300, 100, 100, 100);
        closeMainButton.setBounds(600, 100, 100, 100);
        gotoBoardButton.setBounds(300,600,100,100);
        add(openMarketButton);
        add(openMypageButton);
        add(closeMainButton);
        add(gotoBoardButton);
        setTitle("Main");
    }

    private void openMarketFrame() {
        MarketFrame marketFrame = new MarketFrame(this);

        setVisible(false);

        marketFrame.setVisible(true);
    }

    private void openMypageFrame() {
        MypageFrame mypageFrame = new MypageFrame(this);
        setVisible(false);
        mypageFrame.setVisible(true);
    }

    private void closeMainFrame() {
        System.exit(0);
    }

    private void openBoard() {
        Snake board = new Snake();
        setVisible(false);
        board.setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
