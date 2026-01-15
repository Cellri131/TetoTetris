package main;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;

public class GamePanel extends JPanel implements Runnable {
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager pm;
    public boolean paused = false;
    public ControlConfigPanel configPanel;

    public GamePanel() {

        // Configuration du JPanel
        this.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        //Implement KeyListener
        this.addKeyListener(new KeyHandle(this));
        this.setFocusable(true);

        pm = new PlayManager();
        pm.setGamePanel(this);

        // Panel de configuration des touches
        configPanel = new ControlConfigPanel();
        configPanel.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                setPaused(false);
            }
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                setPaused(false);
            }
        });
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
                if (!paused) {
                    update();
                }
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (!paused && !pm.gameOver)
            pm.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO Auto-generated method stub
        
        Graphics2D g2 = (Graphics2D)g; 
        pm.draw(g2);
    }

    public void setPaused(boolean value) {
        paused = value;
        if (!paused) {
            configPanel.setVisible(false);
        }
    }

    public void showConfigPanel() {
        configPanel.setVisible(true);
    }
}
