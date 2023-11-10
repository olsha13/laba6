package classes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MyThread extends JFrame {
    private int ballX = 100;
    private int ballY = 155;
    private int TanksX = 10;
    private int TanksY = 155;
    private int XX = 700;
    private int XY = 310;
    private Random randomBuffImg1;
    private int ballHeight = 1;
    private int ballWidth = 1;
    private int tanksHeight = 170;
    private int tanksWidth = 170;
    private int XHeight = 1;
    private int XWidth = 1;
    private int bumHeight = 1;
    private int bumWidth = 1;
    private int bumStart = 0;
    private static Image background;
    private static Image ball1;
    private static Image X;
    private static Image tanks;
    private static Image bum;
    private static Thread ball1Move;
    private static Thread XMove;
    private static Thread bumMove;
    JButton start;
    JButton bums;
    Label finish;

    public MyThread(){
        this.setTitle("Стрелялки");
        this.setDefaultCloseOperation(3);
        this.setSize(800, 420);
        this.setLocationRelativeTo((Component)null);
        this.setResizable(false);
        

        ball1Move = new Thread(new ball1Thread());
        XMove = new Thread(new XThread());
        bumMove = new Thread(new bumThread());

        this.setContentPane(new Background());
        Container content = this.getContentPane();


        this.start = new JButton("Старт");
        this.start.setPreferredSize(new Dimension(500, 25));


        this.bums = new JButton("Стрелять");
        this.bums.setPreferredSize(new Dimension(500, 25));
        this.bums.setVisible(false);


        this.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyThread.this.start.setVisible(false);
                MyThread.this.bums.setVisible(true);
                MyThread.XMove.start();
            }
        });

        this.bums.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyThread.this.start.setVisible(true);
                MyThread.this.bums.setVisible(false);
                MyThread.ball1Move.start();
            }
        });

        content.add(this.start);
        content.add(this.bums);
        content.add(new All());
        
    }

    public class ball1Thread implements Runnable{
        public ball1Thread(){}

        @Override
        public synchronized void run() {
            MyThread.this.ballHeight = 80;
            MyThread.this.ballWidth = 80;
            while (MyThread.this.ballY <=50 || MyThread.this.ballX <=300||!Thread.interrupted()){
                MyThread v = MyThread.this;
                v.ballX +=2;
                v.ballY -=1;
                if(v.ballX >=(v.XX - v.ballHeight) && v.ballY>=(v.XY  - v.ballWidth) && v.ballX <=(v.XX + v.XHeight) && v.ballY <= (v.XY + v.XWidth)){
                    
                    MyThread.bumMove.start();
                    bumStart = 1;
                    return;
                }
                MyThread.this.repaint();
                try {
                    Thread.sleep(5);
                } catch (Exception e) {}
            }

            while (MyThread.this.ballY <=380 || MyThread.this.ballX <=700||!Thread.interrupted()){
                MyThread v = MyThread.this;
                v.ballX +=2;
                v.ballY +=1;
                if(v.ballX >=(v.XX - v.ballHeight) && v.ballY>=(v.XY  - v.ballWidth) && v.ballX <=(v.XX + v.XHeight) && v.ballY <= (v.XY + v.XWidth)){
                    
                    MyThread.bumMove.start();
                    bumStart = 1;
                    return;
                }
                MyThread.this.repaint();
                try {
                    Thread.sleep(5);
                } catch (Exception e) {}
            }
        }
    }

    
    
    public class bumThread implements Runnable{
        
        @Override
        public  void run() {
            MyThread.XMove.interrupt();  
            MyThread.ball1Move.interrupt();
            MyThread.this.ballHeight = 1;
            MyThread.this.ballWidth = 1;
            MyThread.this.XHeight = 1;
            MyThread.this.XWidth = 1;
            for(int i =0;i<5;i++)
            {
                MyThread.this.bumWidth = 200;
                MyThread.this.bumHeight = 200;
                MyThread.this.repaint();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {}

                MyThread.this.bumWidth = 1;
                MyThread.this.bumHeight = 1;
                MyThread.this.repaint();
                try {
                        Thread.sleep(500);
                    } catch (Exception e) {}
            }
            
        }
    }

    
    public class XThread implements Runnable{
        public XThread(){}

        @Override
        public synchronized void run() {
            while(!Thread.interrupted())
            {
                if(bumStart == 1){
                    MyThread.this.XHeight = 1;
                    MyThread.this.XWidth = 1;
                    MyThread.this.repaint();
                    return;}
                else{
                    randomBuffImg1 = new Random();
                    MyThread.this.XX = randomBuffImg1.nextInt((MyThread.this.getWidth() - XWidth-80 )+1)-XWidth;
                    MyThread.this.XY = randomBuffImg1.nextInt((MyThread.this.getHeight() - XHeight-130)+1)-XHeight;

                    MyThread.this.XHeight = 80;
                    MyThread.this.XWidth = 80;
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}
                    MyThread.this.repaint();
                    MyThread.this.XHeight = 1;
                    MyThread.this.XWidth = 1;
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {}
                    MyThread.this.repaint();
                }
            }
        }
    }

    private static class Background extends JPanel {
        private Background() {
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                MyThread.background = ImageIO.read(new File("resourse/фон.jpg"));
            } catch (IOException var3) {
                var3.printStackTrace();
            }

            g.drawImage(MyThread.background, 0, 0, (ImageObserver)null);
        }
    }

    private class All extends JPanel {
        public All() {
            this.setOpaque(false);
            this.setPreferredSize(new Dimension(800, 420));

            try {
                MyThread.ball1 = ImageIO.read(new File("resourse/boom.png"));
                MyThread.tanks = ImageIO.read(new File("resourse/пушка.png"));
                MyThread.X = ImageIO.read(new File("resourse/мишень.png"));
                MyThread.bum = ImageIO.read(new File("resourse/взрыв.png"));
            } catch (IOException var3) {
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D)g;
            graphics2D.drawImage(MyThread.ball1, MyThread.this.ballX, MyThread.this.ballY, MyThread.this.ballWidth, MyThread.this.ballHeight, this);
            graphics2D.drawImage(MyThread.X, MyThread.this.XX, MyThread.this.XY, MyThread.this.XWidth, MyThread.this.XHeight, this);
            graphics2D.drawImage(MyThread.tanks, MyThread.this.TanksX, MyThread.this.TanksY, MyThread.this.tanksWidth, MyThread.this.tanksHeight, this);
            graphics2D.drawImage(MyThread.bum, MyThread.this.XX, MyThread.this.XY, MyThread.this.bumWidth, MyThread.this.bumHeight, this);
        }
    }
}
