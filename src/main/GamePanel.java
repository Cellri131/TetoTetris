package main;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;

public class GamePanel extends JPanel implements Runnable {
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;

    public GamePanel() {

        // Configuration du JPanel
        this.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void run() {
        
        //Game loop
        double drawInterval = 1000000000 / FPS; // 0.01666667 secondes
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        // TODO Auto-generated method stub
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO Auto-generated method stub
        
    }
}
