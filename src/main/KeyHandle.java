package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandle implements KeyListener{


    public static boolean upPressed, leftPressed, downPressed, rightPressed;
    private GamePanel gamePanel;

    public KeyHandle(GamePanel panel) {
        this.gamePanel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        

        int code = e.getKeyCode();
        String keyText = KeyEvent.getKeyText(code).toUpperCase();
        ControlConfigPanel config = gamePanel.configPanel;

        // Clavier dynamique
        if(keyText.equalsIgnoreCase(config.keyBindings.get("Rotation")))
            upPressed = true;
        if(keyText.equalsIgnoreCase(config.keyBindings.get("Gauche")))
            leftPressed = true;
        if(keyText.equalsIgnoreCase(config.keyBindings.get("Descente Rapide")))
            downPressed = true;
        if(keyText.equalsIgnoreCase(config.keyBindings.get("Droite")))
            rightPressed = true;

        // Manette (flèches)
        if(config.padBindings.get("Gauche").equals("←") && code == KeyEvent.VK_LEFT)
            leftPressed = true;
        if(config.padBindings.get("Droite").equals("→") && code == KeyEvent.VK_RIGHT)
            rightPressed = true;
        if(config.padBindings.get("Rotation").equals("↺") && code == KeyEvent.VK_UP)
            upPressed = true;
        if(config.padBindings.get("Descente Rapide").equals("↓") && code == KeyEvent.VK_DOWN)
            downPressed = true;

        // Pause (toggle) avec espace
        if(code == KeyEvent.VK_SPACE) {
            gamePanel.setPaused(!gamePanel.paused);
        }

        // Panneau config avec P (met en pause et affiche le panneau)
        if(code == KeyEvent.VK_P) {
            if (!gamePanel.paused) {
                gamePanel.setPaused(true);
            }
            gamePanel.showConfigPanel();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    
}
