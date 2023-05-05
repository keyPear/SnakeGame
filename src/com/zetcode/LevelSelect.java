package com.zetcode;

import javax.swing.*;
import java.awt.event.*;

public class LevelSelect extends JFrame implements KeyListener{
    private MainFrame mainFrame;

    public LevelSelect(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Level Select");
        JButton backMainButton = new JButton("Back to Main");
        backMainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backMainFrame();
            }
        });
        JButton btn1=new JButton("Level 1");
        JButton btn2=new JButton("Level 2");
        JButton btn3=new JButton("Level 3");
        JButton btn4=new JButton("Level 4");
        JButton btn5=new JButton("Level 5");

        setLayout(null);
        backMainButton.setBounds(100,600,100,100);
        btn1.setBounds(100,100,100,100);
        btn2.setBounds(100,200,100,100);
        btn3.setBounds(100,300,100,100);
        btn4.setBounds(100,400,100,100);
        btn5.setBounds(100,500,100,100);
        add(backMainButton);
        add(btn1);
        add(btn2);
        add(btn3);
        add(btn4);
        add(btn5);

        ImageIcon background = new ImageIcon("src/resources/main_screen.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0,800, 800);
        add(backgroundLabel);

        btn1.addKeyListener(this);
        btn2.addKeyListener(this);
        btn3.addKeyListener(this);
        btn4.addKeyListener(this);
        btn5.addKeyListener(this);
        btn1.setFocusable(true);
        btn2.setFocusable(true);
        btn3.setFocusable(true);
        btn4.setFocusable(true);
        btn5.setFocusable(true);


        // Level 버튼의 ActionPerformed 메서드에 selectLevel 메서드 호출 추가
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectLevel(1);
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectLevel(2);
            }
        });
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectLevel(3);
            }
        });
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectLevel(4);
            }
        });
        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectLevel(5);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
            }
        });
    }


    // 추가적인 레벨에 따른 DELAY, Num 값 설정
    public static void selectLevel(int level)  {
        switch(level) {
            case 1:
                Board.setDelay(150);
                Board.setNum(13);
                System.out.println(1);
                System.out.println(Board.getDelay());
                System.out.println(Board.getNum());
                break;
            case 2:
                Board.setDelay(125);
                Board.setNum(15);
                System.out.println(2);
                System.out.println(Board.getDelay());
                System.out.println(Board.getNum());
                break;
            case 3:
                Board.setDelay(100);
                Board.setNum(20);
                System.out.println(3);
                System.out.println(Board.getDelay());
                System.out.println(Board.getNum());
                break;
            case 4:
                Board.setDelay(75);
                Board.setNum(23);
                System.out.println(4);
                System.out.println(Board.getDelay());
                System.out.println(Board.getNum());
                break;
            case 5:
                Board.setDelay(50);
                Board.setNum(25);
                System.out.println(5);
                System.out.println(Board.getDelay());
                System.out.println(Board.getNum());
                break;
            default:
                Board.setDelay(200);
                Board.setNum(10);
                System.out.println(0);
                System.out.println(Board.getDelay());
                System.out.println(Board.getNum());
                break;

            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // ->와<- 방향키로 메뉴버튼 이동
        if (keyCode == KeyEvent.VK_LEFT) {
            this.getFocusOwner().transferFocusBackward();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            this.getFocusOwner().transferFocus();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    private void backMainFrame() {
        MainFrame mainFrame = new MainFrame();
        setVisible(false);
        mainFrame.setVisible(true);
    }
}